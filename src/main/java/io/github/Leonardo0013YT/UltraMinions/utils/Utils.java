package io.github.Leonardo0013YT.UltraMinions.utils;

import io.github.Leonardo0013YT.UltraMinions.Main;
import io.github.Leonardo0013YT.UltraMinions.database.PlayerMinion;
import io.github.Leonardo0013YT.UltraMinions.enums.MinionFace;
import io.github.Leonardo0013YT.UltraMinions.minions.levels.MinionLevel;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
   private static DecimalFormat df = new DecimalFormat("##.#");
   private static String version;

   public static int getMaxPages(int size, int maxPerPage) {
      return size / Math.max(maxPerPage, 1) + 1;
   }

   public static boolean is1_14to1_16() {
      return !version.equals("v1_13_R1") && !version.equals("v1_13_R2");
   }

   public static void damageBlock(Location l, int damage) {
      MinionUtils_1_17.damageBlock(l, damage);
   }

   public static void addMinionUUID(UUID uuid) {
      Main plugin = Main.get();
      List<String> uuids = plugin.getTemp().getListOrDefault("minionsData", new ArrayList());
      uuids.add(uuid.toString());
      plugin.getTemp().set("minionsData", uuids);
      plugin.getTemp().save();
   }

   public static boolean isMinionUUID(UUID uuid) {
      Main plugin = Main.get();
      List<String> uuids = plugin.getTemp().getListOrDefault("minionsData", new ArrayList());
      return uuids.contains(uuid.toString());
   }

   public static void removeMinionUUID(UUID uuid) {
      Main plugin = Main.get();
      List<String> uuids = plugin.getTemp().getListOrDefault("minionsData", new ArrayList());
      uuids.remove(uuid.toString());
      if (uuids.isEmpty()) {
         plugin.getTemp().set("minionsData", (Object)null);
      } else {
         plugin.getTemp().set("minionsData", uuids);
      }

      plugin.getTemp().save();
   }

   public static int getMaxSlots(PlayerMinion pm) {
      MinionLevel ml;
      for(ml = pm.getMinionLevel(); ml == null; ml = pm.getMinionLevel()) {
         if (pm.getStat().getLevel() <= 1) {
            return 1;
         }

         pm.getStat().setLevel(pm.getStat().getLevel() - 1);
      }

      int slots = ml.getMax() / 64;
      if (ml.getMax() % 64 != 0) {
         ++slots;
      }

      return slots;
   }

   public static MinionFace getMinionFace(Location start, Location to) {
      double x0 = start.getX();
      double px = to.getX();
      double z0 = start.getZ();
      double pz = to.getZ();
      double rotation = Math.atan2(x0 - px, z0 - pz) * (180D / Math.PI);
      if ((double)0.0F <= rotation && rotation < (double)22.5F) {
         return MinionFace.SOUTH;
      } else if ((double)22.5F <= rotation && rotation < (double)67.5F) {
         return MinionFace.SOUTHWEST;
      } else if ((double)67.5F <= rotation && rotation < (double)112.5F) {
         return MinionFace.WEST;
      } else if ((double)112.5F <= rotation && rotation < (double)157.5F) {
         return MinionFace.NORTHWEST;
      } else if ((double)157.5F <= rotation && rotation < (double)202.5F) {
         return MinionFace.NORTH;
      } else if ((double)202.5F <= rotation && rotation < (double)247.5F) {
         return MinionFace.NORTHEAST;
      } else if ((double)247.5F <= rotation && rotation < (double)292.5F) {
         return MinionFace.EAST;
      } else {
         return (double)292.5F <= rotation && rotation < (double)337.5F ? MinionFace.SOUTHEAST : MinionFace.SOUTH;
      }
   }

   public static boolean isMax(Block block) {
      if (block.getBlockData() instanceof Ageable) {
         Ageable age = (Ageable)block.getBlockData();
         return age.getAge() >= age.getMaximumAge();
      } else {
         return true;
      }
   }

   public static boolean isSimilar(ItemStack first, ItemStack second) {
      boolean similar = false;
      if (first != null && second != null) {
         boolean sameTypeId = first.getType().equals(second.getType());
         boolean sameDurability = first.getDurability() == second.getDurability();
         boolean sameAmount = first.getAmount() == second.getAmount();
         boolean sameHasItemMeta = false;
         if ((!first.hasItemMeta() || second.hasItemMeta()) && (first.hasItemMeta() || !second.hasItemMeta())) {
            if (first.hasItemMeta() && second.hasItemMeta()) {
               sameHasItemMeta = checkBoolean(first.hasItemMeta(), second.hasItemMeta());
            }

            boolean sameEnchantments = first.getEnchantments().equals(second.getEnchantments());
            boolean sameItemMeta = true;
            if (sameHasItemMeta) {
               boolean sameHasDisplayName = checkBoolean(first.getItemMeta().hasDisplayName(), second.getItemMeta().hasDisplayName());
               boolean sameHasLore = checkBoolean(first.getItemMeta().hasLore(), second.getItemMeta().hasLore());
               if (sameHasDisplayName && sameHasLore) {
                  sameItemMeta = first.getItemMeta().getDisplayName().equals(second.getItemMeta().getDisplayName()) && first.getItemMeta().getLore().equals(second.getItemMeta().getLore());
               } else if (sameHasDisplayName) {
                  sameItemMeta = first.getItemMeta().getDisplayName().equals(second.getItemMeta().getDisplayName());
               } else if (sameHasLore) {
                  sameItemMeta = first.getItemMeta().getLore().equals(second.getItemMeta().getLore());
               }
            }

            if (sameTypeId && sameDurability && sameAmount && sameEnchantments && sameItemMeta) {
               similar = true;
            }

            return similar;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   static boolean checkBoolean(boolean b1, boolean b2) {
      if (!b1 && !b2) {
         return false;
      } else {
         return b1 == b2;
      }
   }

   public static String ObjectOrDefaultString(Object ob, String def) {
      return ob == null ? def : ob.toString();
   }

   public static void check(String path, Object value, YamlConfiguration minion, File f) {
      if (!minion.isSet(path)) {
         minion.set(path, value);

         try {
            minion.save(f);
         } catch (IOException var5) {
         }
      }

   }

   public static String format(double value) {
      return df.format(value);
   }

   public static Location getStringLocation(String location) {
      String[] l = location.split(";");
      World world = Bukkit.getWorld(l[0]);
      double x = Double.parseDouble(l[1]);
      double y = Double.parseDouble(l[2]);
      double z = Double.parseDouble(l[3]);
      float yaw = Float.parseFloat(l[4]);
      float pitch = Float.parseFloat(l[5]);
      return new Location(world, x, y, z, yaw, pitch);
   }

   public static String getLocationString(Location loc) {
      String var10000 = loc.getWorld().getName();
      return var10000 + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
   }

   public static String parseBoolean(boolean b) {
      return b ? Main.get().getLang().get("enabled") : Main.get().getLang().get("disabled");
   }

   public static String IntegerToRomanNumeral(int input) {
      if (input >= 1 && input <= 50) {
         StringBuilder s;
         for(s = new StringBuilder(); input >= 50; input -= 50) {
            s.append("L");
         }

         while(input >= 40) {
            s.append("XL");
            input -= 40;
         }

         while(input >= 10) {
            s.append("X");
            input -= 10;
         }

         while(input >= 9) {
            s.append("IX");
            input -= 9;
         }

         while(input >= 5) {
            s.append("V");
            input -= 5;
         }

         while(input >= 4) {
            s.append("IV");
            input -= 4;
         }

         while(input >= 1) {
            s.append("I");
            --input;
         }

         return s.toString();
      } else {
         return "C";
      }
   }

   public static MinionFace getCardinalDirection(Player player) {
      double rotation = (double)player.getLocation().getYaw();
      if ((double)0.0F <= rotation && rotation < (double)22.5F) {
         return MinionFace.SOUTH;
      } else if ((double)22.5F <= rotation && rotation < (double)67.5F) {
         return MinionFace.SOUTHWEST;
      } else if ((double)67.5F <= rotation && rotation < (double)112.5F) {
         return MinionFace.WEST;
      } else if ((double)112.5F <= rotation && rotation < (double)157.5F) {
         return MinionFace.NORTHWEST;
      } else if ((double)157.5F <= rotation && rotation < (double)202.5F) {
         return MinionFace.NORTH;
      } else if ((double)202.5F <= rotation && rotation < (double)247.5F) {
         return MinionFace.NORTHEAST;
      } else if ((double)247.5F <= rotation && rotation < (double)292.5F) {
         return MinionFace.EAST;
      } else {
         return (double)292.5F <= rotation && rotation < (double)337.5F ? MinionFace.SOUTHEAST : MinionFace.SOUTH;
      }
   }

   public static String convertTime(int timeInSeconds) {
      int hours = timeInSeconds / 3600;
      int secondsLeft = timeInSeconds - hours * 3600;
      int minutes = secondsLeft / 60;
      int seconds = secondsLeft - minutes * 60;
      String formattedTime = "";
      if (hours > 0) {
         if (hours < 10) {
            formattedTime = formattedTime + "0";
         }

         formattedTime = formattedTime + hours + "h:";
      }

      if (minutes < 10) {
         formattedTime = formattedTime + "0";
      }

      formattedTime = formattedTime + minutes + "m:";
      if (seconds < 10) {
         formattedTime = formattedTime + "0";
      }

      formattedTime = formattedTime + seconds + "s";
      return formattedTime;
   }

   public static String getFormatedLocation(Location loc) {
      if (loc == null) {
         return "§cNot set!";
      } else {
         String var10000 = loc.getWorld().getName();
         return var10000 + ", " + df.format(loc.getX()) + ", " + df.format(loc.getY()) + ", " + df.format(loc.getZ());
      }
   }

   public static void addItems(Player p, HashMap<ItemStack, Integer> items, int max) {
      boolean drop = false;
      int gived = 0;

      for(Map.Entry<ItemStack, Integer> entry : items.entrySet()) {
         if (entry.getKey() != null && !((ItemStack)entry.getKey()).getType().equals(Material.AIR)) {
            int stack = (Integer)entry.getValue() / 64;
            int rest = (Integer)entry.getValue() % 64;

            for(int i = 0; i < stack; ++i) {
               ItemStack item = ((ItemStack)entry.getKey()).clone();
               item.setAmount(64);
               if (p.getInventory().firstEmpty() == -1) {
                  p.getWorld().dropItemNaturally(p.getLocation(), item);
                  drop = true;
               } else {
                  p.getInventory().addItem(new ItemStack[]{item});
               }
            }

            if (rest > 0) {
               ItemStack add = ((ItemStack)entry.getKey()).clone();
               add.setAmount(rest);
               if (p.getInventory().firstEmpty() == -1) {
                  p.getWorld().dropItemNaturally(p.getLocation(), add);
                  drop = true;
               } else {
                  p.getInventory().addItem(new ItemStack[]{add});
               }
            }
         }
      }

      if (drop) {
         p.sendMessage(Main.get().getLang().get("messages.fullInventory"));
      }

   }

   public static void addItems(Player p, ItemStack... items) {
      boolean drop = false;

      for(ItemStack i : items) {
         if (p.getInventory().firstEmpty() == -1) {
            p.getWorld().dropItemNaturally(p.getLocation(), i);
            drop = true;
         } else {
            p.getInventory().addItem(new ItemStack[]{i});
         }
      }

      if (drop) {
         p.sendMessage(Main.get().getLang().get("messages.fullInventory"));
      }

   }

   static {
      try {
         version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
      } catch (Exception var1) {
      }

   }
}
