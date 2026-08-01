package io.github.Leonardo0013YT.UltraMinions.addons;

import io.github.Leonardo0013YT.UltraMinions.interfaces.PlaceholderAddon;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderAPIAddon implements PlaceholderAddon {
   public String parsePlaceholders(Player p, String value) {
      return PlaceholderAPI.setPlaceholders(p, value);
   }

   public List<String> parsePlaceholders(Player p, List<String> value) {
      return PlaceholderAPI.setPlaceholders(p, value);
   }
}
