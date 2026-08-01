package io.github.Leonardo0013YT.UltraMinions.database;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.customs.CVector;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionChest;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionUpgrade;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionFace;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.animations.BlockBreakAnimation;
import io.github.Leonardo0013YT.UltraMinions.minions.animations.CropsUpgradeAnimation;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.minions.skins.MinionSkin;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionCactusCane;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionFarmer;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionLumberjack;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionMiner;
import io.github.Leonardo0013YT.UltraMinions.minions.types.MinionPeasant;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeFuel;
import io.github.Leonardo0013YT.UltraMinions.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerMinion {
   private Main plugin = Main.get();
   private Player p;
   private HashMap<ItemStack, Integer> items = new HashMap();
   private ArmorStand armor;
   private Minion minion;
   private Location spawn;
   private PlayerMinionStat stat;
   private PlayerMinionUpgrade upgrade;
   private PlayerMinionChest chest;
   private String key;
   private String skin;
   private String id;
   private int action;
   private int actions = 0;
   private MinionType type;
   private MinionFace minionFace;
   private MinionLevel minionLevel;
   private HashMap<String, CVector> locations = new HashMap();
   private boolean enabled = true;
   private boolean full = false;
   private boolean isChest = false;
   private boolean loaded = false;

   public PlayerMinion(Location spawn, String key, Player p) {
      this.p = p;
      this.spawn = spawn;
      this.key = key;
      this.minionFace = Utils.getCardinalDirection(p);
      this.spawn.setYaw(this.minionFace.getYaw());
      this.id = Utils.getLocationString(spawn);
      this.create();
   }

   public void setLoaded(boolean loaded) {
      this.loaded = loaded;
   }

   public String getId() {
      return this.id;
   }

   public void create() {
      this.skin = "none";
      this.minion = this.plugin.getMm().getMinion(this.key);
      this.type = this.minion.getType();
      this.enabled = this.minion.check(this.spawn);
   }

   public void firstSpawn() {
      if (this.minionLevel == null) {
         this.p.sendMessage(this.plugin.getLang().get("messages.noExistLevel"));
      } else {
         if (!this.spawn.getBlock().getType().equals(Material.BEDROCK) && !this.spawn.getBlock().getType().equals(Material.ENDER_CHEST) && !this.spawn.getBlock().getType().equals(Material.CHEST) && !this.spawn.getBlock().getType().equals(Material.TRAPPED_CHEST)) {
            this.spawn.getBlock().setType(Material.AIR);
         }

         ArrayList<Entity> entities = (ArrayList)this.spawn.getWorld().getNearbyEntities(this.spawn, (double)0.5F, (double)0.5F, (double)0.5F).stream().filter((entity) -> entity.getType().equals(EntityType.ARMOR_STAND)).collect(Collectors.toCollection(ArrayList::new));
         entities.forEach(Entity::remove);
         this.armor = (ArmorStand)this.spawn.getWorld().spawn(this.spawn, ArmorStand.class);
         this.armor.setHelmet(this.minionLevel.getHead());
         ItemStack[] ar = ItemBuilder.getArmorMinion(this.minion.getRed(), this.minion.getBlue(), this.minion.getGreen());
         this.armor.setChestplate(ar[0]);
         this.armor.setLeggings(ar[1]);
         this.armor.setBoots(ar[2]);
         this.armor.setVisible(true);
         this.armor.setGravity(false);
         this.armor.setArms(true);
         this.armor.setBasePlate(false);
         this.armor.setSmall(true);
         this.armor.setCustomNameVisible(false);
         this.setSkin(this.skin);
         this.plugin.getMm().getActiveMinions().put(this.armor.getUniqueId(), this);
         Utils.addMinionUUID(this.armor.getUniqueId());
         this.enabled = this.minion.check(this.spawn);
         this.loaded = true;
      }
   }

   public boolean isFull() {
      return this.full;
   }

   public void setFull(boolean full) {
      this.full = full;
      this.plugin.getAdm().deleteHologram(this);
      if (full) {
         this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getHm().getFullyMessage(this.type));
      }

   }

   public void destroy() {
      this.plugin.getAdm().deleteHologram(this);
      if (this.armor != null) {
         Utils.removeMinionUUID(this.armor.getUniqueId());
         this.armor.remove();
      }

      if (this.armor != null) {
         this.plugin.getMm().getMinionsToRemove().add(this.armor.getUniqueId());
      }

      try {
         ArrayList<Entity> entities = (ArrayList)this.spawn.getWorld().getNearbyEntities(this.spawn, (double)0.5F, (double)0.5F, (double)0.5F).stream().filter((entity) -> entity.getType().equals(EntityType.ARMOR_STAND)).collect(Collectors.toCollection(ArrayList::new));
         entities.forEach(Entity::remove);
      } catch (IllegalStateException var2) {
         Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            ArrayList<Entity> entities = (ArrayList)this.spawn.getWorld().getNearbyEntities(this.spawn, (double)0.5F, (double)0.5F, (double)0.5F).stream().filter((entity) -> entity.getType().equals(EntityType.ARMOR_STAND)).collect(Collectors.toCollection(ArrayList::new));
            entities.forEach(Entity::remove);
         });
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public void update() {
      if (this.armor == null) {
         this.plugin.getAdm().deleteHologram(this);
      } else if (!this.enabled) {
         if (!this.plugin.getAdm().hasHologram(this) && this.loaded) {
            this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getNoCorrectMessage(this.type)));
         }

      } else if (!this.full) {
         ++this.action;
         if (!this.plugin.getCfm().isStopOnlyFoodLow() || !this.plugin.getCfm().isFood() || this.stat.getFood() > 0) {
            if (this.plugin.getCfm().isHealth() && this.plugin.getCfm().isFood()) {
               if (this.stat.getHealth() <= 0 && this.stat.getFood() <= 0) {
                  if (!this.plugin.getAdm().hasHologram(this) && this.loaded) {
                     this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getNoHealthNoFood(this.type)));
                  }

                  return;
               }
            } else if (this.plugin.getCfm().isHealth() && this.stat.getHealth() <= 0) {
               if (!this.plugin.getAdm().hasHologram(this) && this.loaded) {
                  this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getNoHealthNoFood(this.type)));
               }

               return;
            }

            if (this.action == this.getDelay() - 3) {
               if (this.plugin.getCfm().isOptimizeOnUnloadChunk()) {
                  if (this.loaded) {
                     this.executeAction();
                  }
               } else {
                  this.executeAction();
               }
            }

            if (this.action >= this.getDelay()) {
               this.action = 0;
               if (this.plugin.getCfm().isFood()) {
                  this.stat.removeFood(1);
               }

               if (this.full) {
                  if (this.loaded && !this.plugin.getAdm().hasHologram(this)) {
                     this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getFullyMessage(this.type)));
                  }
               } else if (this.plugin.getCfm().isOptimizeOnUnloadChunk()) {
                  if (this.loaded) {
                     this.executeFood();
                  }
               } else {
                  this.executeFood();
               }

               this.minion.update(this, this.armor, this.stat, this.spawn, (b) -> {
                  if (b) {
                     ++this.actions;
                  }

               });
            }

         }
      }
   }

   private void executeFood() {
      if (this.plugin.getCfm().isFood() && this.plugin.getCfm().isHealth()) {
         if (this.stat.getFood() < 10 && this.stat.getHealth() > 10) {
            this.plugin.getAdm().deleteHologram(this);
            this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getLowFoodMessage(this.type)));
         }
      } else if (this.plugin.getCfm().isHealth()) {
         if (this.stat.getHealth() < 10) {
            this.plugin.getAdm().deleteHologram(this);
            this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getLowHealthMessage(this.type)));
         }
      } else {
         int random = ThreadLocalRandom.current().nextInt(0, 10);
         if (!this.full && this.plugin.getCfm().isSocialHolograms()) {
            this.plugin.getAdm().deleteHologram(this);
            if (random < 3) {
               this.plugin.getAdm().createHologram(this, this.spawn, this.plugin.getAdm().parsePlaceholders(this.p, this.plugin.getHm().getSocialMessage(this.type)));
            }
         }
      }

   }

   private void executeAction() {
      Location armorC = this.armor.getLocation();
      Location s = null;
      if (this.minion instanceof MinionPeasant) {
         MinionPeasant mm = (MinionPeasant)this.minion;
         Location l = mm.checkFamerDirt(this.spawn);
         if (l != null) {
            s = l;
            this.locations.put("DIRT", new CVector(l.toVector()));
         } else {
            Location pr = mm.checkFamerProduct(this.spawn);
            if (pr != null) {
               s = pr;
               this.locations.put("PRODUCT", new CVector(pr.toVector()));
            } else {
               Location fr = mm.getArroundRandomFarmer(this.spawn);
               if (fr != null) {
                  s = fr;
                  this.locations.put("FARMER", new CVector(fr.toVector()));
               } else {
                  Location rd = mm.checkFarmerBlock(this.spawn);
                  if (rd != null) {
                     s = rd;
                     this.locations.put("BLOCK", new CVector(rd.toVector()));
                  } else {
                     List<Location> bll = mm.getArroundRandomReady(this.spawn);
                     Location bl = null;
                     if (!bll.isEmpty()) {
                        bl = (Location)bll.get(0);
                     }

                     if (bl != null) {
                        s = bl;
                        this.locations.put("READY", new CVector(bl.toVector()));
                     }
                  }
               }
            }
         }
      }

      if (this.minion instanceof MinionCactusCane) {
         MinionCactusCane mm = (MinionCactusCane)this.minion;
         Location l = mm.checkArroundAir(this.spawn);
         if (l != null) {
            s = l;
            this.locations.put("SAPPLING", new CVector(l.toVector()));
         } else {
            Location de = mm.getArroundRandomWood(this.spawn);
            s = de;
            this.plugin.getAm().addBlockAnimation(new BlockBreakAnimation(de.getBlock()));
            this.locations.put("DESTROY", new CVector(de.toVector()));
         }
      }

      if (this.minion instanceof MinionLumberjack) {
         MinionLumberjack mm = (MinionLumberjack)this.minion;
         Location l = mm.checkArroundWood(this.spawn);
         if (l != null) {
            s = l;
            this.locations.put("SAPPLING", new CVector(l.toVector()));
         } else {
            Location lo = mm.checkArroundSappling(this.spawn);
            if (lo != null) {
               s = lo;
               lo.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, lo, 0);
               this.locations.put("BUILD", new CVector(lo.toVector()));
            } else {
               Location de = mm.getArroundRandomWood(this.spawn);
               s = de;
               this.plugin.getAm().addBlockAnimation(new BlockBreakAnimation(de.getBlock()));
               this.locations.put("DESTROY", new CVector(de.toVector()));
            }
         }
      }

      if (this.minion instanceof MinionMiner) {
         MinionMiner mm = (MinionMiner)this.minion;
         Location l = mm.checkAround(this.spawn);
         if (l != null) {
            s = l;
            this.locations.put("PLACE", new CVector(l.toVector()));
         } else {
            Location lo = mm.getAroundRandom(this.spawn);
            s = lo;
            this.plugin.getAm().addBlockAnimation(new BlockBreakAnimation(lo.getBlock()));
            this.locations.put("BREAK", new CVector(lo.toVector()));
         }
      }

      if (this.minion instanceof MinionFarmer) {
         MinionFarmer mm = (MinionFarmer)this.minion;
         Location l = mm.checkFamerDirt(this.spawn);
         if (mm.getPlace().getType().equals(Material.NETHER_WART)) {
            l = null;
         }

         if (l != null) {
            s = l;
            this.locations.put("DIRT", new CVector(l.toVector()));
         } else {
            Location lo = mm.checkFamerProduct(this.spawn);
            if (lo != null) {
               s = lo;
               this.locations.put("PRODUCT", new CVector(lo.toVector()));
               this.plugin.getAm().addBlockAnimation(new CropsUpgradeAnimation(lo.getBlock()));
            } else {
               Location mt = mm.getArroundRandomFarmer(this.spawn);
               if (mt != null) {
                  s = mt;
                  this.locations.put("FARMER", new CVector(mt.toVector()));
               } else {
                  Location mf = mm.getArroundRandomReady(this.spawn);
                  if (mf != null) {
                     s = mf;
                     this.locations.put("READY", new CVector(mf.toVector()));
                  }
               }
            }
         }
      }

      if (s != null) {
         Vector toNpc = s.toVector().subtract(armorC.toVector());
         armorC.setDirection(toNpc);
         this.armor.teleport(armorC);
      }

      this.plugin.getAm().execute(this.minion.getAnimation(), this.armor);
   }

   public Location getSelected(String fase) {
      if (!this.locations.containsKey(fase)) {
         return null;
      } else {
         CVector c = (CVector)this.locations.get(fase);
         this.locations.clear();
         return this.spawn.clone().zero().add((double)c.getX(), (double)c.getY(), (double)c.getZ());
      }
   }

   public int getActions() {
      return this.actions;
   }

   public void setActions(int actions) {
      this.actions = actions;
   }

   public int getDelay() {
      UpgradeFuel uf = this.getUpgrade().getFuel();
      int delay = this.minionLevel.getDelay();
      double reduction = (double)0.0F;
      if (uf != null) {
         if (!this.plugin.getMem().ended(this.stat, uf)) {
            reduction = (double)delay * (uf.getPercent() / (double)100.0F);
         } else {
            this.getUpgrade().setFuel((UpgradeFuel)null);
         }
      }

      return (int)((double)delay - reduction);
   }

   public HashMap<ItemStack, Integer> getItems() {
      return this.items;
   }

   public void setItems(HashMap<ItemStack, Integer> items) {
      this.items = items;
   }

   public MinionLevel getMinionLevel() {
      return this.minionLevel;
   }

   public MinionFace getMinionFace() {
      return this.minionFace;
   }

   public MinionType getType() {
      return this.type;
   }

   public void setType(MinionType type) {
      this.type = type;
   }

   public String getSkin() {
      return this.skin;
   }

   public void setSkin(String skin) {
      this.skin = skin;
      if (this.armor != null) {
         if (skin.equals("none")) {
            this.armor.setHelmet(this.minionLevel.getHead());
            ItemStack[] ar = ItemBuilder.getArmorMinion(this.minion.getRed(), this.minion.getGreen(), this.minion.getBlue());
            this.armor.setChestplate(ar[0]);
            this.armor.setLeggings(ar[1]);
            this.armor.setBoots(ar[2]);
         } else {
            MinionSkin minionSkin = (MinionSkin)this.plugin.getSkm().getSkins().get(skin);
            this.armor.setHelmet(minionSkin.getHead());
            ItemStack[] ar = ItemBuilder.getArmorMinion(minionSkin.getRed(), minionSkin.getGreen(), minionSkin.getBlue());
            this.armor.setChestplate(ar[0]);
            this.armor.setLeggings(ar[1]);
            this.armor.setBoots(ar[2]);
         }
      }
   }

   public PlayerMinionChest getChest() {
      return !this.plugin.getCfm().isChestLink() ? null : this.chest;
   }

   public boolean isChest() {
      return !this.plugin.getCfm().isChestLink() ? false : this.isChest;
   }

   public void setChest(PlayerMinionChest chest) {
      this.chest = chest;
   }

   public void setChest(boolean chest) {
      this.isChest = chest;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
      this.minion = this.plugin.getMm().getMinion(key);
   }

   public Player getP() {
      return this.p;
   }

   public PlayerMinionStat getStat() {
      return this.stat;
   }

   public void setStat(PlayerMinionStat stat) {
      this.stat = stat;
      this.minionLevel = this.minion.getMinionLevelByLevel(stat.getLevel());
      if (this.skin.equals("none") && this.armor != null && this.loaded) {
         this.armor.setHelmet(this.minionLevel.getHead());
      }

   }

   public PlayerMinionUpgrade getUpgrade() {
      return this.upgrade;
   }

   public void setUpgrade(PlayerMinionUpgrade upgrade) {
      this.upgrade = upgrade;
   }

   public Location getSpawn() {
      return this.spawn;
   }

   public void setSpawn(Location spawn) {
      this.spawn = spawn;
   }

   public ArmorStand getArmor() {
      return this.armor;
   }

   public Minion getMinion() {
      return this.minion;
   }

   public int getSpaces() {
      int spaces = this.minionLevel.getMax() / 64;
      if (spaces < 1) {
         ++spaces;
      }

      return spaces;
   }
}
