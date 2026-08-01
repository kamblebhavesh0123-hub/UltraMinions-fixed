package io.github.Leonardo0013YT.UltraMinions.minions.types;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class MinionFisher extends Minion {
   private ArrayList<Vector> fisher = new ArrayList(Arrays.asList(new Vector(1, -1, 0), new Vector(0, -1, 1), new Vector(1, -1, 1), new Vector(-1, -1, 0), new Vector(0, -1, -1), new Vector(-1, -1, -1), new Vector(1, -1, -1), new Vector(-1, -1, 1), new Vector(2, -1, 0), new Vector(2, -1, -1), new Vector(2, -1, 1), new Vector(-2, -1, 0), new Vector(-2, -1, -1), new Vector(-2, -1, 1), new Vector(0, -1, 2), new Vector(1, -1, 2), new Vector(-1, -1, 2), new Vector(0, -1, -2), new Vector(1, -1, -2), new Vector(-1, -1, -2), new Vector(2, -1, -2), new Vector(2, -1, 2), new Vector(-2, -1, -2), new Vector(-2, -1, 2)));

   public MinionFisher(Main plugin, YamlConfiguration minion, String path, File f) {
      super(plugin, minion, path, f);
   }

   public void update(PlayerMinion pm, ArmorStand armor, PlayerMinionStat stat, Location spawn, CallBackAPI<Boolean> action) {
      if (armor != null && pm != null) {
         if (spawn.getChunk().isLoaded()) {
            Collection<Entity> ents = spawn.getWorld().getNearbyEntities(spawn, Main.get().getCfm().getX(), Main.get().getCfm().getY(), Main.get().getCfm().getZ());
            ents.removeIf((e) -> !e.getType().equals(this.getSpawn()));
            if (!this.getSpawn().equals(EntityType.SQUID) && !this.getSpawn().equals(EntityType.GUARDIAN)) {
               stat.setGenerated(stat.getGenerated() + 1);
               armor.setItemInHand(this.getHandItem());
               action.done(true);
            } else if (ents.size() < 2) {
               stat.setGenerated(stat.getGenerated() + 1);
               Entity entity = spawn.getWorld().spawnEntity(spawn.clone().add((double)1.5F, (double)0.0F, (double)0.0F), this.getSpawn());
               entity.setMetadata("MINION", new FixedMetadataValue(Main.get(), "HUNTER"));
               armor.setItemInHand(new ItemStack(Material.FISHING_ROD));
               action.done(false);
            } else {
               armor.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
               ArrayList<Entity> entities = new ArrayList(ents);
               Entity ent = (Entity)entities.get(ThreadLocalRandom.current().nextInt(0, entities.size()));
               if (ent instanceof Damageable) {
                  Damageable en = (Damageable)ent;
                  if (en.getHealth() - (double)this.getDamage() <= (double)0.0F) {
                     action.done(true);
                  } else {
                     action.done(false);
                  }

                  en.damage((double)this.getDamage());
               }
            }

         }
      }
   }

   public boolean check(Location spawn) {
      boolean yes = true;

      for(Vector l : this.fisher) {
         Location lo = spawn.clone().add(l.getX(), l.getY(), l.getZ());
         Material t = lo.getBlock().getType();
         if (!lo.getBlock().isLiquid() || t.name().contains("LAVA")) {
            yes = false;
         }
      }

      return yes;
   }
}
