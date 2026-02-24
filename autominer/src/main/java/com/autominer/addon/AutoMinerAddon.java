package meteordevelopment.autominer;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;

public class AutoMinerAddon extends MeteorAddon {

    @Override
    public void onInitialize() {
        Modules.get().add(new AutoMiner());
    }

    @Override
    public String getPackage() {
        return "meteordevelopment.autominer";
    }
}
