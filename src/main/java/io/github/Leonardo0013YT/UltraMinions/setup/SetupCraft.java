package io.github.Leonardo0013YT.UltraMinions.setup;

import io.github.Leonardo0013YT.UltraMinions.craft.Craft;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetupCraft {
   private ItemStack[] matrix;
   private ItemStack result;
   private String permission;

   public SetupCraft() {
      this.permission = "none";
   }

   public SetupCraft(Craft c) {
      this.permission = c.getPermission();
      this.matrix = c.getMatrix();
      if (!c.getResult().getType().equals(Material.PLAYER_HEAD)) {
         this.result = c.getResult();
      }

   }

   public String getPermission() {
      return this.permission;
   }

   public void setPermission(String permission) {
      this.permission = permission;
   }

   public ItemStack getResult() {
      return this.result;
   }

   public void setResult(ItemStack result) {
      this.result = result;
   }

   public ItemStack[] getMatrix() {
      return this.matrix;
   }

   public void setMatrix(ItemStack[] matrix) {
      this.matrix = matrix;
   }
}
