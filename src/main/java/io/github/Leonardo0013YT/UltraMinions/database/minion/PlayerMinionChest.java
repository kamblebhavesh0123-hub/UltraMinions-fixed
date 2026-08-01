package io.github.Leonardo0013YT.UltraMinions.database.minion;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class PlayerMinionChest {
   private Location loc;
   private Inventory inv;
   private Chest chest;

   public PlayerMinionChest(Location loc) {
      this.loc = loc;
      Block b = loc.getBlock();
      if (b.getType().equals(Material.CHEST)) {
         this.chest = (Chest)b.getState();
         InventoryHolder holder = this.chest.getInventory().getHolder();
         if (holder instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest)holder;
            this.inv = doubleChest.getInventory();
         } else {
            this.inv = this.chest.getInventory();
         }
      }

   }

   public Location getLoc() {
      return this.loc;
   }

   public boolean isChest() {
      return this.chest != null;
   }

   public boolean addItem(ItemStack i) {
      if (this.inv.firstEmpty() != -1) {
         this.inv.addItem(new ItemStack[]{i});
         return true;
      } else {
         return false;
      }
   }

   public int addItem(List<ItemStack> i, int max) {
      int amount = 0;

      for(ItemStack it : i) {
         if (this.inv.firstEmpty() != -1) {
            amount += it.getAmount();
            this.inv.addItem(new ItemStack[]{it});
         }
      }

      return max - amount;
   }

   public boolean isFull() {
      if (this.inv == null) {
         return false;
      } else {
         return this.inv.firstEmpty() == -1;
      }
   }

   public Inventory getInventory() {
      return this.inv;
   }
}
