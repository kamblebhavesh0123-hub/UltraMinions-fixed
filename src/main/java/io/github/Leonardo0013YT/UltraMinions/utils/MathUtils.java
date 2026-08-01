package io.github.Leonardo0013YT.UltraMinions.utils;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionSellItemEvent;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.minions.MinionItem;
import io.github.Leonardo0013YT.UltraMinions.minions.MinionSell;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSell;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeAutoSmelt;
import io.github.Leonardo0013YT.UltraMinions.upgrades.UpgradeCompressor;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class MathUtils {
   private PlayerMinion pm;
   private UpgradeCompressor compressor;
   private UpgradeAutoSmelt autoSmelt;
   private UpgradeAutoSell autoSell;
   private boolean isCompressor;
   private boolean isAutoSmelt;
   private boolean isAutoSell;
   private int actions;
   private int slots;
   private int spaces;
   private MinionItem normal;
   private MinionItem compressed;
   private MinionItem smelted;

   public MathUtils(PlayerMinion pm, UpgradeCompressor compressor, UpgradeAutoSmelt autoSmelt, UpgradeAutoSell autoSell) {
      this.pm = pm;
      this.compressor = compressor;
      this.autoSmelt = autoSmelt;
      this.autoSell = autoSell;
      this.isAutoSell = autoSell != null;
      this.isAutoSmelt = autoSmelt != null;
      this.isCompressor = compressor != null;
      this.actions = pm.getActions();
      this.slots = pm.getMinionLevel().getMax();
      this.spaces = this.slots / 64;
      if (this.spaces < 1) {
         ++this.spaces;
      }

      this.normal = pm.getMinion().getGiveInInv();
      this.compressed = pm.getMinion().getCompressor();
      this.smelted = pm.getMinion().getSmelt();
   }

   public synchronized void fill() {
      if (!this.pm.getType().equals(MinionType.COLLECTOR) && !this.pm.getType().equals(MinionType.SELLER)) {
         if (this.actions > 0) {
            this.pm.getItems().clear();
            Minion m = this.pm.getMinion();
            MinionSell ms;
            if (this.isAutoSmelt) {
               if (this.isCompressor) {
                  ms = this.compressed.getItems(this.pm, this.actions, this.spaces, this.compressor.getAmount(this.pm.getKey()), this.slots, this.smelted);
               } else {
                  ms = this.smelted.getItems(this.pm, this.actions, this.spaces, this.slots);
               }
            } else if (this.isCompressor) {
               ms = this.compressed.getItems(this.pm, this.actions, this.spaces, this.compressor.getAmount(this.pm.getKey()), this.slots, this.normal);
            } else {
               ms = this.normal.getItems(this.pm, this.actions, this.spaces, this.slots);
            }

            this.pm.getItems().putAll(ms.getItems());
            ArrayList<ItemStack> sells = new ArrayList(ms.getSell());
            this.pm.setActions(this.pm.getActions() - ms.getChest());
            this.pm.setFull(!sells.isEmpty());
            if (this.isAutoSell && !sells.isEmpty()) {
               boolean isSellAddon = Main.get().getAdm().isPricePlugin();
               double price = (double)0.0F;

               for(ItemStack i : sells) {
                  if (isSellAddon) {
                     price += Main.get().getAdm().getPrice(this.pm.getP(), i) * (this.autoSell.getSell() / (double)100.0F) * (double)i.getAmount();
                  } else {
                     price += m.getPriceNormalSell() * (this.autoSell.getSell() / (double)100.0F) * (double)i.getAmount();
                  }
               }

               if (price > (double)0.0F) {
                  MinionSellItemEvent item = new MinionSellItemEvent(this.pm.getP(), sells, this.isAutoSmelt, this.isCompressor, m.getPriceNormalSell(), m.getPriceSmeltedSell(), m.getPriceCompressedSell(), price);
                  Bukkit.getServer().getPluginManager().callEvent(item);
                  if (!item.isCancelled()) {
                     this.pm.getP().sendMessage(Main.get().getLang().get("messages.produced").replaceAll("<title>", m.getTitle()).replaceAll("<coins>", Utils.format(item.getFinalPrice())));
                     Main.get().getAdm().addCoins(this.pm.getP(), item.getFinalPrice());
                     this.pm.setActions(this.pm.getActions() - ms.getRealized());
                  }
               }
            }
         }

      }
   }

   public int getSpaces() {
      return this.spaces;
   }
}
