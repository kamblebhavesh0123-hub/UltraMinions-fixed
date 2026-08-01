package io.github.Leonardo0013YT.UltraMinions.database.data;

import io.github.Leonardo0013YT.UltraMinions.interfaces.MinionSave;

public class PlayerMinionSave implements MinionSave {
   private String type;
   private String key;
   private String skin = "none";
   private String loc;
   private String chest;
   private String fuel = "none";
   private String autoSell = "none";
   private String compressor = "none";
   private String autoSmelt = "none";
   private boolean isChest;
   private long fuelTime;
   private int actions;
   private int level;
   private int generated;
   private int work;
   private int sleep;
   private int food;
   private int health;
   private int totalFuel;
   private int fuelAmount;

   public int getFuelAmount() {
      return this.fuelAmount;
   }

   public void setFuelAmount(int fuelAmount) {
      this.fuelAmount = fuelAmount;
   }

   public int getTotalFuel() {
      return this.totalFuel;
   }

   public void setTotalFuel(int totalFuel) {
      this.totalFuel = totalFuel;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getKey() {
      return this.key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getSkin() {
      return this.skin;
   }

   public void setSkin(String skin) {
      this.skin = skin;
   }

   public String getLoc() {
      return this.loc;
   }

   public void setLoc(String loc) {
      this.loc = loc;
   }

   public String getChest() {
      return this.chest;
   }

   public String getFuel() {
      return this.fuel;
   }

   public void setFuel(String fuel) {
      this.fuel = fuel;
   }

   public String getAutoSell() {
      return this.autoSell;
   }

   public void setAutoSell(String autoSell) {
      this.autoSell = autoSell;
   }

   public String getCompressor() {
      return this.compressor;
   }

   public void setCompressor(String compressor) {
      this.compressor = compressor;
   }

   public String getAutoSmelt() {
      return this.autoSmelt;
   }

   public void setAutoSmelt(String autoSmelt) {
      this.autoSmelt = autoSmelt;
   }

   public boolean isChest() {
      return this.isChest;
   }

   public void setChest(String chest) {
      this.chest = chest;
   }

   public void setChest(boolean chest) {
      this.isChest = chest;
   }

   public long getFuelTime() {
      return this.fuelTime;
   }

   public void setFuelTime(long fuelTime) {
      this.fuelTime = fuelTime;
   }

   public int getActions() {
      return this.actions;
   }

   public void setActions(int actions) {
      this.actions = actions;
   }

   public int getLevel() {
      return this.level;
   }

   public void setLevel(int level) {
      this.level = level;
   }

   public int getGenerated() {
      return this.generated;
   }

   public void setGenerated(int generated) {
      this.generated = generated;
   }

   public int getWork() {
      return this.work;
   }

   public void setWork(int work) {
      this.work = work;
   }

   public int getSleep() {
      return this.sleep;
   }

   public void setSleep(int sleep) {
      this.sleep = sleep;
   }

   public int getFood() {
      return this.food;
   }

   public void setFood(int food) {
      this.food = food;
   }

   public int getHealth() {
      return this.health;
   }

   public void setHealth(int health) {
      this.health = health;
   }
}
