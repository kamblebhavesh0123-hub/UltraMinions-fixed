package io.github.Leonardo0013YT.UltraMinions.listeners;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionCraftEvent;
import io.github.Leonardo0013YT.UltraMinions.craft.Craft;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftListener implements Listener {
   private HashMap<Player, HashMap<Integer, Integer>> players = new HashMap();
   private Main plugin;

   public CraftListener(Main plugin) {
      this.plugin = plugin;
   }

   @EventHandler
   public void onCraft(InventoryClickEvent e) {
      if (e.getClickedInventory() != null) {
         if (e.getClickedInventory().getType().equals(InventoryType.WORKBENCH)) {
            final Player p = (Player)e.getWhoClicked();
            if (!(e.getClickedInventory() instanceof CraftingInventory)) {
               return;
            }

            final CraftingInventory crafting = (CraftingInventory)e.getInventory();
            if (e.getSlot() == 0 && crafting.getItem(0) != null && this.players.containsKey(p)) {
               ItemStack result = crafting.getItem(0);
               ItemStack cursor = p.getItemOnCursor();
               if (cursor != null && !cursor.getType().equals(Material.AIR)) {
                  if (!this.checkEquals(cursor.clone(), result.clone())) {
                     e.setCancelled(true);
                     return;
                  }

                  if (cursor.getAmount() + result.getAmount() > 64) {
                     e.setCancelled(true);
                     return;
                  }

                  ItemStack now = result.clone();
                  now.setAmount(cursor.getAmount() + result.getAmount());
                  p.setItemOnCursor(now);
               } else {
                  p.setItemOnCursor(result);
               }

               this.clear(p, crafting, (HashMap)this.players.get(p));
               this.players.remove(p);
            }

            this.players.remove(p);
            (new BukkitRunnable() {
               public void run() {
                  ItemStack[] input = new ItemStack[9];

                  for(int i = 0; i < 9; ++i) {
                     ItemStack item = crafting.getMatrix()[i];
                     if (item != null && item.getType() != Material.AIR) {
                        input[i] = item;
                     } else {
                        input[i] = new ItemStack(Material.AIR);
                     }
                  }

                  for(Craft r : CraftListener.this.plugin.getCm().getCrafts()) {
                     if (r.checkRequired(input, (a) -> CraftListener.this.players.put(p, a))) {
                        if (!r.isCraft()) {
                           CraftListener.this.players.remove(p);
                        } else if (r.getPermission() != null && !r.getPermission().equals("none") && !p.hasPermission(r.getPermission())) {
                           CraftListener.this.players.remove(p);
                        } else {
                           if (r.isMinionCraft()) {
                              Minion minion = CraftListener.this.plugin.getMm().getMinion(r.getKey());
                              MinionLevel minionLevel = minion.getMinionLevelByLevel(r.getLevel());
                              MinionCraftEvent ce = new MinionCraftEvent(p, minion, minionLevel);
                              Bukkit.getScheduler().scheduleSyncDelayedTask(CraftListener.this.plugin, () -> Bukkit.getServer().getPluginManager().callEvent(ce));
                              if (ce.isCancelled()) {
                                 CraftListener.this.players.remove(p);
                                 break;
                              }

                              if (!minionLevel.isCraftingTable()) {
                                 CraftListener.this.players.remove(p);
                                 break;
                              }

                              if (CraftListener.this.plugin.getCfm().isLevelPermission()) {
                                 Player var10000 = p;
                                 String var10001 = r.getKey();
                                 if (!var10000.hasPermission("minions.craft." + var10001 + "." + r.getLevel())) {
                                    CraftListener.this.players.remove(p);
                                    break;
                                 }
                              }
                           }

                           crafting.setResult(r.getResult());
                           p.updateInventory();
                        }
                        break;
                     }
                  }

               }
            }).runTaskLaterAsynchronously(this.plugin, 2L);
         }

      }
   }

   @EventHandler
   public void onCraft(InventoryDragEvent e) {
      if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
         final Player p = (Player)e.getWhoClicked();
         if (!(e.getInventory() instanceof CraftingInventory)) {
            return;
         }

         final CraftingInventory crafting = (CraftingInventory)e.getInventory();
         this.players.remove(p);
         (new BukkitRunnable() {
            public void run() {
               ItemStack[] input = new ItemStack[9];

               for(int i = 0; i < 9; ++i) {
                  ItemStack item = crafting.getMatrix()[i];
                  if (item != null && item.getType() != Material.AIR) {
                     input[i] = item;
                  } else {
                     input[i] = new ItemStack(Material.AIR);
                  }
               }

               for(Craft r : CraftListener.this.plugin.getCm().getCrafts()) {
                  if (r.checkRequired(input, (a) -> CraftListener.this.players.put(p, a))) {
                     if (!r.isCraft()) {
                        CraftListener.this.players.remove(p);
                     } else if (r.getPermission() != null && !r.getPermission().equals("none") && !p.hasPermission(r.getPermission())) {
                        CraftListener.this.players.remove(p);
                     } else {
                        if (r.isMinionCraft()) {
                           Minion minion = CraftListener.this.plugin.getMm().getMinion(r.getKey());
                           MinionLevel minionLevel = minion.getMinionLevelByLevel(r.getLevel());
                           MinionCraftEvent ce = new MinionCraftEvent(p, minion, minionLevel);
                           Bukkit.getScheduler().scheduleSyncDelayedTask(CraftListener.this.plugin, () -> Bukkit.getServer().getPluginManager().callEvent(ce));
                           if (ce.isCancelled()) {
                              CraftListener.this.players.remove(p);
                              break;
                           }

                           if (!minionLevel.isCraftingTable()) {
                              CraftListener.this.players.remove(p);
                              break;
                           }

                           if (CraftListener.this.plugin.getCfm().isLevelPermission()) {
                              Player var10000 = p;
                              String var10001 = r.getKey();
                              if (!var10000.hasPermission("minions.craft." + var10001 + "." + r.getLevel())) {
                                 CraftListener.this.players.remove(p);
                                 break;
                              }
                           }
                        }

                        crafting.setResult(r.getResult());
                        p.updateInventory();
                     }
                     break;
                  }
               }

            }
         }).runTaskLaterAsynchronously(this.plugin, 2L);
      }

   }

   public void clear(Player p, Inventory inv, HashMap<Integer, Integer> values) {
      for(int slot : values.keySet()) {
         int s = slot + 1;
         int amount = (Integer)values.get(slot);
         if (amount > 0) {
            ItemStack i = inv.getItem(s);
            i.setAmount(amount);
            inv.setItem(s, i);
         } else {
            inv.setItem(s, (ItemStack)null);
         }
      }

      p.updateInventory();
   }

   public boolean checkEquals(ItemStack cursor, ItemStack result) {
      cursor.setAmount(1);
      result.setAmount(1);
      return cursor.equals(result);
   }

   @EventHandler
   public void onQuit(PlayerQuitEvent e) {
      Player p = e.getPlayer();
      this.players.remove(p);
   }

   @EventHandler
   public void onKick(PlayerKickEvent e) {
      Player p = e.getPlayer();
      this.players.remove(p);
   }

   @EventHandler
   public void onClose(InventoryCloseEvent e) {
      if (e.getInventory().getType().equals(InventoryType.WORKBENCH)) {
         Player p = (Player)e.getPlayer();
         this.players.remove(p);
      }

   }
}
