package de.rupturereborn.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Entscheidet, ob Kampf-Module laufen dürfen: im Einzelspieler immer,
 * auf Servern nur, wenn die Adresse in "rupturereborn-server.txt" (im Spielordner) steht.
 */
public final class ServerGate {
    private static final String FILE_NAME = "rupturereborn-server.txt";
    private static final String TEMPLATE = String.join("\n",
            "# Rupture Reborn: Server, auf denen die Kampf-Module laufen duerfen.",
            "# Eine Adresse pro Zeile, genau so wie in der Minecraft-Serverliste (Port ist optional).",
            "# Beispiel:",
            "# mein-server.de",
            "") + "\n";

    private static Set<String> cached = Set.of();
    private static long lastLoad;

    private ServerGate() {}

    public static boolean allowed(Minecraft mc) {
        if (mc.hasSingleplayerServer()) return true;
        var data = mc.getCurrentServer();
        if (data == null) return false;
        return load().contains(host(data.ip));
    }

    /** true, wenn der Spieler auf einem fremden/entfernten Server ist (nicht im Einzelspieler). */
    public static boolean isRemote(Minecraft mc) {
        return !mc.hasSingleplayerServer() && mc.getCurrentServer() != null;
    }

    /** Trägt den aktuellen Server in rupturereborn-server.txt ein. */
    public static boolean allowCurrent(Minecraft mc) {
        if (!isRemote(mc)) return false;
        String host = host(mc.getCurrentServer().ip);
        Path path = FMLPaths.GAMEDIR.get().resolve(FILE_NAME);
        try {
            if (!Files.exists(path)) Files.writeString(path, TEMPLATE, StandardCharsets.UTF_8);
            if (!load().contains(host)) {
                Files.writeString(path, host + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
            lastLoad = 0; // beim nächsten Check neu einlesen
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static String host(String address) {
        String a = address.trim().toLowerCase(Locale.ROOT);
        if (a.startsWith("[")) {
            int end = a.indexOf(']');
            return end > 0 ? a.substring(1, end) : a;
        }
        int colon = a.lastIndexOf(':');
        if (colon > 0 && a.indexOf(':') == colon) a = a.substring(0, colon);
        return a;
    }

    private static Set<String> load() {
        long now = System.currentTimeMillis();
        if (now - lastLoad < 3000) return cached;
        lastLoad = now;
        Path path = FMLPaths.GAMEDIR.get().resolve(FILE_NAME);
        try {
            if (!Files.exists(path)) Files.writeString(path, TEMPLATE, StandardCharsets.UTF_8);
            Set<String> set = new HashSet<>();
            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                set.add(host(line));
            }
            cached = set;
        } catch (IOException ignored) {
        }
        return cached;
    }
}
