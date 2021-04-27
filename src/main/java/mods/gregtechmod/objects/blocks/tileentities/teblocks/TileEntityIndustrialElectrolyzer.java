package mods.gregtechmod.objects.blocks.tileentities.teblocks;

import ic2.core.ContainerBase;
import mods.gregtechmod.api.recipe.GtRecipes;
import mods.gregtechmod.gui.GuiIndustrialElectrolyzer;
import mods.gregtechmod.objects.blocks.tileentities.teblocks.container.ContainerIndustrialElectrolyzer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityIndustrialElectrolyzer extends TileEntityIndustrialCentrifugeBase {

    public TileEntityIndustrialElectrolyzer() {
        super("industrial_electrolyzer", 2, 64000, GtRecipes.industrialElectrolyzer, false);
    }

    @Override
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiIndustrialElectrolyzer(new ContainerIndustrialElectrolyzer(player, this));
    }

    @Override
    public ContainerBase<TileEntityIndustrialCentrifugeBase> getGuiContainer(EntityPlayer player) {
        return new ContainerIndustrialElectrolyzer(player, this);
    }
}
