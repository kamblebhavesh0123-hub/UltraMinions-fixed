package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.animator.Animation;
import io.github.Leonardo0013YT.UltraMinions.animator.Animator;
import io.github.Leonardo0013YT.UltraMinions.animator.Frame;
import io.github.Leonardo0013YT.UltraMinions.interfaces.BlockAnimation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public class AnimationManager {
   private Map<String, Animation> animCache = new HashMap();
   private ArrayList<Animator> animators = new ArrayList();
   private ArrayList<BlockAnimation> blocks = new ArrayList();
   private boolean blockAnimation = false;
   private BukkitTask task;
   private Main plugin;

   public AnimationManager(Main plugin) {
      this.plugin = plugin;
      this.reload();
   }

   public void addBlockAnimation(BlockAnimation block) {
      this.blocks.add(block);
   }

   public void reload() {
      this.animators.clear();
      if (this.task != null) {
         this.task.cancel();
      }

      this.task = (new BukkitRunnable() {
         public void run() {
            AnimationManager.this.blockAnimation = !AnimationManager.this.blockAnimation;
            if (AnimationManager.this.blockAnimation && !AnimationManager.this.blocks.isEmpty()) {
               ArrayList<BlockAnimation> to = new ArrayList();

               for(BlockAnimation ani : AnimationManager.this.blocks) {
                  ani.update();
                  if (ani.isFinished()) {
                     to.add(ani);
                  }
               }

               AnimationManager.this.blocks.removeAll(to);
            }

            if (!AnimationManager.this.animators.isEmpty()) {
               ArrayList<Animator> to = null;

               for(Animator ani : AnimationManager.this.animators) {
                  ani.addExecute();
                  ani.update();
                  if (ani.getExecutes() >= ani.getLength() - 1) {
                     if (to == null) {
                        to = new ArrayList();
                     }

                     ani.getArmorStand().setHeadPose(new EulerAngle((double)0.0F, (double)0.0F, (double)0.0F));
                     to.add(ani);
                  }
               }

               if (to != null) {
                  AnimationManager.this.animators.removeAll(to);
               }

            }
         }
      }).runTaskTimer(Main.get(), 0L, 1L);
   }

   private void loadAnimator(File aniFile) {
      Frame[] frames = new Frame[0];
      int length = 0;
      boolean interpolate = false;

      try {
         BufferedReader br = new BufferedReader(new FileReader(aniFile));

         label132: {
            label131: {
               label130: {
                  label129: {
                     label128: {
                        label127: {
                           label126: {
                              try {
                                 String line = "";
                                 Frame currentFrame = null;

                                 while(true) {
                                    if ((line = br.readLine()) == null) {
                                       if (currentFrame != null) {
                                          frames[currentFrame.frameID] = currentFrame;
                                       }
                                       break label132;
                                    }

                                    if (line.startsWith("length")) {
                                       length = (int)Float.parseFloat(line.split(" ")[1]);
                                       frames = new Frame[length];
                                    } else if (line.startsWith("frame")) {
                                       if (currentFrame != null) {
                                          frames[currentFrame.frameID] = currentFrame;
                                       }

                                       int frameID = Integer.parseInt(line.split(" ")[1]);
                                       currentFrame = new Frame();
                                       currentFrame.frameID = frameID;
                                    } else if (line.contains("interpolate")) {
                                       interpolate = true;
                                    } else if (line.contains("Armorstand_Position")) {
                                       if (currentFrame == null) {
                                          break label131;
                                       }

                                       currentFrame.x = Float.parseFloat(line.split(" ")[1]);
                                       currentFrame.y = Float.parseFloat(line.split(" ")[2]);
                                       currentFrame.z = Float.parseFloat(line.split(" ")[3]);
                                       currentFrame.r = Float.parseFloat(line.split(" ")[4]);
                                    } else if (line.contains("Armorstand_Middle")) {
                                       if (currentFrame == null) {
                                          break label130;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.middle = new EulerAngle((double)x, (double)y, (double)z);
                                    } else if (line.contains("Armorstand_Right_Leg")) {
                                       if (currentFrame == null) {
                                          break label129;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.rightLeg = new EulerAngle((double)x, (double)y, (double)z);
                                    } else if (line.contains("Armorstand_Left_Leg")) {
                                       if (currentFrame == null) {
                                          break label128;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.leftLeg = new EulerAngle((double)x, (double)y, (double)z);
                                    } else if (line.contains("Armorstand_Left_Arm")) {
                                       if (currentFrame == null) {
                                          break label127;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.leftArm = new EulerAngle((double)x, (double)y, (double)z);
                                    } else if (line.contains("Armorstand_Right_Arm")) {
                                       if (currentFrame == null) {
                                          break label126;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.rightArm = new EulerAngle((double)x, (double)y, (double)z);
                                    } else if (line.contains("Armorstand_Head")) {
                                       if (currentFrame == null) {
                                          break;
                                       }

                                       float x = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[1]));
                                       float y = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[2]));
                                       float z = (float)Math.toRadians((double)Float.parseFloat(line.split(" ")[3]));
                                       currentFrame.head = new EulerAngle((double)x, (double)y, (double)z);
                                    }
                                 }
                              } catch (Throwable var12) {
                                 try {
                                    br.close();
                                 } catch (Throwable var11) {
                                    var12.addSuppressed(var11);
                                 }

                                 throw var12;
                              }

                              br.close();
                              return;
                           }

                           br.close();
                           return;
                        }

                        br.close();
                        return;
                     }

                     br.close();
                     return;
                  }

                  br.close();
                  return;
               }

               br.close();
               return;
            }

            br.close();
            return;
         }

         br.close();
      } catch (Exception var13) {
      }

      this.animCache.put(aniFile.getAbsolutePath(), new Animation(frames, length, interpolate));
   }

   public void execute(String animation, ArmorStand stand) {
      File file = new File(this.plugin.getDataFolder(), animation);
      if (!this.animCache.containsKey(file.getAbsolutePath())) {
         this.loadAnimator(file);
      }

      Animator ani = new Animator((Animation)this.animCache.get(file.getAbsolutePath()), stand);
      this.animators.add(ani);
   }
}
