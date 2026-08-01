package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetupMinionLevel {
   private boolean isLevel;
   private boolean isCoins;
   private boolean isCraft;
   private int delay;
   private int max;
   private int upgradeLevel;
   private int upgradeCoins;
   private int health;
   private int food;
   private int workTime;
   private int sleep;
   private ItemStack item;
   private String levelTitle;
   private String url;
   private int level;
   private SetupCraft craft;

   public SetupMinionLevel(SetupMinion sm, int level) {
      this.level = level;
      this.isCoins = true;
      this.isLevel = false;
      this.isCraft = false;
      this.delay = 30;
      this.max = 64;
      this.url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGMxNzU0ODUxZTM2N2U4YmViYTJhNmQ4ZjdjMmZlZGU4N2FlNzkzYWM1NDZiMGYyOTlkNjczMjE1YjI5MyJ9fX0=";
      this.upgradeCoins = 500;
      this.upgradeLevel = 10;
      this.health = 20;
      this.food = 100;
      this.workTime = 3600;
      this.sleep = 600;
      this.item = new ItemStack(Material.COBBLESTONE);
      String var10001 = sm.getTitle();
      this.levelTitle = var10001 + " " + Utils.IntegerToRomanNumeral(level);
   }

   public SetupMinionLevel(MinionLevel ml) {
      this.level = ml.getLevel();
      this.isCoins = ml.isCoins();
      this.isLevel = ml.isLevel();
      this.isCraft = ml.isCraft();
      this.delay = ml.getDelay();
      this.max = ml.getMax();
      this.upgradeCoins = ml.getUpgradeCoins();
      this.url = ml.getUrl();
      this.upgradeLevel = ml.getUpgradeLevels();
      this.health = ml.getHealth();
      this.food = ml.getFood();
      this.workTime = ml.getWorkTime();
      this.sleep = ml.getSleep();
      this.item = ml.getMinionHead();
      this.levelTitle = ml.getLevelTitle();
      if (this.isCraft) {
         this.craft = new SetupCraft(ml.getCraft());
      }

   }

   public boolean isLevel() {
      return this.isLevel;
   }

   public boolean isCoins() {
      return this.isCoins;
   }

   public void setCoins(boolean coins) {
      this.isCoins = coins;
   }

   public boolean isCraft() {
      return this.isCraft;
   }

   public int getDelay() {
      return this.delay;
   }

   public void setDelay(int delay) {
      this.delay = delay;
   }

   public int getMax() {
      return this.max;
   }

   public void setMax(int max) {
      this.max = max;
   }

   public int getUpgradeLevel() {
      return this.upgradeLevel;
   }

   public void setUpgradeLevel(int upgradeLevel) {
      this.upgradeLevel = upgradeLevel;
   }

   public int getUpgradeCoins() {
      return this.upgradeCoins;
   }

   public void setUpgradeCoins(int upgradeCoins) {
      this.upgradeCoins = upgradeCoins;
   }

   public int getHealth() {
      return this.health;
   }

   public void setHealth(int health) {
      this.health = health;
   }

   public int getFood() {
      return this.food;
   }

   public void setFood(int food) {
      this.food = food;
   }

   public int getWorkTime() {
      return this.workTime;
   }

   public void setWorkTime(int workTime) {
      this.workTime = workTime;
   }

   public int getSleep() {
      return this.sleep;
   }

   public void setSleep(int sleep) {
      this.sleep = sleep;
   }

   public ItemStack getItem() {
      return this.item;
   }

   public void setItem(ItemStack item) {
      this.item = item;
   }

   public String getLevelTitle() {
      return this.levelTitle;
   }

   public void setLevelTitle(String levelTitle) {
      this.levelTitle = levelTitle;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public int getLevel() {
      return this.level;
   }

   public void setLevel(boolean level) {
      this.isLevel = level;
   }

   public void setLevel(int level) {
      this.level = level;
   }

   public SetupCraft getCraft() {
      return this.craft;
   }

   public void setCraft(boolean craft) {
      this.isCraft = craft;
   }

   public void setCraft(SetupCraft craft) {
      this.craft = craft;
   }
}
