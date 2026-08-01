package io.github.Leonardo0013YT.UltraMinions.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class InterfaceDataSave implements JsonSerializer, JsonDeserializer {
   private static final String CLASSNAME = "PlayerDataSave";
   private static final String DATA = "DATA";

   public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
      JsonObject jsonObject = jsonElement.getAsJsonObject();
      JsonPrimitive prim = (JsonPrimitive)jsonObject.get("PlayerDataSave");
      String className = prim.getAsString();
      Class<?> klass = this.getObjectClass(className);
      return jsonDeserializationContext.deserialize(jsonObject.get("DATA"), klass);
   }

   public JsonElement serialize(Object jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("PlayerDataSave", jsonElement.getClass().getName());
      jsonObject.add("DATA", jsonSerializationContext.serialize(jsonElement));
      return jsonObject;
   }

   public Class<?> getObjectClass(String className) {
      try {
         return Class.forName(className);
      } catch (ClassNotFoundException e) {
         throw new JsonParseException(e.getMessage());
      }
   }
}
