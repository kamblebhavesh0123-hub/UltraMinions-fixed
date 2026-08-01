package io.github.Leonardo0013YT.UltraMinions.animator;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public class Animator {
   private ArmorStand armorStand;
   private int length;
   private Frame[] frames;
   private boolean paused = false;
   private int currentFrame;
   private int executes = 0;
   private boolean interpolate;
   private EulerAngle ea = new EulerAngle(19.2, (double)0.0F, (double)0.0F);

   public Animator(Animation animation, ArmorStand stand) {
      Animation ani = animation.clone();
      this.frames = ani.getFrames();
      this.length = ani.getLength();
      this.interpolate = ani.isInterpolate();
      this.armorStand = stand;
   }

   public void update() {
      if (this.armorStand == null) {
         this.executes = this.length - 1;
      } else {
         if (!this.paused) {
            if (this.currentFrame >= this.length - 1 || this.currentFrame < 0) {
               this.currentFrame = 0;
               this.paused = true;
               return;
            }

            Frame f = this.frames[this.currentFrame];
            if (this.interpolate && f == null) {
               f = this.interpolate(this.currentFrame);
            }

            if (f != null) {
               this.armorStand.setBodyPose(f.middle);
               this.armorStand.setLeftLegPose(f.leftLeg);
               this.armorStand.setRightLegPose(f.rightLeg);
               this.armorStand.setLeftArmPose(f.leftArm);
               this.armorStand.setRightArmPose(f.rightArm);
               this.armorStand.setHeadPose(this.ea);
            }

            ++this.currentFrame;
         }

      }
   }

   private Frame interpolate(int frameID) {
      Frame minFrame = null;

      for(int i = frameID; i >= 0; --i) {
         if (this.frames[i] != null) {
            minFrame = this.frames[i];
            break;
         }
      }

      Frame maxFrame = null;

      for(int i = frameID; i < this.frames.length; ++i) {
         if (this.frames[i] != null) {
            maxFrame = this.frames[i];
            break;
         }
      }

      if (maxFrame != null && minFrame != null) {
         Frame res = new Frame();
         res.frameID = frameID;
         float Dmin = (float)(frameID - minFrame.frameID);
         float D = (float)(maxFrame.frameID - minFrame.frameID);
         float D0 = Dmin / D;
         res = minFrame.mult(1.0F - D0, frameID).add(maxFrame.mult(D0, frameID), frameID);
         return res;
      } else if (maxFrame == null && minFrame != null) {
         return minFrame;
      } else if (maxFrame != null) {
         return maxFrame;
      } else {
         Frame res = new Frame();
         res.frameID = frameID;
         return res;
      }
   }

   public ArmorStand getArmorStand() {
      return this.armorStand;
   }

   public void addExecute() {
      ++this.executes;
   }

   public int getExecutes() {
      return this.executes;
   }

   public int getLength() {
      return this.length;
   }
}
