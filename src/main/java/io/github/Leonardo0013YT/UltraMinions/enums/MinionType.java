package io.github.Leonardo0013YT.UltraMinions.enums;

import java.util.Arrays;
import org.bukkit.Material;

public enum MinionType {
   FARMER(Material.WOODEN_HOE, 3),
   FISHER(Material.FISHING_ROD, 2),
   LUMBERJACK(Material.WOODEN_AXE, 3, new Material[]{Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG}),
   MINER(Material.WOODEN_PICKAXE, 2),
   RANCHER(Material.WOODEN_SWORD, 2),
   HUNTER(Material.WOODEN_SWORD, 2),
   PEASANT(Material.WOODEN_HOE, 3, new Material[]{Material.MELON, Material.PUMPKIN}),
   CACTUSCANE(Material.WOODEN_HOE, 2, new Material[]{Material.CACTUS, Material.SUGAR_CANE}),
   COLLECTOR(Material.CHEST, 1),
   SELLER(Material.GOLD_NUGGET, 1);

   private Material[] works;
   private Material handItem;
   private int actions;

   private MinionType(Material handItem, int actions) {
      this.handItem = handItem;
      this.works = new Material[0];
      this.actions = actions;
   }

   private MinionType(Material handItem, int actions, Material... works) {
      this.handItem = handItem;
      this.works = works;
      this.actions = actions;
   }

   public Material getHandItem() {
      return this.handItem;
   }

   public String toString() {
      return Arrays.toString(this.works);
   }

   public int getActions() {
      return this.actions;
   }

   public boolean check(Material material) {
      return Arrays.asList(this.works).contains(material);
   }

   // $FF: synthetic method
   private static MinionType[] $values() {
      return new MinionType[]{FARMER, FISHER, LUMBERJACK, MINER, RANCHER, HUNTER, PEASANT, CACTUSCANE, COLLECTOR, SELLER};
   }
}
