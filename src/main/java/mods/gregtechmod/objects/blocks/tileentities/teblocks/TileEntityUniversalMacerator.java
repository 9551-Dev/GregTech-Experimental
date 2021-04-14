package mods.gregtechmod.objects.blocks.tileentities.teblocks;

import ic2.api.recipe.Recipes;
import mods.gregtechmod.api.recipe.GtRecipes;
import mods.gregtechmod.api.recipe.IRecipePulverizer;
import mods.gregtechmod.compat.ModHandler;
import mods.gregtechmod.gui.GuiAutoMacerator;
import mods.gregtechmod.objects.blocks.tileentities.teblocks.base.TileEntityBasicMachineSingleInput;
import mods.gregtechmod.objects.blocks.tileentities.teblocks.container.ContainerBasicMachine;
import mods.gregtechmod.recipe.RecipePulverizer;
import mods.gregtechmod.recipe.compat.ModRecipes;
import mods.gregtechmod.util.GtUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TileEntityUniversalMacerator extends TileEntityBasicMachineSingleInput<IRecipePulverizer> {
    private boolean addSecondaryOutput;

    public TileEntityUniversalMacerator() {
        super("universal_macerator", GtRecipes.pulverizer);
    }

    public static void initMaceratorRecipes() {
        ItemStack gravel = new ItemStack(Blocks.GRAVEL);
        ModHandler.getTEPulverizerRecipes().stream()
                .filter(recipe -> !recipe.getInput().apply(gravel))
                .forEach(GtRecipes.pulverizer::addRecipe);

        ModHandler.getRockCrusherRecipes()
                .forEach(GtRecipes.pulverizer::addRecipe);

        StreamSupport.stream(Recipes.macerator.getRecipes().spliterator(), false)
                .collect(Collectors.toList())
                .stream()
                .map(recipe -> RecipePulverizer.create(ModRecipes.convertInput(recipe.getInput()), new ArrayList<>(recipe.getOutput()), 2, 0, false, false))
                .forEach(GtRecipes.pulverizer::addRecipe);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
        return new GuiAutoMacerator(new ContainerBasicMachine<>(player, this));
    }

    @Override
    public IRecipePulverizer getRecipe() {
        relocateStacks();

        if (this.queueOutputSlot.isEmpty() && this.outputSlot.isEmpty()) {
            ItemStack input = getInput();
            IRecipePulverizer recipe = this.recipeManager.getRecipeFor(input);
            if (recipe != null) this.addSecondaryOutput = GtUtil.RANDOM.nextInt(100) < recipe.getChance();
            return recipe;
        } else this.outputBlocked = true;

        return null;
    }

    @Override
    public void addOutput(List<ItemStack> processResult) {
        this.queueOutputSlot.add(processResult.get(0));
        if (processResult.size() > 1 && this.addSecondaryOutput) this.outputSlot.add(processResult.get(1));

        dumpOutput();
    }
}
