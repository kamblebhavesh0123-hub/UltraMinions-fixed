package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetupFuel {
   private Player p;
   private String name;
   private SetupCraft craft;
   private ItemStack result;
   private int duration;
   private double percent;
   private boolean isCraft;

   public SetupFuel(Player p, String name) {
      this.p = p;
      this.name = name;
      this.duration = 600;
      this.percent = (double)15.0F;
      this.craft = new SetupCraft();
   }

   public void save(CallBackAPI<Boolean> backAPI) {
      Main plugin = Main.get();
      String var10000 = this.name;
      String pt = "upgrades.fuel." + var10000;
      plugin.getUpgrades().set(pt + ".name", this.name);
      plugin.getUpgrades().set(pt + ".duration", this.duration);
      plugin.getUpgrades().set(pt + ".percent", this.percent);
      plugin.getUpgrades().set(pt + ".isCraft", this.isCraft);
      plugin.getUpgrades().set(pt + ".result", this.result);
      backAPI.done(this.result != null);
      if (this.isCraft) {
         plugin.getUpgrades().set(pt + ".craft.permission", this.craft.getPermission());

         for(int i = 0; i < 9; ++i) {
            plugin.getUpgrades().set(pt + ".craft.items." + i, this.craft.getMatrix()[i]);
         }
      }

      plugin.getUpgrades().save();
   }

   public double getPercent() {
      return this.percent;
   }

   public void setPercent(double percent) {
      this.percent = percent;
   }

   public boolean isCraft() {
      return this.isCraft;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public void setResult(ItemStack result) {
      this.result = result;
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

   public int getDuration() {
      return this.duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }
}
