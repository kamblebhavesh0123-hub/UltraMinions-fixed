package io.github.Leonardo0013YT.UltraMinions.api.events;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class MinionSellItemEvent extends Event implements Cancellable {
   private static final HandlerList HANDLERS_LIST = new HandlerList();
   private boolean isCancelled;
   private double priceNormal;
   private double priceSmelted;
   private double priceCompressed;
   private double finalPrice;
   private List<ItemStack> items;
   private boolean smelted;
   private boolean compressed;
   private Player owner;

   public MinionSellItemEvent(Player owner, List<ItemStack> items, boolean smelted, boolean compressed, double priceNormal, double priceSmelted, double priceCompressed, double finalPrice) {
      this.owner = owner;
      this.items = items;
      this.smelted = smelted;
      this.compressed = compressed;
      this.priceNormal = priceNormal;
      this.priceSmelted = priceSmelted;
      this.priceCompressed = priceCompressed;
      this.finalPrice = finalPrice;
      this.isCancelled = false;
   }

   public static HandlerList getHandlerList() {
      return HANDLERS_LIST;
   }

   public double getPriceCompressed() {
      return this.priceCompressed;
   }

   public void setPriceCompressed(double priceCompressed) {
      this.priceCompressed = priceCompressed;
   }

   /** @deprecated */
   @Deprecated
   public void setPriceCompressed(int priceCompressed) {
      this.priceCompressed = (double)priceCompressed;
   }

   public List<ItemStack> getItems() {
      return this.items;
   }

   public double getFinalPrice() {
      return this.finalPrice;
   }

   public void setFinalPrice(double finalPrice) {
      this.finalPrice = finalPrice;
   }

   public Player getOwner() {
      return this.owner;
   }

   public double getPriceNormal() {
      return this.priceNormal;
   }

   /** @deprecated */
   @Deprecated
   public void setPriceNormal(int priceNormal) {
      this.priceNormal = (double)priceNormal;
   }

   public void setPriceNormal(double priceNormal) {
      this.priceNormal = priceNormal;
   }

   public double getPriceSmelted() {
      return this.priceSmelted;
   }

   /** @deprecated */
   @Deprecated
   public void setPriceSmelted(int priceSmelted) {
      this.priceSmelted = (double)priceSmelted;
   }

   public void setPriceSmelted(double priceSmelted) {
      this.priceSmelted = priceSmelted;
   }

   public boolean isCompressed() {
      return this.compressed;
   }

   public void setCompressed(boolean compressed) {
      this.compressed = compressed;
   }

   public boolean isSmelted() {
      return this.smelted;
   }

   public boolean isCancelled() {
      return this.isCancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.isCancelled = cancelled;
   }

   public HandlerList getHandlers() {
      return HANDLERS_LIST;
   }
}
