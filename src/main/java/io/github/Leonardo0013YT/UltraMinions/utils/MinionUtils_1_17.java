package io.github.Leonardo0013YT.UltraMinions.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MinionUtils_1_17 {
   public static void damageBlock(Location l, int damage) {
      try {
         float progress = damage < 0 ? 0.0F : Math.min(1.0F, damage / 9.0F);

         for(Entity ent : l.getWorld().getNearbyEntities(l, 4.0D, 4.0D, 4.0D)) {
            if (ent instanceof Player p) {
               p.sendBlockDamage(l, progress);
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
