package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionUpgrade;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionCactusCane;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionCollector;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionFarmer;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionFisher;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionHunter;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionLumberjack;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionMiner;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionPeasant;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionRancher;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionSeller;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSell;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeCompressor;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MinionManager {
   private Main plugin;
   private Map<Player, PlayerMinion> view = new HashMap();
   private Map<String, Minion> minions = new HashMap();
   private Map<UUID, PlayerMinion> activeMinions = new HashMap();
   private Map<UUID, PlayerMinion> minionsToLoad = new HashMap();
   private HashSet<UUID> minionsToRemove = new HashSet();
   private Collection<PlayerMinion> toSpawn = new ArrayList();

   public MinionManager(Main plugin) {
      this.plugin = plugin;
      this.loadMinions();
   }

   public static void createMinion(ItemStack item, int level, int generated, int food, int health, int workTime, int sleep, long fueltime, PlayerMinion pm, PlayerMinionUpgrade upgrade, String autosell, Main plugin) {
      PlayerMinionStat pms = new PlayerMinionStat(pm, level, generated, workTime, sleep, food, health, fueltime);
      if (autosell != null) {
         UpgradeAutoSell autoSell = plugin.getUm().getAutoSell(autosell);
         if (autoSell != null) {
            upgrade.setAutoSell(autoSell);
         }
      }

      String autosmelt = NBTEditor.getString(item, "AUTOSMELT");
      if (autosmelt != null) {
         UpgradeAutoSmelt autoSmelt = plugin.getUm().getAutoSmelt(autosmelt);
         if (autoSmelt != null) {
            upgrade.setAutoSmelt(autoSmelt);
         }
      }

      String compressor = NBTEditor.getString(item, "COMPRESSOR");
      if (compressor != null) {
         UpgradeCompressor Compressor = plugin.getUm().getCompressor(compressor);
         if (Compressor != null) {
            upgrade.setCompressor(Compressor);
         }
      }

      String fuel = NBTEditor.getString(item, "FUEL");
      if (fuel != null) {
         UpgradeFuel Fuel = plugin.getUm().getFuel(fuel);
         if (Fuel != null) {
            upgrade.setFuel(Fuel);
         }
      }

      pm.setStat(pms);
      pm.setUpgrade(upgrade);
   }

   public void loadMinions() {
      this.minions.clear();
      File dataFolder = new File(this.plugin.getDataFolder(), "minions");
      if (!dataFolder.exists()) {
         dataFolder.mkdirs();
      }

      for(File f : dataFolder.listFiles()) {
         String s = f.getName().replaceAll(".yml", "");
         YamlConfiguration minion = YamlConfiguration.loadConfiguration(f);
         MinionType type = MinionType.valueOf(minion.getString("minions." + s + ".type"));
         String key = minion.getString("minions." + s + ".key");
         this.plugin.sendDebugMessage("§dTrying load §a" + key + "§d.");
         if (type.equals(MinionType.MINER)) {
            this.minions.put(key, new MinionMiner(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.FARMER)) {
            this.minions.put(key, new MinionFarmer(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.FISHER)) {
            this.minions.put(key, new MinionFisher(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.HUNTER)) {
            this.minions.put(key, new MinionHunter(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.RANCHER)) {
            this.minions.put(key, new MinionRancher(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.LUMBERJACK)) {
            this.minions.put(key, new MinionLumberjack(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.PEASANT)) {
            this.minions.put(key, new MinionPeasant(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.CACTUSCANE)) {
            this.minions.put(key, new MinionCactusCane(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.COLLECTOR)) {
            this.minions.put(key, new MinionCollector(this.plugin, minion, "minions." + s, f));
         } else if (type.equals(MinionType.SELLER)) {
            this.minions.put(key, new MinionSeller(this.plugin, minion, "minions." + s, f));
         }

         this.plugin.sendDebugMessage("§dMinion §a" + s + "§d has been loaded.");
      }

   }

   public HashSet<UUID> getMinionsToRemove() {
      return this.minionsToRemove;
   }

   public Map<String, Minion> getMinions() {
      return this.minions;
   }

   public Minion getMinion(String key) {
      return (Minion)this.minions.get(key);
   }

   public void setView(Player p, PlayerMinion pm) {
      this.view.put(p, pm);
   }

   public PlayerMinion getView(Player p) {
      return (PlayerMinion)this.view.get(p);
   }

   public void removeView(Player p) {
      this.view.remove(p);
   }

   public Collection<PlayerMinion> getToSpawn() {
      return this.toSpawn;
   }

   public void close(PlayerMinion pm) {
      HashMap<Player, PlayerMinion> cloneView = new HashMap(this.view);

      for(Player on : cloneView.keySet()) {
         if (on != null && on.isOnline()) {
            if (((PlayerMinion)cloneView.get(on)).equals(pm)) {
               on.closeInventory();
               this.view.remove(on);
            }
         } else {
            this.view.remove(on);
         }
      }

   }

   public Map<UUID, PlayerMinion> getActiveMinions() {
      return this.activeMinions;
   }

   public Map<UUID, PlayerMinion> getMinionsToLoad() {
      return this.minionsToLoad;
   }

   public void createIslandMinion(final Player p, final Location miLoc) {
      (new BukkitRunnable() {
         public void run() {
            Minion m = MinionManager.this.plugin.getMm().getMinion(MinionManager.this.plugin.getCfm().getMinionKey());
            if (m == null) {
               p.sendMessage("§cThe selected minion does not exist.");
            } else {
               ItemStack item = m.getMinionLevelByLevel(MinionManager.this.plugin.getCfm().getMinionLevel()).getMinionHead();
               int level = NBTEditor.getInt(item, "LEVEL");
               int generated = NBTEditor.getInt(item, "GENERATED");
               int food = NBTEditor.getInt(item, "FOOD");
               int health = NBTEditor.getInt(item, "HEALTH");
               int workTime = NBTEditor.getInt(item, "WORKTIME");
               int sleep = NBTEditor.getInt(item, "SLEEP");
               long fueltime = NBTEditor.getLong(item, "FUELTIME");
               String skin = NBTEditor.contains(item, "SKIN") ? NBTEditor.getString(item, "SKIN") : "none";
               PlayerMinion pm = new PlayerMinion(miLoc, MinionManager.this.plugin.getCfm().getMinionKey(), p);
               PlayerMinionUpgrade upgrade = new PlayerMinionUpgrade(pm);
               String autosell = NBTEditor.getString(item, "AUTOSELL");
               MinionManager.createMinion(item, level, generated, food, health, workTime, sleep, fueltime, pm, upgrade, autosell, MinionManager.this.plugin);
               PlayerData pd = PlayerData.getPlayerData(p);
               pd.addPlayerMinion(pm);
               pm.firstSpawn();
               pm.setSkin(skin);
            }
         }
      }).runTaskLater(this.plugin, 30L);
   }

   public void removeIslandMinion(Entity ent) {
      ArmorStand as = (ArmorStand)ent;
      UUID uuid = as.getUniqueId();
      if (Utils.isMinionUUID(uuid)) {
         if (this.plugin.getMm().getActiveMinions().containsKey(uuid)) {
            PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getActiveMinions().get(uuid);
            if (this.remove(pm)) {
               return;
            }

            this.plugin.getMm().getActiveMinions().remove(uuid);
         }

         if (this.plugin.getMm().getMinionsToLoad().containsKey(uuid)) {
            PlayerMinion pm = (PlayerMinion)this.plugin.getMm().getMinionsToLoad().get(uuid);
            if (this.remove(pm)) {
               return;
            }

            this.plugin.getMm().getMinionsToLoad().remove(uuid);
         }
      }

   }

   private boolean remove(PlayerMinion pm) {
      if (pm != null && pm.getP().isOnline()) {
         PlayerData pd = PlayerData.getPlayerUUID(pm.getP().getUniqueId());
         MinionLevel ml = pm.getMinionLevel();
         pm.destroy();
         pd.removePlayerMinion(pm);
         if (pm.getP().getInventory().firstEmpty() == -1) {
            pm.getP().getWorld().dropItem(pm.getP().getLocation(), ml.getMinionHead(pm));
         } else {
            pm.getP().getInventory().addItem(new ItemStack[]{ml.getMinionHead(pm)});
         }

         Utils.addItems(pm.getP(), pm.getItems(), pm.getMinionLevel().getMax());
         pm.setActions(0);
         return false;
      } else {
         return true;
      }
   }
}
