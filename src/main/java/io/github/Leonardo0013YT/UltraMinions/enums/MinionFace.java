package io.github.Leonardo0013YT.UltraMinions.enums;

public enum MinionFace {
   NORTH(0.0F),
   NORTHEAST(45.0F),
   EAST(90.0F),
   SOUTHEAST(135.0F),
   SOUTH(180.0F),
   SOUTHWEST(225.0F),
   WEST(270.0F),
   NORTHWEST(315.0F);

   private float yaw;

   private MinionFace(float yaw) {
      this.yaw = yaw;
   }

   public float getYaw() {
      return this.yaw;
   }

   // $FF: synthetic method
   private static MinionFace[] $values() {
      return new MinionFace[]{NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
   }
}
