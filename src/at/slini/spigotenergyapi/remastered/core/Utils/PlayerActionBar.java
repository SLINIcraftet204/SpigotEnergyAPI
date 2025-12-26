package at.slini.spigotenergyapi.remastered.core.Utils;

import at.slini.spigotenergyapi.remastered.core.Blocks.impls.EnergyBlockimpl;
import at.slini.spigotenergyapi.remastered.core.Managers.EnergyProvider;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerActionBar {
    public static void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    checkPlayerLookingAtBlock(player);
                }
            }
        }.runTaskTimer(SpigotEnergyAPI.getInstance(), 1L, 5L);
    }

    private static void checkPlayerLookingAtBlock(Player player) {
        int maxDistance = 6;


        Block targetBlock = player.getTargetBlock(null, maxDistance);

        EnergyBlockimpl EnergyBlock = new EnergyBlockimpl(targetBlock);

        if (targetBlock.getType() != Material.AIR) {
            if (EnergyProvider.hasStoredEnergyInBlock(EnergyBlock)){
                String message = SpigotEnergyAPI.config.getString("energyprefix") + ": " + EnergyProvider.getStoredEnergyForBlock(EnergyBlock);

                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        }
    }
}
