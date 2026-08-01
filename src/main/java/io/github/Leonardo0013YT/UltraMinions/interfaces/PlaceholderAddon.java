package io.github.Leonardo0013YT.UltraMinions.interfaces;

import java.util.List;
import org.bukkit.entity.Player;

public interface PlaceholderAddon {
   String parsePlaceholders(Player var1, String var2);

   List<String> parsePlaceholders(Player var1, List<String> var2);
}
