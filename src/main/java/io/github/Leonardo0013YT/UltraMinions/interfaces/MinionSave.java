package io.github.Leonardo0013YT.UltraMinions.interfaces;

public interface MinionSave {
   int getActions();

   void setActions(int var1);

   int getFuelAmount();

   void setFuelAmount(int var1);

   int getTotalFuel();

   void setTotalFuel(int var1);

   String getType();

   void setType(String var1);

   String getKey();

   void setKey(String var1);

   String getSkin();

   void setSkin(String var1);

   String getLoc();

   void setLoc(String var1);

   String getChest();

   String getFuel();

   void setFuel(String var1);

   String getAutoSell();

   void setAutoSell(String var1);

   String getCompressor();

   void setCompressor(String var1);

   String getAutoSmelt();

   void setAutoSmelt(String var1);

   boolean isChest();

   void setChest(String var1);

   void setChest(boolean var1);

   long getFuelTime();

   void setFuelTime(long var1);

   int getLevel();

   void setLevel(int var1);

   int getGenerated();

   void setGenerated(int var1);

   int getWork();

   void setWork(int var1);

   int getSleep();

   void setSleep(int var1);

   int getFood();

   void setFood(int var1);

   int getHealth();

   void setHealth(int var1);
}
