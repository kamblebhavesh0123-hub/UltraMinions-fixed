package io.github.Leonardo0013YT.UltraMinions.api.events;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class MinionCollectEvent extends Event implements Cancellable {
   private static final HandlerList HANDLERS_LIST = new HandlerList();
   private boolean isCancelled;
   private HashMap<ItemStack, Integer> items;
   private Player player;
   private boolean emptyCollect;

   public MinionCollectEvent(HashMap<ItemStack, Integer> items, Player player) {
      this.items = items;
      this.player = player;
      this.emptyCollect = items.isEmpty();
   }

   public static HandlerList getHandlerList() {
      return HANDLERS_LIST;
   }

   public HashMap<ItemStack, Integer> getItems() {
      return this.items;
   }

   public void setItems(HashMap<ItemStack, Integer> items) {
      this.items = items;
   }

   public Player getPlayer() {
      return this.player;
   }

   public boolean isEmptyCollect() {
      return this.emptyCollect;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }

   public void setCancelled(boolean isCancelled) {
      this.isCancelled = isCancelled;
   }

   public HandlerList getHandlers() {
      return HANDLERS_LIST;
   }
}
