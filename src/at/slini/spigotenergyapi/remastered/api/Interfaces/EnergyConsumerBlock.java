package at.slini.spigotenergyapi.remastered.api.Interfaces;

public interface EnergyConsumerBlock extends EnergyBlock {
    void setUsageEnergyPerSecond(double useenergypersecond);
    void toggleEnergyConsume(Boolean state);
}