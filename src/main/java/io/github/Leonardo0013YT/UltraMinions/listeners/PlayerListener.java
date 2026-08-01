package io.github.Leonardo0013YT.UltraMinions.listeners;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionCollectEvent;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionLoadEvent;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionUnloadEvent;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionUpgrade;
import io.github.Leonardo0013YT.UltraMinions.food.Food;
import io.github.Leonardo0013YT.UltraMinions.managers.MinionManager;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.tiers.Tier;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.MathUtils;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {
   private final Main plugin;
   private final Map<UUID, Long> lastRemove = new HashMap();
   private final Map<UUID, Long> lastClick = new HashMap();

   public PlayerListener(Main plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onLogin(PlayerLoginEvent e) {
      if (this.plugin.isStop()) {
         e.setResult(Result.KICK_OTHER);
         e.setKickMessage(this.plugin.getLang().get("messages.inStopLogin"));
      }

   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onStop(PlayerCommandPreprocessEvent e) {
      if (this.plugin.getCfm().isSecureStop()) {
         if (!this.plugin.isStop()) {
            String cmd = e.getMessage().substring(1).split(" ", 2)[0].toLowerCase();
            if (cmd.equals("minecraft:stop") || cmd.equals("bukkit:stop") || cmd.equals("stop")) {
               e.setCancelled(true);
            }

         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onCommand(ServerCommandEvent e) {
      if (this.plugin.getCfm().isSecureStop()) {
         if (!this.plugin.isStop()) {
            String cmd = e.getCommand().split(" ", 2)[0].toLowerCase();
            if (cmd.equals("minecraft:stop") || cmd.equals("bukkit:stop") || cmd.equals("stop")) {
               e.setCancelled(true);
               this.shutdown();
            }

         }
      }
   }

   public void shutdown() {
      this.plugin.setStop(true);
      int amount = Bukkit.getOnlinePlayers().size();

      for(PlayerData pd : new ArrayList<PlayerData>(PlayerData.getPlayers().values())) {
         this.plugin.getDb().savePlayerSync(pd.getUuid());
      }

      (new BukkitRunnable() {
         public void run() {
            Bukkit.shutdown();
         }
      }).runTaskLater(this.plugin, (long)amount * 5L);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void onJoin(PlayerJoinEvent e) {
      Player p = e.getPlayer();
      new PlayerData(p.getUniqueId());
      this.plugin.getDb().loadPlayer(p);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onQuit(PlayerQuitEvent e) {
      Player p = e.getPlayer();
      this.plugin.getDb().savePlayer(p);
      this.lastRemove.remove(p.getUniqueId());
      this.lastClick.remove(p.getUniqueId());
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onKick(PlayerKickEvent e) {
      Player p = e.getPlayer();
      this.plugin.getDb().savePlayer(p);
      this.lastRemove.remove(p.getUniqueId());
      this.lastClick.remove(p.getUniqueId());
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onLoad(ChunkLoadEvent e) {
      if (e.isAsynchronous()) {
         Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.load(e));
      } else {
         this.load(e);
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onChunk(ChunkUnloadEvent e) {
      if (e.isAsynchronous()) {
         Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> this.unload(e));
      } else {
         this.unload(e);
      }

   }

   @EventHandler
   public void onBurn(EntityDamageEvent e) {
      if (e.getEntity() instanceof ArmorStand && (e.getCause().equals(DamageCause.FIRE) || e.getCause().equals(DamageCause.FIRE_TICK) || e.getCause().equals(DamageCause.PROJECTILE) || e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION))) {
         ArmorStand stand = (ArmorStand)e.getEntity();
         PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(stand.getUniqueId());
         if (pm == null) {
            return;
         }

         e.setCancelled(true);
         stand.setFireTicks(0);
      }

   }

   @EventHandler
   public void onExtend(BlockPistonExtendEvent e) {
      Block bl = e.getBlock();
      if (this.isInBlockRadius(bl)) {
         e.setCancelled(true);
      }

      for(Block b : e.getBlocks()) {
         if (b instanceof Skull || this.isInBlockRadius(b)) {
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void onRetract(BlockPistonRetractEvent e) {
      for(Block b : e.getBlocks()) {
         if (b instanceof Skull || this.isInBlockRadius(b)) {
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void onIgnite(EntityCombustEvent e) {
      if (e.getEntity().hasMetadata("MINION")) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void onDamage(EntityDamageByEntityEvent e) {
      if (e.getEntity() instanceof ArmorStand && e.getDamager() instanceof Player) {
         Player p = (Player)e.getDamager();
         ArmorStand stand = (ArmorStand)e.getEntity();
         PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(stand.getUniqueId());
         if (pm == null) {
            return;
         }

         e.setCancelled(true);
         if (!this.plugin.getCfm().isDestroyToRemove()) {
            p.sendMessage(this.plugin.getLang().get("messages.noDestroy"));
            return;
         }

         if (this.plugin.getCfm().isAdminBypass() && (p.isOp() || p.hasPermission("ultraminions.admin"))) {
            this.lastRemove.put(p.getUniqueId(), System.currentTimeMillis());
            Player on = pm.getP();
            PlayerData pd = PlayerData.getPlayerUUID(on.getUniqueId());
            Minion m = this.plugin.getMm().getMinion(pm.getKey());
            MinionLevel ml = m.getMinionLevelByLevel(pm.getStat().getLevel());
            MathUtils mu = new MathUtils(pm, pm.getUpgrade().getCompressor(), pm.getUpgrade().getAutoSmelt(), pm.getUpgrade().getAutoSell());
            mu.fill();
            this.plugin.getMm().getActiveMinions().remove(stand.getUniqueId());
            pm.destroy();
            pd.removePlayerMinion(pm);
            this.remove(p, pm, pd, ml);
            pd.getTypes().put(pm.getKey(), Math.max((Integer)pd.getTypes().getOrDefault(pm.getKey(), 0) - 1, 0));
            on.sendMessage(this.plugin.getLang().get("messages.adminRemoved"));
            return;
         }

         boolean permited = !this.plugin.getAdm().isAddon() ? pm.getP().getUniqueId().equals(p.getUniqueId()) : this.plugin.getAdm().checkMember(p) && this.plugin.getCfm().isRemoveMinion() || pm.getP().getUniqueId().equals(p.getUniqueId());
         if (permited) {
            this.lastRemove.put(p.getUniqueId(), System.currentTimeMillis());
            PlayerData pd = PlayerData.getPlayerUUID(pm.getP().getUniqueId());
            Minion m = this.plugin.getMm().getMinion(pm.getKey());
            MinionLevel ml = m.getMinionLevelByLevel(pm.getStat().getLevel());
            this.plugin.getMm().getActiveMinions().remove(stand.getUniqueId());
            pm.destroy();
            pd.removePlayerMinion(pm);
            MathUtils mu = new MathUtils(pm, pm.getUpgrade().getCompressor(), pm.getUpgrade().getAutoSmelt(), pm.getUpgrade().getAutoSell());
            mu.fill();
            pd.getTypes().put(pm.getKey(), Math.max((Integer)pd.getTypes().getOrDefault(pm.getKey(), 0) - 1, 0));
            this.remove(p, pm, pd, ml);
         } else {
            p.sendMessage(this.plugin.getLang().get("messages.noDestroyYour"));
         }
      }

   }

   private void remove(Player p, PlayerMinion pm, PlayerData pd, MinionLevel ml) {
      MinionCollectEvent ec = new MinionCollectEvent(new HashMap(pm.getItems()), p);
      Bukkit.getPluginManager().callEvent(ec);
      Utils.addItems(p, ec.getItems(), pm.getMinionLevel().getMax());
      pm.setActions(0);
      Utils.addItems(p, ml.getMinionHead(pm));
      if (pm.getUpgrade().getFuel() != null) {
         UpgradeFuel uf = pm.getUpgrade().getFuel();
         if (uf.isUnlimited()) {
            Utils.addItems(p, uf.getResult(false));
         }
      }

      p.sendMessage(this.plugin.getLang().get("messages.removedMinion").replaceAll("<min>", String.valueOf(pd.getMinionSize())).replaceAll("<max>", String.valueOf(this.plugin.getAdm().getMaxMinion(p))));
   }

   @EventHandler
   public void onInteractEntity(PlayerInteractAtEntityEvent e) {
      if (e.getRightClicked() instanceof ArmorStand) {
         Player p = e.getPlayer();
         ArmorStand stand = (ArmorStand)e.getRightClicked();
         if (this.lastRemove.containsKey(p.getUniqueId())) {
            long last = (Long)this.lastRemove.get(p.getUniqueId());
            if (last + 500L > System.currentTimeMillis()) {
               e.setCancelled(true);
               return;
            }

            this.lastRemove.remove(p.getUniqueId());
         }

         if (this.plugin.getMm().getMinionsToLoad().containsKey(stand.getUniqueId()) || this.plugin.getMm().getActiveMinions().containsKey(stand.getUniqueId())) {
            e.setCancelled(true);
         }

         PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(stand.getUniqueId());
         if (pm == null) {
            return;
         }

         e.setCancelled(true);
         if (this.lastClick.containsKey(p.getUniqueId())) {
            long last = (Long)this.lastClick.get(p.getUniqueId());
            if (last + 500L > System.currentTimeMillis()) {
               return;
            }

            this.lastClick.remove(p.getUniqueId());
         }

         this.lastClick.put(p.getUniqueId(), System.currentTimeMillis());
         boolean admin = this.plugin.getCfm().isAdminBypass() && (p.isOp() || p.hasPermission("ultraminions.admin"));
         boolean permited = !this.plugin.getAdm().isAddon() ? pm.getP().getUniqueId().equals(p.getUniqueId()) : this.plugin.getAdm().checkMember(p) && this.plugin.getCfm().isOpenInventory() || pm.getP().getUniqueId().equals(p.getUniqueId());
         if (permited) {
            this.plugin.getMm().removeView(p);
            this.plugin.getMm().setView(p, pm);
            MathUtils mu = new MathUtils(pm, pm.getUpgrade().getCompressor(), pm.getUpgrade().getAutoSmelt(), pm.getUpgrade().getAutoSell());
            mu.fill();
            ItemStack item = p.getItemInHand();
            if (!item.getType().equals(Material.AIR)) {
               if (this.plugin.getCfm().isFood() && pm.getStat().getFood() < pm.getMinionLevel().getFood()) {
                  Food f = this.plugin.getFm().getFoodByItem(item.clone());
                  if (f != null) {
                     pm.getStat().addFood(this.getFoodAmount(p, pm, f, item));
                     if (pm.getStat().getFood() > 10) {
                        this.plugin.getAdm().deleteHologram(pm);
                     }

                     p.sendMessage(this.plugin.getLang().get("messages.feed").replaceAll("<amount>", String.valueOf(pm.getStat().getFood())));
                     return;
                  }
               }

               if (this.plugin.getCfm().isHealth() && pm.getStat().getHealth() < pm.getMinionLevel().getHealth() && this.check(pm.getMinion().getPlace(), item.clone())) {
                  pm.getStat().addHealth(this.getHealthAmount(p, pm, item));
                  if (pm.getStat().getHealth() > 10) {
                     this.plugin.getAdm().deleteHologram(pm);
                  }

                  p.sendMessage(this.plugin.getLang().get("messages.health").replaceAll("<amount>", String.valueOf(pm.getStat().getHealth())));
                  return;
               }
            }

            this.plugin.getMem().createMinionMenu(p, pm);
         } else {
            if (admin) {
               this.plugin.getMem().createAdminMinionMenu(p, pm);
               return;
            }

            p.sendMessage(this.plugin.getLang().get("messages.noYourMinion"));
         }
      }

   }

   @EventHandler
   public void onInteract(PlayerInteractEvent e) {
      if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
         Player p = e.getPlayer();
         if (e.getClickedBlock().getType().name().endsWith("SHULKER_BOX") && this.isInBlockRadius(e.getClickedBlock())) {
            e.setCancelled(true);
            return;
         }

         ItemStack main = p.getInventory().getItemInMainHand();
         ItemStack off = p.getInventory().getItemInOffHand();
         boolean cMain = this.checkItemHand(p, main, e);
         boolean cOff = false;
         if (!cMain) {
            cOff = this.checkItemHand(p, off, e);
         }

         if (!cMain && !cOff) {
            return;
         }

         ItemStack item;
         if (cMain) {
            item = main;
         } else {
            item = off;
         }

         e.setCancelled(true);
         Block b = e.getClickedBlock();
         PlayerData pd = PlayerData.getPlayerData(p);
         if (pd == null) {
            return;
         }

         if (this.isInBlockRadius(b)) {
            e.setCancelled(true);
            p.sendMessage(this.plugin.getLang().get("messages.alreadyMinion"));
            return;
         }

         if (pd.getMinionSize() >= this.plugin.getAdm().getMaxMinion(p)) {
            p.sendMessage(this.plugin.getLang().get("messages.maxMinion"));
            return;
         }

         if (this.plugin.getAdm().isAddon() && !this.plugin.getAdm().checkMember(p)) {
            p.sendMessage(this.plugin.getLang().get("messages.noPutThis"));
            return;
         }

         if (this.plugin.getAdm().isProtect(p, b.getLocation())) {
            p.sendMessage(this.plugin.getLang().get("messages.isProtect"));
            return;
         }

         String key = NBTEditor.getString(item, "KEY");
         if (this.plugin.getCfm().isPermissionToPlace() && !this.plugin.getAdm().hasPermission(p, "ultraminions.place." + key)) {
            p.sendMessage(this.plugin.getLang().get("messages.noPermissionToPlace"));
            return;
         }

         int max = this.plugin.getAdm().getMaxPerType(p, key);
         if (max != 0 && (Integer)pd.getTypes().getOrDefault(key, 0) >= max) {
            p.sendMessage(this.plugin.getLang().get("messages.noMoreThisType"));
            return;
         }

         pd.getTypes().put(key, (Integer)pd.getTypes().getOrDefault(key, 0) + 1);
         int level = NBTEditor.getInt(item, "LEVEL");
         int generated = NBTEditor.getInt(item, "GENERATED");
         int food = NBTEditor.getInt(item, "FOOD");
         int health = NBTEditor.getInt(item, "HEALTH");
         int workTime = NBTEditor.getInt(item, "WORKTIME");
         int sleep = NBTEditor.getInt(item, "SLEEP");
         long fueltime = NBTEditor.getLong(item, "FUELTIME");
         PlayerMinion pm = new PlayerMinion(b.getLocation().clone().add((double)0.5F, (double)1.0F, (double)0.5F), key, p);
         PlayerMinionUpgrade upgrade = new PlayerMinionUpgrade(pm);
         String autosell = NBTEditor.getString(item, "AUTOSELL");
         String skin = NBTEditor.contains(item, "SKIN") ? NBTEditor.getString(item, "SKIN") : "none";
         MinionManager.createMinion(item, level, generated, food, health, workTime, sleep, fueltime, pm, upgrade, autosell, this.plugin);
         pd.addPlayerMinion(pm);
         pm.firstSpawn();
         this.removeItemInHand(p, cMain);
         p.sendMessage(this.plugin.getLang().get("messages.placeMinion").replaceAll("<minion>", pm.getMinion().getTitle()).replaceAll("<min>", String.valueOf(pd.getMinionSize())).replaceAll("<max>", String.valueOf(this.plugin.getAdm().getMaxMinion(p))));
         pm.setSkin(skin);
         if (this.plugin.getCfm().isUnlockingTiers() && pd.isNewUnlocked(key, level)) {
            pd.setLevel(key, level);
            Tier now = this.plugin.getTm().getTierByUnlocked(pd.getUnlocked());
            Tier next = this.plugin.getTm().getTierByUnlocked(pd.getUnlocked() + 1);
            pd.setUnlocked(pd.getUnlocked() + 1);
            if (next != null) {
               if (level == 1) {
                  Tier nt = this.plugin.getTm().getNextTier(next);
                  p.sendMessage(this.plugin.getLang().get("messages.newMinion").replaceAll("<title>", pm.getMinionLevel().getLevelTitle()).replaceAll("<tier>", String.valueOf(nt.getRequired() - pd.getUnlocked())));
                  return;
               }

               if (!now.equals(next)) {
                  p.sendMessage(this.plugin.getAdm().getTier(p).getMsg().replaceAll("<now>", String.valueOf(pd.getMinionSize())).replaceAll("<max>", String.valueOf(this.plugin.getAdm().getMaxMinion(p))));
               } else {
                  Tier nt = this.plugin.getTm().getNextTier(next);
                  p.sendMessage(this.plugin.getLang().get("messages.newMinion").replaceAll("<title>", pm.getMinionLevel().getLevelTitle()).replaceAll("<tier>", String.valueOf(nt.getRequired() - pd.getUnlocked())));
               }
            }
         }
      }

   }

   public boolean checkItemHand(Player p, ItemStack item, PlayerInteractEvent e) {
      if (item != null && !item.getType().equals(Material.AIR)) {
         if (!item.getType().equals(Material.LAVA_BUCKET) && !item.getType().equals(Material.WATER_BUCKET) && !item.getType().isBlock() || this.plugin.getUm().getFuel(false, item) == null && this.plugin.getUm().getAutoSell(item) == null && this.plugin.getUm().getCompressor(item) == null && this.plugin.getUm().getAutoSmelt(item) == null) {
            if (this.plugin.getSkm().getMinionSkinByName(item) != null) {
               e.setCancelled(true);
               p.sendMessage(this.plugin.getLang().get("messages.noPutSkin"));
               return false;
            } else {
               return NBTEditor.getString(item, "KEY") != null;
            }
         } else {
            e.setCancelled(true);
            p.sendMessage(this.plugin.getLang().get("messages.noPlaceUpgrades"));
            return false;
         }
      } else {
         return false;
      }
   }

   @EventHandler
   public void onDeath(EntityDeathEvent e) {
      if (!(e.getEntity() instanceof Player)) {
         if (e.getEntity().hasMetadata("MINION") && e.getEntity().getKiller() == null) {
            e.getDrops().clear();
         }

      }
   }

   private void removeItemInHand(Player p, boolean main) {
      ItemStack item = main ? p.getInventory().getItemInMainHand() : p.getInventory().getItemInOffHand();
      if (item != null && !item.getType().equals(Material.AIR)) {
         if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
         } else if (main) {
            p.getInventory().setItemInMainHand((ItemStack)null);
         } else {
            p.getInventory().setItemInOffHand((ItemStack)null);
         }
      }

   }

   @EventHandler
   public void onTP(PlayerTeleportEvent e) {
      if (e.getTo() != null) {
         Chunk chunk = e.getTo().getChunk();
         this.loadChunk(chunk);
      }
   }

   private void load(ChunkLoadEvent e) {
      Chunk chunk = e.getChunk();
      this.loadChunk(chunk);
   }

   public void loadChunk(Chunk chunk) {
      if (this.plugin.getMm().getToSpawn().size() > 0) {
         Collection<PlayerMinion> remove = new ArrayList();

         for(PlayerMinion pm : this.plugin.getMm().getToSpawn()) {
            int x = pm.getSpawn().getBlockX() >> 4;
            int z = pm.getSpawn().getBlockZ() >> 4;
            if (chunk.getX() == x && chunk.getZ() == z) {
               pm.firstSpawn();
               ArmorStand as = pm.getArmor();
               if (as != null) {
                  UUID uuid = as.getUniqueId();
                  this.plugin.getMm().getActiveMinions().put(uuid, pm);
                  remove.add(pm);
               }
            }
         }

         if (remove.size() > 0) {
            this.plugin.getMm().getToSpawn().removeAll(remove);
            remove.clear();
         }
      }

      if (this.plugin.getMm().getMinionsToLoad().size() > 0 || this.plugin.getMm().getMinionsToRemove().size() > 0) {
         Collection<UUID> removeLoad = new ArrayList();
         Entity[] entities = chunk.getEntities();

         for(Entity ent : entities) {
            if (ent instanceof ArmorStand) {
               ArmorStand as = (ArmorStand)ent;
               UUID uuid = as.getUniqueId();
               if (this.plugin.getMm().getMinionsToRemove().contains(uuid)) {
                  as.remove();
               } else if (Utils.isMinionUUID(uuid)) {
                  PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getMinionsToLoad().get(uuid);
                  if (pm != null) {
                     MinionLoadEvent load = new MinionLoadEvent(pm, chunk);
                     Bukkit.getPluginManager().callEvent(load);
                     if (!load.isCancelled()) {
                        pm.setLoaded(true);
                        this.plugin.getMm().getActiveMinions().put(uuid, pm);
                        removeLoad.add(uuid);
                     }
                  }
               }
            }
         }

         if (removeLoad.size() > 0) {
            removeLoad.forEach((u) -> this.plugin.getMm().getMinionsToLoad().remove(u));
            removeLoad.clear();
         }
      }

   }

   private void unload(ChunkUnloadEvent e) {
      Chunk chunk = e.getChunk();
      if (this.plugin.getMm().getActiveMinions().size() > 0) {
         Collection<UUID> active = new ArrayList();
         Entity[] entities = chunk.getEntities();

         for(Entity ent : entities) {
            if (ent instanceof ArmorStand) {
               ArmorStand as = (ArmorStand)ent;
               UUID uuid = as.getUniqueId();
               if (Utils.isMinionUUID(uuid)) {
                  PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(uuid);
                  if (pm != null) {
                     MinionUnloadEvent unload = new MinionUnloadEvent(pm, chunk);
                     Bukkit.getPluginManager().callEvent(unload);
                     if (!unload.isCancelled()) {
                        pm.setLoaded(false);
                        this.plugin.getAdm().deleteHologram(pm);
                        this.plugin.getMm().getMinionsToLoad().put(uuid, pm);
                        active.add(uuid);
                     }
                  }
               }
            }
         }

         if (active.size() > 0) {
            active.forEach((u) -> this.plugin.getMm().getActiveMinions().remove(u));
            active.clear();
         }
      }

   }

   private boolean isInBlockRadius(Block b) {
      Location l = b.getLocation().clone().add((double)0.5F, (double)0.0F, (double)0.5F);
      Collection<Entity> entities = l.getWorld().getNearbyEntities(l, (double)2.0F, (double)2.0F, (double)2.0F);
      boolean checked = false;

      for(Entity e : entities) {
         if (e instanceof ArmorStand && !checked) {
            checked = Utils.isMinionUUID(e.getUniqueId());
         }
      }

      return checked;
   }

   private int getFoodAmount(Player p, PlayerMinion pm, Food f, ItemStack item) {
      int amount = item.getAmount();
      int actual = pm.getMinionLevel().getFood() - pm.getStat().getFood();
      int need = actual / f.getAmount();
      if (item.getAmount() > need) {
         item.setAmount(amount - Math.max(need, 1));
         return pm.getMinionLevel().getFood();
      } else {
         p.setItemInHand((ItemStack)null);
         return amount * f.getAmount();
      }
   }

   private int getHealthAmount(Player p, PlayerMinion pm, ItemStack item) {
      int amount = item.getAmount();
      int actual = pm.getMinionLevel().getHealth() - pm.getStat().getHealth();
      int need = actual / 5;
      if (item.getAmount() > need) {
         item.setAmount(amount - Math.max(need, 1));
         return pm.getMinionLevel().getHealth();
      } else {
         p.setItemInHand((ItemStack)null);
         return amount * 5;
      }
   }

   private boolean check(ItemStack i1, ItemStack i2) {
      ItemStack item1 = i1.clone();
      item1.setAmount(1);
      ItemStack item2 = i2.clone();
      item2.setAmount(1);
      return item1.equals(item2);
   }
}
