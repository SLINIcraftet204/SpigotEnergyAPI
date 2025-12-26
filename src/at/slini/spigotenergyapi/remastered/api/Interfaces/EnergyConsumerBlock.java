package at.slini.spigotenergyapi.remastered.api.Interfaces;

public interface EnergyConsumerBlock extends EnergyBlock {
    void setusageEnergypersecond(double useenergypersecond);
    void ToggleEnergyConsume(Boolean state);
}