package at.slini.spigotenergyapi.remastered.core.Bootstrap;

import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.HexFormat;

public final class ExternalLibraryBootstrap {

    private ExternalLibraryBootstrap() {}

    public static final String CUSTOM_BLOCK_DATA_CLASS = "com.jeff_media.customblockdata.CustomBlockData";
    public static final String CUSTOM_BLOCK_DATA_URL =
            "https://repo1.maven.org/maven2/com/jeff-media/custom-block-data/2.2.5/custom-block-data-2.2.5.jar";

    /**
     * Ensures CustomBlockData is available at runtime.
     * Downloads the jar into plugins/<YourPlugin>/libs and appends it to the plugin classloader.
     *
     * IMPORTANT:
     * - Call this as early as possible (onLoad or very first in onEnable)
     * - Avoid direct imports/usages of CustomBlockData until this method returns true
     */
    public static boolean ensureCustomBlockData(Plugin plugin) {
        // 1) Already present?
        if (isClassPresent(CUSTOM_BLOCK_DATA_CLASS, plugin.getClass().getClassLoader())) {
            plugin.getLogger().info("CustomBlockData already available.");
            return true;
        }

        try {
            Path libsDir = plugin.getDataFolder().toPath().resolve("libs");
            Files.createDirectories(libsDir);

            Path jarPath = libsDir.resolve("custom-block-data-2.2.5.jar");

            // 2) Download if missing
            if (!Files.exists(jarPath) || Files.size(jarPath) == 0) {
                plugin.getLogger().warning("CustomBlockData missing. Downloading...");
                downloadTo(CUSTOM_BLOCK_DATA_URL, jarPath);
                plugin.getLogger().info("Downloaded CustomBlockData to: " + jarPath.toAbsolutePath());
            }

            // 3) Append to plugin classloader
            if (!appendJarToPluginClassLoader(plugin, jarPath.toUri().toURL())) {
                plugin.getLogger().severe("Failed to append CustomBlockData jar to classloader.");
                return false;
            }

            // 4) Verify again
            boolean ok = isClassPresent(CUSTOM_BLOCK_DATA_CLASS, plugin.getClass().getClassLoader());
            if (ok) {
                plugin.getLogger().info("CustomBlockData loaded successfully.");
            } else {
                plugin.getLogger().severe("CustomBlockData still not found after loading jar.");
            }
            return ok;

        } catch (Exception e) {
            plugin.getLogger().severe("Failed to bootstrap CustomBlockData: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isClassPresent(String fqcn, ClassLoader loader) {
        try {
            Class.forName(fqcn, false, loader);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private static void downloadTo(String url, Path target) throws IOException {
        Path tmp = target.resolveSibling(target.getFileName() + ".tmp");
        Files.deleteIfExists(tmp);

        URLConnection conn = new URL(url).openConnection();
        conn.setConnectTimeout(10_000);
        conn.setReadTimeout(30_000);
        conn.setRequestProperty("User-Agent", "SpigotEnergyAPI-LibraryBootstrap/1.0");

        try (InputStream in = conn.getInputStream()) {
            Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);
        }

        // atomic-ish replace
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }

    /**
     * Attempts to append a jar URL to the plugin's classloader (Paper/Spigot usually uses URLClassLoader).
     */
    private static boolean appendJarToPluginClassLoader(Plugin plugin, URL jarUrl) {
        ClassLoader cl = plugin.getClass().getClassLoader();

        try {
            // Most Bukkit/Paper plugin classloaders are URLClassLoader (or extend it)
            // We reflectively call addURL(URL).
            var method = cl.getClass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(cl, jarUrl);
            return true;
        } catch (NoSuchMethodException e) {
            // Some environments hide addURL; try URLClassLoader parent method via reflection
            try {
                var method = java.net.URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(cl, jarUrl);
                return true;
            } catch (Exception ex) {
                plugin.getLogger().severe("ClassLoader does not support addURL: " + ex.getMessage());
                return false;
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to add jar to classloader: " + e.getMessage());
            return false;
        }
    }

    // Optional helper if you want to pin integrity (not required, but nice):
    public static String sha256(Path file) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try (InputStream in = Files.newInputStream(file);
             DigestInputStream dis = new DigestInputStream(in, md)) {
            byte[] buf = new byte[8192];
            while (dis.read(buf) != -1) { /* read through */ }
        }
        return HexFormat.of().formatHex(md.digest());
    }
}
