package io.github.Leonardo0013YT.UltraMinions.minions.levels;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.craft.Craft;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraMinions.utils.NBTEditor;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MinionLevel {
   private int level;
   private int health;
   private int food;
   private int workTime;
   private int sleep;
   private int upgradeCoins;
   private int upgradeLevels;
   private int max;
   private int delay;
   private int upgrades = 0;
   private boolean isCraft;
   private boolean isLevel;
   private boolean isCoins;
   private boolean isCraftingTable;
   private String levelTitle;
   private String url;
   private Main plugin;
   private Minion minion;
   private Craft craft;

   public MinionLevel(Main plugin, Minion minion, YamlConfiguration config, String path, File f) {
      this.plugin = plugin;
      this.minion = minion;
      this.level = config.getInt(path + ".level");
      if (!config.isSet(path + ".url")) {
         config.set(path + ".url", minion.getUrl());

         try {
            config.save(f);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      this.url = config.getString(path + ".url");
      this.levelTitle = config.getString(path + ".levelTitle").replaceAll("&", "§");
      this.health = config.getInt(path + ".health");
      this.food = config.getInt(path + ".food");
      this.workTime = config.getInt(path + ".workTime");
      this.sleep = config.getInt(path + ".sleep");
      this.upgradeCoins = config.getInt(path + ".upgradeCoins");
      this.upgradeLevels = config.getInt(path + ".upgradeLevels");
      this.max = config.getInt(path + ".max");
      this.delay = config.getInt(path + ".delay");
      this.isCraft = config.getBoolean(path + ".isCraft");
      this.isLevel = config.getBoolean(path + ".isLevel");
      this.isCoins = config.getBoolean(path + ".isCoins");
      Utils.check(path + ".isCraftingTable", true, config, f);
      this.isCraftingTable = config.getBoolean(path + ".isCraftingTable", true);
      if (this.isCraft) {
         ++this.upgrades;
      }

      if (this.isLevel) {
         ++this.upgrades;
      }

      if (this.isCoins) {
         ++this.upgrades;
      }

      if (this.isCraft) {
         this.craft = plugin.getCm().loadMinionCustomCraft(this, path, minion.getKey(), this.level, (b) -> this.isCraft = b);
      }

   }

   public String getUrl() {
      return this.url;
   }

   public ItemStack getHead() {
      return ItemBuilder.createSkull("", "", this.url);
   }

   public ItemStack getMinionHead(String url) {
      ItemStack head = NBTEditor.getHead(url);
      ItemMeta headM = head.getItemMeta();
      headM.setDisplayName(this.levelTitle);
      List<String> lore = new ArrayList();

      for(String l : this.minion.getLore()) {
         lore.add(l.replaceAll("<time>", "" + this.delay).replaceAll("<max>", "" + this.max).replaceAll("<generated>", "0"));
      }

      headM.setLore(lore);
      head.setItemMeta(headM);
      head = (ItemStack)NBTEditor.set(head, this.level, "LEVEL");
      head = (ItemStack)NBTEditor.set(head, this.minion.getKey(), "KEY");
      head = (ItemStack)NBTEditor.set(head, this.food, "FOOD");
      head = (ItemStack)NBTEditor.set(head, this.health, "HEALTH");
      head = (ItemStack)NBTEditor.set(head, this.workTime, "WORKTIME");
      head = (ItemStack)NBTEditor.set(head, this.sleep, "SLEEP");
      head = (ItemStack)NBTEditor.set(head, 0L, "FUELTIME");
      head = (ItemStack)NBTEditor.set(head, "none", "SKIN");
      return head;
   }

   public ItemStack getMinionHead() {
      ItemStack head = NBTEditor.getHead(this.url);
      ItemMeta headM = head.getItemMeta();
      headM.setDisplayName(this.levelTitle);
      List<String> lore = new ArrayList();

      for(String l : this.minion.getLore()) {
         lore.add(l.replaceAll("<time>", "" + this.delay).replaceAll("<max>", "" + this.max).replaceAll("<generated>", "0"));
      }

      headM.setLore(lore);
      head.setItemMeta(headM);
      head = (ItemStack)NBTEditor.set(head, this.level, "LEVEL");
      head = (ItemStack)NBTEditor.set(head, this.minion.getKey(), "KEY");
      head = (ItemStack)NBTEditor.set(head, this.food, "FOOD");
      head = (ItemStack)NBTEditor.set(head, this.health, "HEALTH");
      head = (ItemStack)NBTEditor.set(head, this.workTime, "WORKTIME");
      head = (ItemStack)NBTEditor.set(head, this.sleep, "SLEEP");
      head = (ItemStack)NBTEditor.set(head, 0L, "FUELTIME");
      head = (ItemStack)NBTEditor.set(head, "none", "SKIN");
      return head;
   }

   public ItemStack getMinionHead(PlayerMinion pm) {
      ItemStack head = NBTEditor.getHead(this.url);
      ItemMeta headM = head.getItemMeta();
      headM.setDisplayName(this.levelTitle);
      List<String> lore = new ArrayList();

      for(String l : this.minion.getLore()) {
         lore.add(l.replaceAll("<time>", "" + pm.getDelay()).replaceAll("<max>", "" + this.max).replaceAll("<generated>", "" + pm.getStat().getGenerated()));
      }

      headM.setLore(lore);
      head.setItemMeta(headM);
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getLevel(), "LEVEL");
      head = (ItemStack)NBTEditor.set(head, this.minion.getKey(), "KEY");
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getFood(), "FOOD");
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getHealth(), "HEALTH");
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getWork(), "WORKTIME");
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getSleep(), "SLEEP");
      head = (ItemStack)NBTEditor.set(head, pm.getStat().getFuel(), "FUELTIME");
      head = (ItemStack)NBTEditor.set(head, pm.getSkin(), "SKIN");
      if (pm.getUpgrade().getAutoSell() != null) {
         head = (ItemStack)NBTEditor.set(head, pm.getUpgrade().getAutoSell().getName(), "AUTOSELL");
      }

      if (pm.getUpgrade().getAutoSmelt() != null) {
         head = (ItemStack)NBTEditor.set(head, pm.getUpgrade().getAutoSmelt().getName(), "AUTOSMELT");
      }

      if (pm.getUpgrade().getCompressor() != null) {
         head = (ItemStack)NBTEditor.set(head, pm.getUpgrade().getCompressor().getName(), "COMPRESSOR");
      }

      if (pm.getUpgrade().getFuel() != null) {
         head = (ItemStack)NBTEditor.set(head, pm.getUpgrade().getFuel().getName(), "FUEL");
      }

      return head;
   }

   public Minion getMinion() {
      return this.minion;
   }

   public Craft getCraft() {
      return this.craft == null ? null : this.craft.clone();
   }

   public int getUpgrades() {
      return this.upgrades;
   }

   public int getLevel() {
      return this.level;
   }

   public String getLevelTitle() {
      return this.levelTitle;
   }

   public int getHealth() {
      return this.health;
   }

   public int getFood() {
      return this.food;
   }

   public int getWorkTime() {
      return this.workTime;
   }

   public int getSleep() {
      return this.sleep;
   }

   public int getUpgradeCoins() {
      return this.upgradeCoins;
   }

   public int getUpgradeLevels() {
      return this.upgradeLevels;
   }

   public int getMax() {
      return this.max;
   }

   public int getDelay() {
      return this.delay;
   }

   public boolean isCraft() {
      return this.craft != null && this.craft.getResult() != null && this.craft.getMatrix() != null ? this.isCraft : false;
   }

   public boolean isCraftingTable() {
      return this.isCraftingTable;
   }

   public boolean isLevel() {
      return this.isLevel;
   }

   public boolean isCoins() {
      return this.isCoins;
   }
}
