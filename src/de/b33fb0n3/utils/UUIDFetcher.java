package de.b33fb0n3.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    /**
     * Date when name changes were introduced
     * @see UUIDFetcher#getUUIDAt(String, long)
     */
    public static final long FEBRUARY_2015 = 1422748800000L;

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

    private static Map<String, UUID> uuidCache = new HashMap<String, UUID>();
    private static Map<UUID, String> nameCache = new HashMap<UUID, String>();

    private static ExecutorService pool = Executors.newCachedThreadPool();

    private String name;
    private UUID id;

    /**
     * Fetches the uuid asynchronously and passes it to the consumer
     *
     * @param name The name
     * @param action Do what you want to do with the uuid her
     */
    public static void getUUID(String name, Consumer<UUID> action) {
        pool.execute(() -> action.accept(getUUID(name)));
    }

    /**
     * Fetches the uuid synchronously and returns it
     *
     * @param name The name
     * @return The uuid
     */
    public static UUID getUUID(String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    /**
     * Fetches the uuid synchronously for a specified name and time and passes the result to the consumer
     *
     * @param name The name
     * @param timestamp Time when the player had this name in milliseconds
     * @param action Do what you want to do with the uuid her
     */
    public static void getUUIDAt(String name, long timestamp, Consumer<UUID> action) {
        pool.execute(() -> action.accept(getUUIDAt(name, timestamp)));
    }

    /**
     * Fetches the uuid synchronously for a specified name and time
     *
     * @param name The name
     * @param timestamp Time when the player had this name in milliseconds
     * @see UUIDFetcher#FEBRUARY_2015
     */
    public static UUID getUUIDAt(String name, long timestamp) {
        name = name.toLowerCase();
        if (uuidCache.containsKey(name)) {
            return uuidCache.get(name);
        }
        try {
            String s = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/"+name+"?at=" + timestamp).openStream(), "UTF-8").useDelimiter("\\A").next();
            String searchedUUID = parseJSON(s, "id");
            searchedUUID = searchedUUID.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
            uuidCache.put(name, UUID.fromString(searchedUUID));
            nameCache.put(UUID.fromString(searchedUUID), name);
            return UUID.fromString(searchedUUID);
        } catch (Exception ignored) {

        }

        return null;
    }

    /**
     * Fetches the name asynchronously and passes it to the consumer
     *
     * @param uuid The uuid
     * @param action Do what you want to do with the name her
     */
    public static void getName(UUID uuid, Consumer<String> action) {
        pool.execute(() -> action.accept(getName(uuid)));
    }

    /**
     * Fetches the name synchronously and returns it
     *
     * @param uuid The uuid
     * @return The name
     */
    public static String getName(UUID uuid) {
        if (nameCache.containsKey(uuid)) {
            return nameCache.get(uuid);
        }
        try {
            String s = new Scanner(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString()).openStream(), "UTF-8").useDelimiter("\\A").next();
            String name = parseJSON(s, "name");
            nameCache.put(uuid, name);
            uuidCache.put(name, uuid);
            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String parseJSON(final String json, final String key) {
        final JsonElement element = new JsonParser().parse(json);
        if (element instanceof JsonNull) {
            return null;
        }
        final JsonElement obj = ((JsonObject)element).get(key);
        return (obj != null) ? obj.toString().replaceAll("\"", "") : null;
    }
}