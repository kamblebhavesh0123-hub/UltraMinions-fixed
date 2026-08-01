package io.github.Leonardo0013YT.UltraMinions.minions;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public abstract class Minion {
   private final File f;
   public Main plugin;
   private HashMap<Integer, MinionLevel> levels = new HashMap();
   private String url;
   private String key;
   private String title;
   private String animation;
   private ItemStack place;
   private ItemStack handItem;
   private EntityType spawn;
   private int red;
   private int blue;
   private int green;
   private int damage;
   private double priceNormalSell;
   private double priceSmeltedSell;
   private double priceCompressedSell;
   private boolean compressorEnabled;
   private boolean smeltEnabled;
   private boolean autoSellEnabled;
   private boolean fuelEnabled;
   private MinionItem giveInInv;
   private MinionItem compressor;
   private MinionItem smelt;
   private List<String> lore;
   private MinionType type;

   public Minion(Main plugin, YamlConfiguration minion, String path, File f) {
      this.plugin = plugin;
      this.f = f;
      this.key = minion.getString(path + ".key");
      this.title = minion.getString(path + ".title").replaceAll("&", "§");
      List<String> lore = new ArrayList();
      minion.getStringList(path + ".lore").forEach((l) -> lore.add(l.replaceAll("&", "§")));
      this.lore = lore;
      this.url = minion.getString(path + ".url");
      this.place = minion.getItemStack(path + ".place");
      this.red = minion.getInt(path + ".red");
      this.blue = minion.getInt(path + ".blue");
      this.green = minion.getInt(path + ".green");
      if (minion.isSet(path + ".spawn")) {
         this.spawn = EntityType.valueOf(minion.getString(path + ".spawn"));
      }

      if (minion.isSet(path + ".compressor")) {
         ConfigurationSection conf = minion.getConfigurationSection(path + ".compressor");
         ArrayList<ItemStack> items = new ArrayList();

         for(String c : conf.getKeys(false)) {
            ItemStack i = minion.getItemStack(path + ".compressor." + c + ".item");
            items.add(i);
            this.compressor = new MinionItem(items);
         }
      }

      if (minion.isSet(path + ".smelt")) {
         ConfigurationSection conf2 = minion.getConfigurationSection(path + ".smelt");
         ArrayList<ItemStack> items = new ArrayList();

         for(String c : conf2.getKeys(false)) {
            ItemStack i = minion.getItemStack(path + ".smelt." + c + ".item");
            items.add(i);
            this.smelt = new MinionItem(items);
         }
      }

      if (minion.isSet(path + ".giveInInv")) {
         ConfigurationSection conf3 = minion.getConfigurationSection(path + ".giveInInv");
         ArrayList<ItemStack> items = new ArrayList();

         for(String c : conf3.getKeys(false)) {
            ItemStack i = minion.getItemStack(path + ".giveInInv." + c + ".item");
            items.add(i);
            this.giveInInv = new MinionItem(items);
         }
      }

      this.priceNormalSell = minion.getDouble(path + ".priceNormalSell");
      this.priceSmeltedSell = minion.getDouble(path + ".priceSmeltedSell");
      this.priceCompressedSell = minion.getDouble(path + ".priceCompressedSell");
      this.compressorEnabled = minion.getBoolean(path + ".compressorEnabled");
      this.smeltEnabled = minion.getBoolean(path + ".smeltEnabled");
      this.autoSellEnabled = minion.getBoolean(path + ".autoSellEnabled");
      this.fuelEnabled = minion.getBoolean(path + ".fuelEnabled");
      Utils.check(path + ".damage", 5, minion, f);
      this.damage = minion.getInt(path + ".damage", 5);
      Utils.check(path + ".animation", "picar.animc", minion, f);
      this.animation = minion.getString(path + ".animation", "picar.animc");
      this.type = MinionType.valueOf(minion.getString(path + ".type"));
      Utils.check(path + ".handItem", this.type.getHandItem().name(), minion, f);
      this.handItem = new ItemStack(Material.valueOf(minion.getString(path + ".handItem")));
      ConfigurationSection levels = minion.getConfigurationSection(path + ".levels");

      for(String l : levels.getKeys(false)) {
         int level = Integer.parseInt(l);
         plugin.sendDebugMessage("§dTrying load minion level §a" + level + "§d.");
         this.levels.put(level, new MinionLevel(plugin, this, minion, path + ".levels." + l, f));
         plugin.sendDebugMessage("§dMinion Level §a" + level + "§d has been loaded.");
      }

   }

   public String getUrl() {
      return this.url;
   }

   public abstract void update(PlayerMinion var1, ArmorStand var2, PlayerMinionStat var3, Location var4, CallBackAPI<Boolean> var5);

   public abstract boolean check(Location var1);

   public ItemStack getHandItem() {
      return this.handItem;
   }

   public String getAnimation() {
      return this.animation;
   }

   public int getDamage() {
      return this.damage;
   }

   public EntityType getSpawn() {
      return this.spawn;
   }

   public ItemStack getPlace() {
      return this.place != null && !this.place.getType().equals(Material.AIR) ? this.place : new ItemStack(Material.STONE);
   }

   public int getRed() {
      return this.red;
   }

   public int getBlue() {
      return this.blue;
   }

   public int getGreen() {
      return this.green;
   }

   public List<String> getLore() {
      return this.lore;
   }

   public MinionType getType() {
      return this.type;
   }

   public String getKey() {
      return this.key;
   }

   public MinionLevel getMinionLevelByLevel(int level) {
      return (MinionLevel)this.levels.get(level - 1);
   }

   public double getPriceNormalSell() {
      return this.priceNormalSell;
   }

   public double getPriceSmeltedSell() {
      return this.priceSmeltedSell;
   }

   public double getPriceCompressedSell() {
      return this.priceCompressedSell;
   }

   public boolean isCompressorEnabled() {
      return this.compressorEnabled;
   }

   public boolean isSmeltEnabled() {
      return this.smeltEnabled;
   }

   public boolean isAutoSellEnabled() {
      return this.autoSellEnabled;
   }

   public boolean isFuelEnabled() {
      return this.fuelEnabled;
   }

   public MinionItem getCompressor() {
      return this.compressor == null ? new MinionItem(new ArrayList()) : this.compressor;
   }

   public MinionItem getGiveInInv() {
      return this.giveInInv == null ? new MinionItem(new ArrayList()) : this.giveInInv;
   }

   public MinionItem getSmelt() {
      return this.smelt == null ? new MinionItem(new ArrayList()) : this.smelt;
   }

   public String getTitle() {
      return this.title;
   }

   public HashMap<Integer, MinionLevel> getLevels() {
      return this.levels;
   }
}
