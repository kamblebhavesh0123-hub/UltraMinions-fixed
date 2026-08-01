package io.github.Leonardo0013YT.UltraMinions.minions.animations;

import io.github.Leonardo0013YT.UltraMinions.interfaces.BlockAnimation;
import io.github.Leonardo0013YT.UltraMinions.utils.MinionUtils_1_17;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockBreakAnimation implements BlockAnimation {
   private int damage;
   private Block block;
   private boolean finished = false;
   private boolean execute = false;

   public BlockBreakAnimation(Block block) {
      this.block = block;
      this.damage = 0;
   }

   public void update() {
      this.execute = !this.execute;
      if (this.execute) {
         if (this.damage > 9) {
            this.finished = true;
         } else {
            if (this.block != null && !this.block.getType().equals(Material.AIR)) {
               MinionUtils_1_17.damageBlock(this.block.getLocation(), this.damage);
            }

            ++this.damage;
         }
      }
   }

   public boolean isFinished() {
      return this.finished;
   }
}
