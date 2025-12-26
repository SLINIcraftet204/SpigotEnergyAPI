package at.slini.spigotenergyapi.remastered.api.Events;

import at.slini.spigotenergyapi.remastered.api.Interfaces.EnergyBlock;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EnergyBlockUpdateEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final EnergyBlock energyBlock;
    private boolean cancelled;

    public EnergyBlockUpdateEvent(EnergyBlock energyBlock) {
        this.energyBlock = energyBlock;
    }
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public EnergyBlock getEnergyBlock() {
        return this.energyBlock;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
