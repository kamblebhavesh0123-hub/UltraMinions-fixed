package io.github.Leonardo0013YT.UltraMinions.minions;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class MinionSell {
   private HashMap<ItemStack, Integer> items;
   private ArrayList<ItemStack> sell;
   private int realized;
   private int chest;

   public MinionSell(HashMap<ItemStack, Integer> items, ArrayList<ItemStack> sell, int realized, int chest) {
      this.items = items;
      this.sell = sell;
      this.realized = realized;
      this.chest = chest;
   }

   public HashMap<ItemStack, Integer> getItems() {
      return this.items;
   }

   public ArrayList<ItemStack> getSell() {
      return this.sell;
   }

   public int getChest() {
      return this.chest;
   }

   public int getRealized() {
      return this.realized;
   }
}
