package io.github.Leonardo0013YT.UltraMinions.utils;

import io.github.Leonardo0013YT.UltraMinions.Main;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.ProfileProperty;

/**
 * Rewritten for modern Paper (1.20.5+) which removed the legacy versioned
 * net.minecraft.server.<version> reflection this class originally relied on.
 * Custom item data is now stored using the standard PersistentDataContainer
 * API instead of raw NBT reflection. Public method signatures are kept
 * identical to the original so callers elsewhere in the plugin did not need
 * to change.
 */
public final class NBTEditor {

   private static NamespacedKey keyFor(Object... keys) {
      StringBuilder sb = new StringBuilder();
      for (Object k : keys) {
         if (sb.length() > 0) {
            sb.append('.');
         }
         sb.append(String.valueOf(k));
      }
      String flat = sb.toString().toLowerCase().replaceAll("[^a-z0-9._-]", "_");
      return new NamespacedKey(Main.get(), flat);
   }

   private static PersistentDataContainer containerOf(Object object) {
      if (object instanceof ItemStack) {
         ItemMeta meta = ((ItemStack)object).getItemMeta();
         return meta == null ? null : meta.getPersistentDataContainer();
      }
      return null;
   }

   public static ItemStack getHead(String skinURL) {
      ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
      if (skinURL == null || skinURL.isEmpty()) {
         return head;
      }

      ItemMeta meta = head.getItemMeta();
      if (meta instanceof SkullMeta) {
         SkullMeta skullMeta = (SkullMeta)meta;
         PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "MinionHead");
         profile.setProperty(new ProfileProperty("textures", skinURL));
         skullMeta.setPlayerProfile(profile);
         head.setItemMeta(skullMeta);
      }

      return head;
   }

   public static boolean contains(Object object, Object... keys) {
      PersistentDataContainer pdc = containerOf(object);
      if (pdc == null) {
         return false;
      }

      NamespacedKey key = keyFor(keys);
      return pdc.has(key, PersistentDataType.STRING) || pdc.has(key, PersistentDataType.INTEGER)
         || pdc.has(key, PersistentDataType.LONG) || pdc.has(key, PersistentDataType.DOUBLE)
         || pdc.has(key, PersistentDataType.BOOLEAN);
   }

   public static String getString(Object object, Object... keys) {
      PersistentDataContainer pdc = containerOf(object);
      if (pdc == null) {
         return null;
      }

      NamespacedKey key = keyFor(keys);
      if (pdc.has(key, PersistentDataType.STRING)) {
         return pdc.get(key, PersistentDataType.STRING);
      } else if (pdc.has(key, PersistentDataType.INTEGER)) {
         return String.valueOf(pdc.get(key, PersistentDataType.INTEGER));
      } else if (pdc.has(key, PersistentDataType.LONG)) {
         return String.valueOf(pdc.get(key, PersistentDataType.LONG));
      } else if (pdc.has(key, PersistentDataType.DOUBLE)) {
         return String.valueOf(pdc.get(key, PersistentDataType.DOUBLE));
      } else {
         return pdc.has(key, PersistentDataType.BOOLEAN) ? String.valueOf(pdc.get(key, PersistentDataType.BOOLEAN)) : null;
      }
   }

   public static int getInt(Object object, Object... keys) {
      PersistentDataContainer pdc = containerOf(object);
      if (pdc == null) {
         return 0;
      }

      NamespacedKey key = keyFor(keys);
      if (pdc.has(key, PersistentDataType.INTEGER)) {
         Integer v = pdc.get(key, PersistentDataType.INTEGER);
         return v == null ? 0 : v;
      } else if (pdc.has(key, PersistentDataType.LONG)) {
         Long v = pdc.get(key, PersistentDataType.LONG);
         return v == null ? 0 : v.intValue();
      } else if (pdc.has(key, PersistentDataType.STRING)) {
         try {
            return Integer.parseInt(pdc.get(key, PersistentDataType.STRING));
         } catch (NumberFormatException e) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public static long getLong(Object object, Object... keys) {
      PersistentDataContainer pdc = containerOf(object);
      if (pdc == null) {
         return 0L;
      }

      NamespacedKey key = keyFor(keys);
      if (pdc.has(key, PersistentDataType.LONG)) {
         Long v = pdc.get(key, PersistentDataType.LONG);
         return v == null ? 0L : v;
      } else if (pdc.has(key, PersistentDataType.INTEGER)) {
         Integer v = pdc.get(key, PersistentDataType.INTEGER);
         return v == null ? 0L : v.longValue();
      } else if (pdc.has(key, PersistentDataType.STRING)) {
         try {
            return Long.parseLong(pdc.get(key, PersistentDataType.STRING));
         } catch (NumberFormatException e) {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   @SuppressWarnings("unchecked")
   public static <T> T set(T object, Object value, Object... keys) {
      if (!(object instanceof ItemStack)) {
         return object;
      }

      ItemStack item = (ItemStack)object;
      ItemMeta meta = item.getItemMeta();
      if (meta == null) {
         return object;
      }

      PersistentDataContainer pdc = meta.getPersistentDataContainer();
      NamespacedKey key = keyFor(keys);

      if (value instanceof Integer) {
         pdc.set(key, PersistentDataType.INTEGER, (Integer)value);
      } else if (value instanceof Long) {
         pdc.set(key, PersistentDataType.LONG, (Long)value);
      } else if (value instanceof Double || value instanceof Float) {
         pdc.set(key, PersistentDataType.DOUBLE, ((Number)value).doubleValue());
      } else if (value instanceof Boolean) {
         pdc.set(key, PersistentDataType.BOOLEAN, (Boolean)value);
      } else if (value != null) {
         pdc.set(key, PersistentDataType.STRING, String.valueOf(value));
      }

      item.setItemMeta(meta);
      return (T)item;
   }
}
