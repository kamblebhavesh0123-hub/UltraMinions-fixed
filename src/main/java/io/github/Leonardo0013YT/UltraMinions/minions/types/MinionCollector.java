package io.github.Leonardo0013YT.UltraMinions.minions.types;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionChest;
import io.github.Leonardo0013YT.UltraMinions.database.minion.PlayerMinionStat;
import io.github.Leonardo0013YT.UltraMinions.minions.Minion;
import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class MinionCollector extends Minion {
   public MinionCollector(Main plugin, YamlConfiguration minion, String path, File f) {
      super(plugin, minion, path, f);
   }

   public void update(PlayerMinion pm, ArmorStand armor, PlayerMinionStat stat, Location spawn, CallBackAPI<Boolean> action) {
      if (armor != null && pm != null) {
         if (spawn.getChunk().isLoaded()) {
            if (pm.isChest()) {
               armor.setItemInHand(this.getHandItem());
               PlayerMinionChest pmc = pm.getChest();

               for(Entity e : (Collection)spawn.getWorld().getNearbyEntities(spawn, (double)5.0F, (double)5.0F, (double)5.0F).stream().filter((ex) -> ex instanceof Item).collect(Collectors.toList())) {
                  Item i = (Item)e;
                  if (pmc.addItem(i.getItemStack())) {
                     i.remove();
                     pm.setFull(false);
                  } else {
                     pm.setFull(true);
                  }
               }
            }

         }
      }
   }

   public boolean check(Location spawn) {
      return true;
   }
}
