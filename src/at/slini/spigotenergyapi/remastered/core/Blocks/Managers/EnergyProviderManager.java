package at.slini.spigotenergyapi.remastered.core.Blocks.Managers;

import at.slini.spigotenergyapi.remastered.api.EnergyProvider;
import at.slini.spigotenergyapi.remastered.core.SpigotEnergyAPI;

public class EnergyProviderManager implements EnergyProvider {
    @Override
    public String getEnergyPrefix() {
        return SpigotEnergyAPI.configtemp.getString("energy-prefix");
    }

}
