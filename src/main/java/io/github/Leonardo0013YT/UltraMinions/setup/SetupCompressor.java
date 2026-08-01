package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetupCompressor {
   private Player p;
   private String name;
   private SetupCraft craft;
   private ItemStack result;
   private boolean isCraft;
   private int amount;

   public SetupCompressor(Player p, String name) {
      this.p = p;
      this.name = name;
      this.amount = 9;
      this.craft = new SetupCraft();
   }

   public void save(CallBackAPI<Boolean> backAPI) {
      Main plugin = Main.get();
      String var10000 = this.name;
      String pt = "upgrades.compressor." + var10000;
      plugin.getUpgrades().set(pt + ".name", this.name);
      plugin.getUpgrades().set(pt + ".amount", this.amount);
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

   public int getAmount() {
      return this.amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
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
}
