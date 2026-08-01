package io.github.Leonardo0013YT.UltraMinions.api.events;

import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinionBreakEvent extends Event implements Cancellable {
   private static final HandlerList HANDLERS_LIST = new HandlerList();
   private boolean isCancelled;
   private PlayerMinion playerMinion;
   private Block block;

   public MinionBreakEvent(PlayerMinion playerMinion, Block block) {
      this.playerMinion = playerMinion;
      this.block = block;
   }

   public static HandlerList getHandlerList() {
      return HANDLERS_LIST;
   }

   public PlayerMinion getPlayerMinion() {
      return this.playerMinion;
   }

   public Block getBlock() {
      return this.block;
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
