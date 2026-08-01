package io.github.Leonardo0013YT.UltraMinions.minions;

import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MinionItem {
   private ArrayList<ItemStack> items;
   private ArrayList<ItemStack> cached = new ArrayList();
   private int actionsToStack = 1;

   public MinionItem(ArrayList<ItemStack> items) {
      this.items = items;

      for(ItemStack item : this.items) {
         if (item != null && !item.getType().equals(Material.AIR)) {
            int a = 64 / (item.getAmount() > 0 ? item.getAmount() : 1);
            if (this.actionsToStack < a) {
               this.actionsToStack = a;
            }
         } else {
            this.actionsToStack = 64;
         }
      }

      ArrayList<ItemStack> st = this.getStack(64);

      for(int i = 0; i < 100; ++i) {
         this.cached.addAll(st);
      }

   }

   public ArrayList<ItemStack> getItems() {
      return this.items;
   }

   public int getActionsToStack() {
      return this.actionsToStack > 0 ? this.actionsToStack : 64;
   }

   public MinionSell getItems(PlayerMinion pm, int actions, int spaces, int compress, int max, MinionItem normal) {
      if (actions < compress) {
         return normal.getItems(pm, actions, spaces, max);
      } else {
         HashMap<ItemStack, Integer> items = new HashMap();
         ArrayList<ItemStack> sellItems = new ArrayList();
         List<ItemStack> st = this.getStack(max);
         int com = actions / compress;
         int nor = actions % compress;
         int sts = com / this.getActionsToStack();
         int sst = com % this.getActionsToStack();
         int sell = 0;
         int realized = 0;
         int chest = 0;
         if (sts > 0) {
            if (sts > spaces) {
               sell = sts - spaces;
               int posStacks = sts - sell;

               for(int a = 0; a < posStacks; realized += this.getActionsToStack()) {
                  for(ItemStack i : st) {
                     ItemStack it = i.clone();
                     it.setAmount(1);
                     items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
                  }

                  ++a;
               }

               for(int b = 0; b < sell && b < 20; ++b) {
                  if (pm.getChest() != null) {
                     if (!pm.getChest().isFull()) {
                        pm.getChest().addItem(st, this.getActionsToStack());
                        chest += this.getActionsToStack();
                     } else {
                        sellItems.addAll(st);
                     }
                  } else {
                     sellItems.addAll(st);
                  }
               }
            } else {
               for(int a = 0; a < sts; realized += this.getActionsToStack()) {
                  for(ItemStack i : st) {
                     ItemStack it = i.clone();
                     it.setAmount(1);
                     items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
                  }

                  ++a;
               }
            }
         }

         if (sst > 0) {
            if (sell > 0) {
               chest = this.getChest(pm, max, sellItems, sst, chest);
            } else {
               for(ItemStack i : this.getStack(sst, max)) {
                  ItemStack it = i.clone();
                  it.setAmount(1);
                  items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
               }
            }
         }

         if (nor > 0) {
            if (sell > 0) {
               if (pm.getChest() != null) {
                  if (!pm.getChest().isFull()) {
                     pm.getChest().addItem(normal.getStack(nor, max), this.getActionsToStack());
                     chest += nor;
                  } else {
                     sellItems.addAll(normal.getStack(nor, max));
                  }
               } else {
                  sellItems.addAll(normal.getStack(nor, max));
               }
            } else {
               for(ItemStack i : normal.getStack(nor, max)) {
                  ItemStack it = i.clone();
                  it.setAmount(1);
                  items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
               }
            }
         }

         return new MinionSell(items, sellItems, actions - realized, chest);
      }
   }

   private int getChest(PlayerMinion pm, int max, ArrayList<ItemStack> sellItems, int sst, int chest) {
      if (pm.getChest() != null) {
         if (!pm.getChest().isFull()) {
            pm.getChest().addItem(this.getStack(sst, max), this.getActionsToStack());
            chest += sst;
         } else {
            sellItems.addAll(this.getStack(sst, max));
         }
      } else {
         sellItems.addAll(this.getStack(sst, max));
      }

      return chest;
   }

   public MinionSell getItems(PlayerMinion pm, int actions, int spaces, int max) {
      HashMap<ItemStack, Integer> items = new HashMap();
      ArrayList<ItemStack> sellItems = new ArrayList();
      int stacks = actions / this.getActionsToStack();
      int rest = actions % this.getActionsToStack();
      int realized = 0;
      int chest = 0;
      List<ItemStack> st = this.getStack(max);
      int sell = 0;
      int used = 0;
      if (stacks > 0) {
         if (stacks > spaces) {
            sell = stacks - spaces;
            int posStacks = stacks - sell;

            for(int a = 0; a < posStacks; ++used) {
               for(ItemStack i : st) {
                  ItemStack it = i.clone();
                  it.setAmount(1);
                  items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
               }

               ++a;
               realized += this.getActionsToStack();
            }

            for(int b = 0; b < sell && b < 20; ++b) {
               if (pm.getChest() != null) {
                  if (!pm.getChest().isFull()) {
                     pm.getChest().addItem(st, this.getActionsToStack());
                     chest += this.getActionsToStack();
                     --sell;
                  } else {
                     sellItems.addAll(st);
                  }
               } else {
                  sellItems.addAll(st);
               }
            }
         } else {
            for(int a = 0; a < stacks; realized += this.getActionsToStack()) {
               ++used;

               for(ItemStack i : st) {
                  ItemStack it = i.clone();
                  it.setAmount(1);
                  items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
               }

               ++a;
            }
         }
      }

      if (rest > 0) {
         if (used < spaces && sell <= 0) {
            for(ItemStack i : this.getStack(rest, max)) {
               ItemStack it = i.clone();
               it.setAmount(1);
               items.put(it, (Integer)items.getOrDefault(it, 0) + i.getAmount());
            }

            realized += rest;
         } else {
            chest = this.getChest(pm, max, sellItems, rest, chest);
         }
      }

      return new MinionSell(items, sellItems, actions - realized, chest);
   }

   public ArrayList<ItemStack> getStack(int amount, int max) {
      ArrayList<ItemStack> items = new ArrayList();
      if (amount <= 0) {
         return items;
      } else {
         for(ItemStack i : this.items) {
            if (i != null && !i.getType().equals(Material.AIR)) {
               ItemStack item = i.clone();
               int a = i.getAmount();
               item.setAmount(Math.min(a * amount, Math.min(max, 64)));
               items.add(item);
            }
         }

         return items;
      }
   }

   public ArrayList<ItemStack> getStack(int max) {
      ArrayList<ItemStack> items = new ArrayList();

      for(ItemStack i : this.items) {
         if (i != null && !i.getType().equals(Material.AIR)) {
            ItemStack item = i.clone();
            item.setAmount(Math.min(max, 64));
            items.add(item);
         }
      }

      return items;
   }
}
