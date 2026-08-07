package io.github.Leonardo0013YT.UltraMinions.database;

/**
 * A lightweight, read-only summary of one placed minion, used by the
 * /minions save and /minions list commands. This intentionally does not
 * reference PlayerMinion or any live entity/state, since it is built by
 * scanning the database directly and may describe minions belonging to
 * offline players.
 */
public class MinionRecord {
   private final String owner;
   private final String type;
   private final String world;
   private final double x;
   private final double y;
   private final double z;
   private final int level;

   public MinionRecord(String owner, String type, String world, double x, double y, double z, int level) {
      this.owner = owner;
      this.type = type;
      this.world = world;
      this.x = x;
      this.y = y;
      this.z = z;
      this.level = level;
   }

   public String getOwner() {
      return this.owner;
   }

   public String getType() {
      return this.type;
   }

   public String getWorld() {
      return this.world;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public int getLevel() {
      return this.level;
   }
}
