package mods.gregtechmod.objects.blocks.tileentities.machines;

import mods.gregtechmod.core.GregTechConfig;
import mods.gregtechmod.objects.blocks.tileentities.machines.base.TileEntityDigitalChestBase;

public class TileEntityDigitalChest extends TileEntityDigitalChestBase {

    public TileEntityDigitalChest() {
        super(GregTechConfig.FEATURES.digitalChestMaxItemCount, false);
    }
}
