package io.github.Leonardo0013YT.UltraMinions.minions.types;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionBreakEvent;
import io.github.Leonardo0013YT.UltraMinions.api.events.MinionPlaceEvent;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import io.github.Leonardo0013YT.UltraMinions.utils.MinionUtils_1_17;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MinionMiner extends Minion {
   private ItemStack place = this.getPlace();
   private ArrayList<Vector> miners = new ArrayList(Arrays.asList(new Vector(1, -1, 0), new Vector(0, -1, 1), new Vector(1, -1, 1), new Vector(-1, -1, 0), new Vector(0, -1, -1), new Vector(-1, -1, -1), new Vector(1, -1, -1), new Vector(-1, -1, 1), new Vector(2, -1, 0), new Vector(2, -1, -1), new Vector(2, -1, 1), new Vector(-2, -1, 0), new Vector(-2, -1, -1), new Vector(-2, -1, 1), new Vector(0, -1, 2), new Vector(1, -1, 2), new Vector(-1, -1, 2), new Vector(0, -1, -2), new Vector(1, -1, -2), new Vector(-1, -1, -2), new Vector(2, -1, -2), new Vector(2, -1, 2), new Vector(-2, -1, -2), new Vector(-2, -1, 2)));

   public MinionMiner(Main plugin, YamlConfiguration minion, String path, File f) {
      super(plugin, minion, path, f);
   }

   public void update(PlayerMinion pm, ArmorStand armor, PlayerMinionStat stat, Location spawn, CallBackAPI<Boolean> action) {
      if (armor != null && pm != null) {
         Location l = pm.getSelected("PLACE");
         if (l != null) {
            if (!this.plugin.getAdm().isStackable(l)) {
               l.getBlock().setType(this.place.getType());
               stat.setGenerated(stat.getGenerated() + 1);
               armor.teleport(armor.getLocation());
               armor.setItemInHand(this.getHandItem());
               Bukkit.getServer().getPluginManager().callEvent(new MinionPlaceEvent(pm, l.getBlock()));
            }

            action.done(false);
         } else {
            Location lo = pm.getSelected("BREAK");
            if (lo != null) {
               if (!this.plugin.getAdm().isStackable(lo)) {
                  MinionUtils_1_17.damageBlock(lo, -1);
                  armor.setItemInHand(this.place);
                  lo.getBlock().setType(Material.AIR);
                  Bukkit.getServer().getPluginManager().callEvent(new MinionBreakEvent(pm, lo.getBlock()));
               }

               action.done(true);
            }
         }

      }
   }

   public boolean check(Location spawn) {
      boolean yes = true;

      for(Vector l : this.miners) {
         Location lo = spawn.clone().add(l.getX(), l.getY(), l.getZ());
         Material t = lo.getBlock().getType();
         if (!t.equals(Material.AIR) && !t.equals(this.place.getType())) {
            yes = false;
         }
      }

      return yes;
   }

   public Location checkAround(Location loc) {
      for(Vector l : this.miners) {
         Location lo = loc.clone().add(l.getX(), l.getY(), l.getZ());
         if (lo.getBlock().getType().equals(Material.AIR)) {
            return lo;
         }
      }

      return null;
   }

   public Location getAroundRandom(Location loc) {
      return loc.clone().add((Vector)this.miners.get(ThreadLocalRandom.current().nextInt(this.miners.size())));
   }

   public Location getAroundRandomReady(Location loc) {
      Location lo = loc.clone();
      lo.add((Vector)this.miners.get(ThreadLocalRandom.current().nextInt(this.miners.size())));
      return lo.clone().add((double)0.0F, (double)1.0F, (double)0.0F);
   }
}
