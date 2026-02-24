package meteordevelopment.autominer;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.world.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class AutoMiner extends Module {

    public enum Mode {
        Trees,
        Ores
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(
        new EnumSetting.Builder<Mode>()
            .name("mode")
            .defaultValue(Mode.Trees)
            .build()
    );

    private final Setting<Integer> range = sgGeneral.add(
        new IntSetting.Builder()
            .name("range")
            .defaultValue(5)
            .min(1)
            .max(6)
            .build()
    );

    public AutoMiner() {
        super(Category.World, "auto-miner", "Automatically mines trees or ores.");
    }

    @Override
    public void onTick() {
        BlockPos playerPos = mc.player.getBlockPos();

        for (int x = -range.get(); x <= range.get(); x++) {
            for (int y = -range.get(); y <= range.get(); y++) {
                for (int z = -range.get(); z <= range.get(); z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    Block block = mc.world.getBlockState(pos).getBlock();

                    if (shouldMine(block)) {
                        BlockUtils.breakBlock(pos, true);
                        return;
                    }
                }
            }
        }
    }

    private boolean shouldMine(Block block) {
        return mode.get() == Mode.Trees ? isLog(block) : isOre(block);
    }

    private boolean isLog(Block block) {
        return block.toString().contains("log");
    }

    private boolean isOre(Block block) {
        return block.toString().contains("ore");
    }
}
