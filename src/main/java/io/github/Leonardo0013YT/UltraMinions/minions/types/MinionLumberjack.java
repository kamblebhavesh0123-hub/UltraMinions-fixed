package io.github.Leonardo0013YT.UltraMinions.minions.types;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionPlaceEvent;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.utils.ItemBuilder;
import io.github.Leonardo0013YT.UltraMinions.utils.MinionUtils_1_17;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MinionLumberjack extends Minion {
   private List<Vector> woods = Arrays.asList(new Vector(2, 0, 2), new Vector(-2, 0, 2), new Vector(2, 0, -2), new Vector(-2, 0, -2), new Vector(2, 0, 0), new Vector(0, 0, 2), new Vector(-2, 0, 0), new Vector(0, 0, -2));

   public MinionLumberjack(Main plugin, YamlConfiguration minion, String path, File f) {
      super(plugin, minion, path, f);
   }

   public void update(PlayerMinion pm, ArmorStand armor, PlayerMinionStat stat, Location spawn, CallBackAPI<Boolean> action) {
      if (armor != null && pm != null) {
         ItemStack material = this.getPlace();
         Location l = pm.getSelected("SAPPLING");
         if (l != null) {
            if (!this.plugin.getAdm().isStackable(l)) {
               String t = material.getType().name().split("_")[0];
               if (t.startsWith("DARK")) {
                  t = "DARK_OAK";
               }

               Material sapling = Material.valueOf(t + "_SAPLING");
               armor.setItemInHand(new ItemStack(sapling, 1));
               l.getBlock().setType(sapling);
               Bukkit.getServer().getPluginManager().callEvent(new MinionPlaceEvent(pm, l.getBlock()));
            }

            action.done(false);
         } else {
            Location lo = pm.getSelected("BUILD");
            if (lo != null) {
               if (!this.plugin.getAdm().isStackable(lo)) {
                  armor.setItemInHand(ItemBuilder.item(Material.BONE_MEAL, 1, (short)15, "", ""));
                  this.buildTree(lo, material.getType());
                  stat.setGenerated(stat.getGenerated() + 3);
               }

               action.done(false);
            } else {
               armor.setItemInHand(this.getHandItem());
               Location de = pm.getSelected("DESTROY");
               if (de != null) {
                  if (!this.plugin.getAdm().isStackable(de)) {
                     this.destroyTree(de);
                     MinionUtils_1_17.damageBlock(de.getBlock().getLocation(), -1);
                  }

                  action.done(true);
               }
            }
         }

      }
   }

   public boolean check(Location spawn) {
      boolean yes = true;

      for(Vector l : this.woods) {
         Location lo = spawn.clone().add(l.getX(), l.getY() - (double)1.0F, l.getZ());
         Material t = lo.getBlock().getType();
         if (!t.equals(Material.DIRT) && !t.equals(Material.GRASS_BLOCK)) {
            yes = false;
         }
      }

      return yes;
   }

   public Location checkArroundWood(Location loc) {
      for(Vector l : this.woods) {
         Location lo1 = loc.clone().add(l.getX(), l.getY(), l.getZ());
         Location lo2 = loc.clone().add(l.getX(), l.getY() + (double)1.0F, l.getZ());
         Location lo3 = loc.clone().add(l.getX(), l.getY() + (double)2.0F, l.getZ());
         if (!lo1.getBlock().getType().name().contains("SAPLING") && (lo1.getBlock().getType().equals(Material.AIR) || lo2.getBlock().getType().equals(Material.AIR) || lo3.getBlock().getType().equals(Material.AIR))) {
            return lo1;
         }
      }

      return null;
   }

   public Location checkArroundSappling(Location loc) {
      for(Vector l : this.woods) {
         Location lo = loc.clone().add(l.getX(), l.getY(), l.getZ());
         if (lo.getBlock().getType().name().contains("SAPLING")) {
            return lo;
         }
      }

      return null;
   }

   public Location getArroundRandomWood(Location loc) {
      return loc.clone().add((Vector)this.woods.get(ThreadLocalRandom.current().nextInt(this.woods.size())));
   }

   public void destroyTree(Location loc) {
      Location t1 = loc.clone();
      Location t2 = loc.clone().add((double)0.0F, (double)1.0F, (double)0.0F);
      Location t3 = loc.clone().add((double)0.0F, (double)2.0F, (double)0.0F);
      t1.getBlock().setType(Material.AIR);
      t2.getBlock().setType(Material.AIR);
      t3.getBlock().setType(Material.AIR);
   }

   public void buildTree(Location loc, Material material) {
      String t = material.name().split("_")[0];
      if (t.startsWith("DARK")) {
         t = "DARK_OAK";
      }

      Material sapling = Material.valueOf(t + "_LEAVES");
      Material log = Material.valueOf(t + "_LOG");
      Location t1 = loc.clone();
      Location t2 = loc.clone().add((double)0.0F, (double)1.0F, (double)0.0F);
      Location t3 = loc.clone().add((double)0.0F, (double)2.0F, (double)0.0F);
      Location l1 = loc.clone().add((double)1.0F, (double)2.0F, (double)0.0F);
      Location l2 = loc.clone().add((double)-1.0F, (double)2.0F, (double)0.0F);
      Location l3 = loc.clone().add((double)0.0F, (double)2.0F, (double)-1.0F);
      Location l4 = loc.clone().add((double)0.0F, (double)2.0F, (double)1.0F);
      Location l5 = loc.clone().add((double)0.0F, (double)3.0F, (double)0.0F);
      t1.getBlock().setType(log);
      t2.getBlock().setType(log);
      t3.getBlock().setType(log);
      l1.getBlock().setType(sapling);
      l2.getBlock().setType(sapling);
      l3.getBlock().setType(sapling);
      l4.getBlock().setType(sapling);
      l5.getBlock().setType(sapling);
   }
}
