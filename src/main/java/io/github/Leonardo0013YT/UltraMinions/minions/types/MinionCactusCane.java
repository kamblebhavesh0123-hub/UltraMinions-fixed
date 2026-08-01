package io.github.Leonardo0013YT.UltraMinions.minions.types;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionPlaceEvent;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.utils.Utils;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MinionCactusCane extends Minion {
   private ItemStack place = this.getPlace();
   private List<Vector> woods = Arrays.asList(new Vector(2, 0, 2), new Vector(-2, 0, 2), new Vector(2, 0, -2), new Vector(-2, 0, -2), new Vector(2, 0, 0), new Vector(0, 0, 2), new Vector(-2, 0, 0), new Vector(0, 0, -2));
   private List<Vector> water = Arrays.asList(new Vector(-1, 0, -2), new Vector(-2, 0, -1), new Vector(-2, 0, 1), new Vector(-1, 0, 2), new Vector(1, 0, 2), new Vector(2, 0, 1), new Vector(2, 0, -1), new Vector(1, 0, -2));

   public MinionCactusCane(Main plugin, YamlConfiguration minion, String path, File f) {
      super(plugin, minion, path, f);
   }

   public void update(PlayerMinion pm, ArmorStand armor, PlayerMinionStat stat, Location spawn, CallBackAPI<Boolean> action) {
      if (armor != null && pm != null) {
         ItemStack material = this.getPlace();
         Location l = pm.getSelected("SAPPLING");
         if (l != null) {
            if (!this.plugin.getAdm().isStackable(l)) {
               if (!l.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                  pm.setEnabled(false);
                  pm.getP().sendMessage(this.plugin.getLang().get("messages.noPerfect"));
                  return;
               }

               if (material.getType().equals(Material.SUGAR_CANE)) {
                  armor.setItemInHand(new ItemStack(Material.SUGAR_CANE));
                  this.buildTree(l, Material.SUGAR_CANE);
               } else if (material.getType().equals(Material.CACTUS)) {
                  armor.setItemInHand(new ItemStack(Material.CACTUS));
                  this.buildTree(l, Material.CACTUS);
               } else if (Utils.is1_14to1_16()) {
                  armor.setItemInHand(new ItemStack(Material.valueOf("BAMBOO")));
                  this.buildTree(l, Material.valueOf("BAMBOO"));
               }

               stat.setGenerated(stat.getGenerated() + 3);
               Bukkit.getServer().getPluginManager().callEvent(new MinionPlaceEvent(pm, l.getBlock()));
            }

            action.done(false);
         } else {
            armor.setItemInHand(this.getHandItem());
            Location de = pm.getSelected("DESTROY");
            if (de != null && !this.plugin.getAdm().isStackable(de)) {
               this.destroyTree(de);
            }

            action.done(true);
         }

      }
   }

   public boolean check(Location spawn) {
      boolean yes = true;

      for(Vector l : this.water) {
         Location lo = spawn.clone().add(l.getX(), l.getY() - (double)1.0F, l.getZ());
         Material t = lo.getBlock().getType();
         Material p = this.place.getType();
         if (!p.equals(Material.CACTUS) && (!Utils.is1_14to1_16() || !p.equals(Material.valueOf("BAMBOO"))) && !t.equals(Material.WATER)) {
            yes = false;
         }
      }

      for(Vector l : this.woods) {
         Location lo = spawn.clone().add(l.getX(), l.getY() - (double)1.0F, l.getZ());
         Material t = lo.getBlock().getType();
         Material p = this.place.getType();
         if ((!p.equals(Material.CACTUS) || !t.equals(Material.SAND)) && (!p.equals(Material.SUGAR_CANE) || !t.equals(Material.GRASS_BLOCK)) && (!Utils.is1_14to1_16() || !p.equals(Material.valueOf("BAMBOO")) || !t.equals(Material.GRASS_BLOCK))) {
            yes = false;
         }
      }

      return yes;
   }

   public Location checkArroundAir(Location loc) {
      for(Vector l : this.woods) {
         Location lo = loc.clone().add(l.getX(), l.getY(), l.getZ());
         if (lo.getBlock().getType().equals(Material.AIR)) {
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
      t3.getBlock().setType(Material.AIR);
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get(), () -> t2.getBlock().setType(Material.AIR), 1L);
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get(), () -> t1.getBlock().setType(Material.AIR), 2L);
   }

   public void buildTree(Location loc, Material material) {
      Location t1 = loc.clone();
      Location t2 = loc.clone().add((double)0.0F, (double)1.0F, (double)0.0F);
      Location t3 = loc.clone().add((double)0.0F, (double)2.0F, (double)0.0F);
      t1.getBlock().setType(material);
      t2.getBlock().setType(material);
      t3.getBlock().setType(material);
   }
}
