package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetupMinion {
   private HashMap<Integer, SetupMinionLevel> levels = new HashMap();
   private MinionType type;
   private ItemStack place;
   private EntityType spawn;
   private List<String> lore;
   private List<ItemStack> giveInInv = new ArrayList();
   private List<ItemStack> compressor = new ArrayList();
   private List<ItemStack> autoSmelt = new ArrayList();
   private String key;
   private String title;
   private int red;
   private int blue;
   private int green;
   private double priceNormalSell;
   private double priceSmeltedSell;
   private double priceCompressedSell;
   private boolean compressorEnabled;
   private boolean smeltEnabled;
   private boolean autoSellEnabled;
   private boolean fuelEnabled;
   private SetupMinionLevel actual;
   private Main plugin;

   public SetupMinion(Main plugin) {
      this.plugin = plugin;
      this.type = MinionType.MINER;
      this.spawn = EntityType.ZOMBIE;
      this.place = new ItemStack(Material.COBBLESTONE);
      this.key = this.place.getType().name().toLowerCase();
      this.giveInInv.add(new ItemStack(Material.COBBLESTONE));
      this.compressor.add(new ItemStack(Material.COBBLESTONE));
      this.autoSmelt.add(new ItemStack(Material.STONE));
      this.red = 92;
      this.blue = 92;
      this.green = 92;
      this.priceNormalSell = (double)2.0F;
      this.priceSmeltedSell = (double)4.0F;
      this.priceCompressedSell = (double)2.0F;
      this.compressorEnabled = false;
      this.autoSellEnabled = true;
      this.smeltEnabled = true;
      this.fuelEnabled = true;
      this.lore = new ArrayList(Arrays.asList("§7This is a default lore", "§7of Minion.", "§7", "§eRight click to place"));
      this.title = "§8Cobblestone Minion";
   }

   public SetupMinion(Main plugin, Minion m) {
      this.plugin = plugin;
      this.type = m.getType();
      this.spawn = m.getSpawn();
      this.place = m.getPlace();
      this.key = m.getKey();
      this.giveInInv = m.getGiveInInv().getItems();
      this.compressor = m.getCompressor().getItems();
      this.autoSmelt = m.getSmelt().getItems();
      this.red = m.getRed();
      this.blue = m.getBlue();
      this.green = m.getGreen();
      this.priceNormalSell = m.getPriceNormalSell();
      this.priceSmeltedSell = m.getPriceSmeltedSell();
      this.priceCompressedSell = m.getPriceCompressedSell();
      this.compressorEnabled = m.isCompressorEnabled();
      this.autoSellEnabled = m.isAutoSellEnabled();
      this.smeltEnabled = m.isSmeltEnabled();
      this.fuelEnabled = m.isFuelEnabled();
      this.lore = m.getLore();
      this.title = m.getTitle();

      for(MinionLevel ml : m.getLevels().values()) {
         this.levels.put(ml.getLevel() - 1, new SetupMinionLevel(ml));
      }

   }

   public void saveLevel(Player p) {
      if (this.actual != null) {
         this.levels.put(this.levels.size(), this.actual);
         this.actual = null;
         p.sendMessage(this.plugin.getLang().get("setup.saveLevel"));
      }
   }

   public void save(Player p) {
      File dataFolder = new File(this.plugin.getDataFolder(), "minions");
      if (!dataFolder.exists()) {
         dataFolder.mkdirs();
      }

      File file = new File(dataFolder, this.key + ".yml");
      YamlConfiguration minion = YamlConfiguration.loadConfiguration(file);
      minion.set("minions." + this.key, (Object)null);
      minion.set("minions." + this.key + ".key", this.key);
      minion.set("minions." + this.key + ".title", this.title.replaceAll("§", "&"));
      minion.set("minions." + this.key + ".place", this.place);
      minion.set("minions." + this.key + ".lore", this.lore);
      minion.set("minions." + this.key + ".type", this.type.name());
      minion.set("minions." + this.key + ".spawn", this.spawn.name());
      int a = 0;

      for(ItemStack i : this.compressor) {
         minion.set("minions." + this.key + ".compressor." + a + ".item", i);
         ++a;
      }

      int a1 = 0;

      for(ItemStack i : this.autoSmelt) {
         minion.set("minions." + this.key + ".smelt." + a1 + ".item", i);
         ++a1;
      }

      int a2 = 0;

      for(ItemStack i : this.giveInInv) {
         minion.set("minions." + this.key + ".giveInInv." + a2 + ".item", i);
         ++a2;
      }

      minion.set("minions." + this.key + ".priceNormalSell", this.priceNormalSell);
      minion.set("minions." + this.key + ".priceSmeltedSell", this.priceSmeltedSell);
      minion.set("minions." + this.key + ".priceCompressedSell", this.priceCompressedSell);
      minion.set("minions." + this.key + ".compressorEnabled", this.compressorEnabled);
      minion.set("minions." + this.key + ".smeltEnabled", this.smeltEnabled);
      minion.set("minions." + this.key + ".autoSellEnabled", this.autoSellEnabled);
      minion.set("minions." + this.key + ".fuelEnabled", this.fuelEnabled);
      minion.set("minions." + this.key + ".red", this.red);
      minion.set("minions." + this.key + ".blue", this.blue);
      minion.set("minions." + this.key + ".green", this.green);

      for(SetupMinionLevel sml : this.levels.values()) {
         int level = sml.getLevel();
         String path = "minions." + this.key + ".levels." + (level - 1);
         String var10001 = this.getTitle();
         sml.setLevelTitle(var10001 + " " + Utils.IntegerToRomanNumeral(level));
         minion.set(path + ".levelTitle", sml.getLevelTitle());
         minion.set(path + ".isCraft", sml.isCraft());
         minion.set(path + ".isLevel", sml.isLevel());
         minion.set(path + ".isCoins", sml.isCoins());
         minion.set(path + ".level", level);
         minion.set(path + ".url", sml.getUrl());
         minion.set(path + ".delay", sml.getDelay());
         minion.set(path + ".max", sml.getMax());
         minion.set(path + ".upgradeCoins", sml.getUpgradeCoins());
         minion.set(path + ".upgradeLevels", sml.getUpgradeLevel());
         minion.set(path + ".food", sml.getFood());
         minion.set(path + ".health", sml.getFood());
         minion.set(path + ".workTime", sml.getWorkTime());
         minion.set(path + ".sleep", sml.getSleep());
         minion.set(path + ".craft", (Object)null);
         if (sml.getCraft() != null) {
            if (sml.getCraft().getResult() == null) {
               minion.set(path + ".craft.result", sml.getUrl());
            } else {
               minion.set(path + ".craft.result", sml.getCraft().getResult());
            }

            minion.set(path + ".craft.permission", sml.getCraft().getPermission());

            for(int i = 0; i < sml.getCraft().getMatrix().length; ++i) {
               minion.set(path + ".craft.items." + i, sml.getCraft().getMatrix()[i]);
            }
         }
      }

      try {
         minion.save(file);
      } catch (IOException var13) {
      }

      p.sendMessage(this.plugin.getLang().get("setup.save"));
   }

   public int getBlue() {
      return this.blue;
   }

   public void setBlue(int blue) {
      this.blue = blue;
   }

   public int getRed() {
      return this.red;
   }

   public void setRed(int red) {
      this.red = red;
   }

   public int getGreen() {
      return this.green;
   }

   public void setGreen(int green) {
      this.green = green;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public List<ItemStack> getAutoSmelt() {
      return this.autoSmelt;
   }

   public List<ItemStack> getCompressor() {
      return this.compressor;
   }

   public List<ItemStack> getGiveInInv() {
      return this.giveInInv;
   }

   public MinionType getType() {
      return this.type;
   }

   public void setType(MinionType type) {
      this.type = type;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public ItemStack getPlace() {
      return this.place;
   }

   public void setPlace(ItemStack place) {
      this.place = place;
   }

   public EntityType getSpawn() {
      return this.spawn;
   }

   public void setSpawn(EntityType spawn) {
      this.spawn = spawn;
   }

   public double getPriceNormalSell() {
      return this.priceNormalSell;
   }

   public void setPriceNormalSell(double priceNormalSell) {
      this.priceNormalSell = priceNormalSell;
   }

   public double getPriceSmeltedSell() {
      return this.priceSmeltedSell;
   }

   public void setPriceSmeltedSell(double priceSmeltedSell) {
      this.priceSmeltedSell = priceSmeltedSell;
   }

   public double getPriceCompressedSell() {
      return this.priceCompressedSell;
   }

   public void setPriceCompressedSell(double priceCompressedSell) {
      this.priceCompressedSell = priceCompressedSell;
   }

   public boolean isCompressorEnabled() {
      return this.compressorEnabled;
   }

   public void setCompressorEnabled(boolean compressorEnabled) {
      this.compressorEnabled = compressorEnabled;
   }

   public boolean isSmeltEnabled() {
      return this.smeltEnabled;
   }

   public void setSmeltEnabled(boolean smeltEnabled) {
      this.smeltEnabled = smeltEnabled;
   }

   public boolean isAutoSellEnabled() {
      return this.autoSellEnabled;
   }

   public void setAutoSellEnabled(boolean autoSellEnabled) {
      this.autoSellEnabled = autoSellEnabled;
   }

   public boolean isFuelEnabled() {
      return this.fuelEnabled;
   }

   public void setFuelEnabled(boolean fuelEnabled) {
      this.fuelEnabled = fuelEnabled;
   }

   public SetupMinionLevel getActual() {
      return this.actual;
   }

   public void setActual(SetupMinionLevel actual) {
      this.actual = actual;
   }

   public HashMap<Integer, SetupMinionLevel> getLevels() {
      return this.levels;
   }

   public List<String> getLore() {
      return this.lore;
   }
}
