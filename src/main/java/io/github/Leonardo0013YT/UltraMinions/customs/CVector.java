package io.github.Leonardo0013YT.UltraMinions.customs;

import org.bukkit.util.Vector;

public class CVector {
   private int x;
   private int y;
   private int z;

   public CVector(Vector v) {
      this.x = v.getBlockX();
      this.y = v.getBlockY();
      this.z = v.getBlockZ();
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }
}
