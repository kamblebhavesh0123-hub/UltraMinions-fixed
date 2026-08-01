package io.github.Leonardo0013YT.UltraMinions;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Settings {
   private YamlConfiguration config;
   private File file;
   private Main u;
   private boolean hexColor;

   public Settings(Main u, String s, boolean defaults, boolean hexColor) {
      this.u = u;
      this.hexColor = hexColor;
      this.file = new File(u.getDataFolder(), s + ".yml");
      this.config = YamlConfiguration.loadConfiguration(this.file);
      Reader reader = new InputStreamReader(u.getResource(s + ".yml"), StandardCharsets.UTF_8);
      YamlConfiguration loadConfiguration = YamlConfiguration.loadConfiguration(reader);

      try {
         if (!this.file.exists()) {
            this.config.addDefaults(loadConfiguration);
            this.config.options().copyDefaults(true);
            this.save();
         } else {
            if (defaults) {
               this.config.addDefaults(loadConfiguration);
               this.config.options().copyDefaults(true);
               this.save();
            }

            this.config.load(this.file);
         }
      } catch (InvalidConfigurationException | IOException var8) {
      }

   }

   public File getFile() {
      return this.file;
   }

   public void reload() {
      try {
         this.config.load(this.file);
      } catch (InvalidConfigurationException | IOException e) {
         ((Exception)e).printStackTrace();
      }

   }

   public void save() {
      try {
         this.config.save(this.file);
      } catch (IOException var2) {
      }

   }

   public YamlConfiguration getConfig() {
      return this.config;
   }

   public String get(String s) {
      if (this.config.getString(s) == null) {
         return "";
      } else {
         String text = this.config.getString(s).replaceAll("<arrow>", "➤");
         return this.hexColor ? this.u.getHm().replaceColors(text) : text;
      }
   }

   public String getOrDefault(String s, String def) {
      if (this.config.isSet(s)) {
         return this.get(s);
      } else {
         this.set(s, def);
         this.save();
         return def;
      }
   }

   public int getInt(String s) {
      return this.config.getInt(s);
   }

   public int getIntOrDefault(String s, int def) {
      if (this.config.isSet(s)) {
         return this.getInt(s);
      } else {
         this.set(s, def);
         this.save();
         return def;
      }
   }

   public List<String> getList(String s) {
      return this.config.getStringList(s);
   }

   public List<String> getListOrDefault(String s, ArrayList<String> def) {
      return (List<String>)(this.config.isSet(s) ? this.getList(s) : def);
   }

   public boolean isSet(String s) {
      return this.config.isSet(s);
   }

   public void set(String s, Object o) {
      this.config.set(s, o);
   }

   public boolean getBoolean(String s) {
      return this.config.getBoolean(s);
   }

   public boolean getBooleanOrDefault(String s, boolean def) {
      if (this.config.isSet(s)) {
         return this.getBoolean(s);
      } else {
         this.set(s, def);
         this.save();
         return def;
      }
   }
}
