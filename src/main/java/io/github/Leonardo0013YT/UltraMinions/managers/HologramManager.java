package io.github.Leonardo0013YT.UltraMinions.managers;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class HologramManager {
   private final HashMap<MinionType, ArrayList<String>> noCorrect = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> fully = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> lowFood = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> lowHealth = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> sleeping = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> noHealthNoFood = new HashMap();
   private final HashMap<MinionType, ArrayList<String>> social = new HashMap();
   private final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");
   private final Main plugin;

   public HologramManager(Main plugin) {
      this.plugin = plugin;
   }

   public String replaceColors(String text) {
      text = ChatColor.translateAlternateColorCodes('&', text);
      String color;
      if (Bukkit.getVersion().contains("1.16")) {
         for(Matcher matcher = this.pattern.matcher(text); matcher.find(); text = text.replace(color, "" + ChatColor.of(color.toUpperCase()))) {
            color = text.substring(matcher.start(), matcher.end());
         }
      }

      return text;
   }

   public void reload() {
      this.noCorrect.clear();
      this.fully.clear();
      this.noHealthNoFood.clear();
      this.lowFood.clear();
      this.lowHealth.clear();
      this.sleeping.clear();
      this.social.clear();

      for(MinionType t : MinionType.values()) {
         if (!this.noCorrect.containsKey(t)) {
            this.noCorrect.put(t, new ArrayList());
         }

         if (!this.fully.containsKey(t)) {
            this.fully.put(t, new ArrayList());
         }

         if (!this.social.containsKey(t)) {
            this.social.put(t, new ArrayList());
         }

         if (!this.lowHealth.containsKey(t)) {
            this.lowHealth.put(t, new ArrayList());
         }

         if (!this.lowFood.containsKey(t)) {
            this.lowFood.put(t, new ArrayList());
         }

         if (!this.noHealthNoFood.containsKey(t)) {
            this.noHealthNoFood.put(t, new ArrayList());
         }

         if (!this.sleeping.containsKey(t)) {
            this.sleeping.put(t, new ArrayList());
         }

         for(String msg : this.plugin.getLang().get("holograms.noCorrect." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.noCorrect.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.fully." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.fully.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.lowHealth." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.lowHealth.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.lowFood." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.lowFood.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.noHealthNoFood." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.noHealthNoFood.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.sleeping." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.sleeping.get(t)).add(msg);
         }

         for(String msg : this.plugin.getLang().get("holograms.social." + t.name().toLowerCase()).split("\\n")) {
            ((ArrayList)this.social.get(t)).add(msg);
         }
      }

   }

   public ArrayList<String> getSleepingMessage(MinionType type) {
      return (ArrayList)this.sleeping.get(type);
   }

   public ArrayList<String> getNoCorrectMessage(MinionType type) {
      return (ArrayList)this.noCorrect.get(type);
   }

   public ArrayList<String> getFullyMessage(MinionType type) {
      return (ArrayList)this.fully.get(type);
   }

   public ArrayList<String> getLowFoodMessage(MinionType type) {
      return (ArrayList)this.lowFood.get(type);
   }

   public ArrayList<String> getLowHealthMessage(MinionType type) {
      return (ArrayList)this.lowHealth.get(type);
   }

   public ArrayList<String> getNoHealthNoFood(MinionType type) {
      return (ArrayList)this.noHealthNoFood.get(type);
   }

   public ArrayList<String> getSocialMessage(MinionType type) {
      int i = ThreadLocalRandom.current().nextInt(0, ((ArrayList)this.social.get(type)).size());
      if (i == ((ArrayList)this.social.get(type)).size()) {
         --i;
      }

      return new ArrayList(Collections.singletonList((String)((ArrayList)this.social.get(type)).get(i)));
   }
}
