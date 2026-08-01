package io.github.Leonardo0013YT.UltraMinions.craft;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Craft implements Cloneable {
   private final HashMap<ItemStack, Integer> atLeast = new HashMap();
   private final ItemStack[] matrix;
   private final ItemStack result;
   private final String key;
   private final String permission;
   private final int level;
   private final boolean minionCraft;
   private final boolean isCraft;
   private final String url;

   public Craft(ItemStack[] matrix, ItemStack result, String key, String permission, int level, boolean minionCraft, boolean isCraft, String url) {
      this.matrix = matrix;
      this.result = result;
      this.key = key;
      this.permission = permission;
      this.level = level;
      this.minionCraft = minionCraft;
      this.isCraft = isCraft;
      this.url = url;

      for(ItemStack at : matrix) {
         if (at != null && !at.getType().equals(Material.AIR)) {
            ItemStack i = at.clone();
            int a = 0;

            for(ItemStack it : this.atLeast.keySet()) {
               if (i.isSimilar(it)) {
                  a += (Integer)this.atLeast.get(it);
               }
            }

            a += i.getAmount();
            i.setAmount(1);
            this.atLeast.put(i, a);
         }
      }

   }

   public String getPermission() {
      return this.permission;
   }

   public boolean isCraft() {
      return this.isCraft;
   }

   public boolean isMinionCraft() {
      return this.minionCraft;
   }

   public String getKey() {
      return this.key;
   }

   public int getLevel() {
      return this.level;
   }

   public HashMap<ItemStack, Integer> getAtLeast() {
      return this.atLeast;
   }

   public ItemStack[] getMatrix() {
      return this.matrix;
   }

   public ItemStack getResult() {
      if (this.minionCraft) {
         Minion m = Main.get().getMm().getMinion(this.key);
         return m.getMinionLevelByLevel(this.level).getMinionHead(this.url);
      } else {
         return this.result;
      }
   }

   public boolean checkRequired(ItemStack[] input, CallBackAPI<HashMap<Integer, Integer>> now) {
      HashMap<Integer, Integer> values = new HashMap();
      boolean passed = true;

      for(int i = 0; i < input.length; ++i) {
         ItemStack need = this.matrix[i];
         if (need == null) {
            need = this.matrix[i] = new ItemStack(Material.AIR);
         }

         ItemStack gived = input[i].clone();
         boolean na = need.getType().equals(Material.AIR);
         boolean gi = gived.getType().equals(Material.AIR);
         if (na) {
            if (!gi) {
               passed = false;
               break;
            }
         } else {
            if (need.getAmount() > gived.getAmount()) {
               passed = false;
               break;
            }

            gived.setAmount(need.getAmount());
            if (!gived.equals(need)) {
               passed = false;
               break;
            }

            values.put(i, input[i].getAmount() - need.getAmount());
         }
      }

      if (passed) {
         now.done(values);
      }

      return passed;
   }

   public Craft clone() {
      return new Craft(this.matrix, this.result, this.key, this.permission, this.level, this.minionCraft, this.isCraft, this.url);
   }
}
