package io.github.Leonardo0013YT.UltraMinions.placeholders;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerData;
import io.github.Leonardo0013YT.UltraMinions.tiers.Tier;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {
   private Main plugin;

   public Placeholders(Main plugin) {
      this.plugin = plugin;
   }

   public String getIdentifier() {
      return "um";
   }

   public String getName() {
      return "UltraMinions";
   }

   public String getAuthor() {
      return "Leonardo0013YT";
   }

   public String getVersion() {
      return "1.0.0";
   }

   public String onPlaceholderRequest(Player p, String id) {
      PlayerData pd = PlayerData.getPlayerData(p);
      Tier tier = this.plugin.getTm().getTier(pd);
      if (id.equals("minions_size")) {
         return "" + pd.getMinionSize();
      } else if (id.equals("max_size")) {
         return tier == null ? "0" : "" + tier.getMax();
      } else if (id.equals("next_required")) {
         if (this.plugin.getTm().getTier(pd) == null) {
            return "0";
         } else {
            Tier tnext = this.plugin.getTm().getNextTier(tier);
            return "" + tnext.getRequired();
         }
      } else if (id.equals("next_unlocked")) {
         return "" + pd.getUnlocked();
      } else if (id.equals("next_restant")) {
         if (this.plugin.getTm().getTier(pd) == null) {
            return "0";
         } else {
            Tier tnext = this.plugin.getTm().getNextTier(tier);
            return "" + (tnext.getRequired() - pd.getUnlocked());
         }
      } else if (id.startsWith("unlocked")) {
         String[] s = id.split("_");
         String key = s[1];
         int level = Integer.parseInt(s[3]);
         return pd.isUnlocked(key, level) ? this.plugin.getLang().get("unlocked") : this.plugin.getLang().get("locked");
      } else {
         return null;
      }
   }
}
