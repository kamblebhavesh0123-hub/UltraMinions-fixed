package io.github.Leonardo0013YT.UltraMinions.animator;

import org.bukkit.util.EulerAngle;

public class Frame {
   public int frameID;
   public float x;
   public float y;
   public float z;
   public float r;
   public EulerAngle middle;
   public EulerAngle rightLeg;
   public EulerAngle leftLeg;
   public EulerAngle rightArm;
   public EulerAngle leftArm;
   public EulerAngle head;

   public Frame mult(float a, int frameID) {
      Frame f = new Frame();
      f.frameID = frameID;
      f.x *= a;
      f.y *= a;
      f.z *= a;
      f.r *= a;
      f.middle = new EulerAngle(this.middle.getX() * (double)a, this.middle.getY() * (double)a, this.middle.getZ() * (double)a);
      f.rightLeg = new EulerAngle(this.rightLeg.getX() * (double)a, this.rightLeg.getY() * (double)a, this.rightLeg.getZ() * (double)a);
      f.leftLeg = new EulerAngle(this.leftLeg.getX() * (double)a, this.leftLeg.getY() * (double)a, this.leftLeg.getZ() * (double)a);
      f.rightArm = new EulerAngle(this.rightArm.getX() * (double)a, this.rightArm.getY() * (double)a, this.rightArm.getZ() * (double)a);
      f.leftArm = new EulerAngle(this.leftArm.getX() * (double)a, this.leftArm.getY() * (double)a, this.leftArm.getZ() * (double)a);
      f.head = new EulerAngle(this.head.getX() * (double)a, this.head.getY() * (double)a, this.head.getZ() * (double)a);
      return f;
   }

   public Frame add(Frame a, int frameID) {
      Frame f = new Frame();
      f.frameID = frameID;
      f.x += a.x;
      f.y += a.y;
      f.z += a.z;
      f.r += a.r;
      f.middle = new EulerAngle(this.middle.getX() + a.middle.getX(), this.middle.getY() + a.middle.getY(), this.middle.getZ() + a.middle.getZ());
      f.rightLeg = new EulerAngle(this.rightLeg.getX() + a.rightLeg.getX(), this.rightLeg.getY() + a.rightLeg.getY(), this.rightLeg.getZ() + a.rightLeg.getZ());
      f.leftLeg = new EulerAngle(this.leftLeg.getX() + a.leftLeg.getX(), this.leftLeg.getY() + a.leftLeg.getY(), this.leftLeg.getZ() + a.leftLeg.getZ());
      f.rightArm = new EulerAngle(this.rightArm.getX() + a.rightArm.getX(), this.rightArm.getY() + a.rightArm.getY(), this.rightArm.getZ() + a.rightArm.getZ());
      f.leftArm = new EulerAngle(this.leftArm.getX() + a.leftArm.getX(), this.leftArm.getY() + a.leftArm.getY(), this.leftArm.getZ() + a.leftArm.getZ());
      f.head = new EulerAngle(this.head.getX() + a.head.getX(), this.head.getY() + a.head.getY(), this.head.getZ() + a.head.getZ());
      return f;
   }
}
