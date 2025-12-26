package at.slini.spigotenergyapi.remastered.core.Commands;

import com.jeff_media.customblockdata.CustomBlockData;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class GetCustomBlockData implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            Block block = p.getTargetBlock(null, 3);
            if (block.getType() != Material.AIR) {
                PersistentDataContainer customBlockData = new CustomBlockData(block, SpigotEnergyAPI.getInstance());

                Set<NamespacedKey> keys = customBlockData.getKeys();
                if (!keys.isEmpty()) {
                    for (NamespacedKey key : keys) {
                        String value = getValueAsString(customBlockData, key);
                        p.sendMessage("Key: " + key.getKey() + " -> Value: " + value);
                    }
                } else {
                    p.sendMessage("No custom data found for this block.");
                }
            } else {
                p.sendMessage("No block in sight or block is AIR!");
            }
        } else {
            sender.sendMessage("You are not a player.");
        }
        return true;
    }

    private String getValueAsString(PersistentDataContainer container, NamespacedKey key) {
        if (container.has(key, PersistentDataType.INTEGER)) {
            return String.valueOf(container.get(key, PersistentDataType.INTEGER));
        } else if (container.has(key, PersistentDataType.STRING)) {
            return container.get(key, PersistentDataType.STRING);
        } else if (container.has(key, PersistentDataType.BYTE_ARRAY)) {
            byte[] byteArray = container.get(key, PersistentDataType.BYTE_ARRAY);
            return byteArray != null ? new String(byteArray) : "null";
        } else if (container.has(key, PersistentDataType.DOUBLE)) {
            return String.valueOf(container.get(key, PersistentDataType.DOUBLE));
        } else if (container.has(key, PersistentDataType.FLOAT)) {
            return String.valueOf(container.get(key, PersistentDataType.FLOAT));
        } else if (container.has(key, PersistentDataType.LONG)) {
            return String.valueOf(container.get(key, PersistentDataType.LONG));
        } else if (container.has(key, PersistentDataType.SHORT)) {
            return String.valueOf(container.get(key, PersistentDataType.SHORT));
        } else if (container.has(key, PersistentDataType.BYTE)) {
            return String.valueOf(container.get(key, PersistentDataType.BYTE));
        } else if (container.has(key, PersistentDataType.INTEGER_ARRAY)) {
            int[] intArray = container.get(key, PersistentDataType.INTEGER_ARRAY);
            return intArray != null ? arrayToString(intArray) : "null";
        } else if (container.has(key, PersistentDataType.LONG_ARRAY)) {
            long[] longArray = container.get(key, PersistentDataType.LONG_ARRAY);
            return longArray != null ? arrayToString(longArray) : "null";
        } else {
            return "Unknown type";
        }
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String arrayToString(long[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
