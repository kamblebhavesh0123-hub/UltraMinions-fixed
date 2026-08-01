package io.github.Leonardo0013YT.UltraMinions.minions.animations;

import io.github.Leonardo0013YT.UltraMinions.interfaces.BlockAnimation;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class CropsUpgradeAnimation implements BlockAnimation {
   private Block block;
   private boolean finished = false;
   private boolean execute = false;

   public CropsUpgradeAnimation(Block block) {
      this.block = block;
   }

   public void update() {
      if (this.block != null && !this.block.getType().equals(Material.AIR)) {
         this.execute = !this.execute;
         if (this.execute) {
            if (this.block.getBlockData() instanceof Ageable && !Utils.isMax(this.block)) {
               Ageable age = (Ageable)this.block.getBlockData();
               age.setAge(age.getAge() + 1);
               this.block.setBlockData(age);
            } else {
               this.finished = true;
            }
         }
      }
   }

   public boolean isFinished() {
      return this.finished;
   }
}
