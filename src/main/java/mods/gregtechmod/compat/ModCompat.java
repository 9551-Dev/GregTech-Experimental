package mods.gregtechmod.compat;

import ic2.core.util.StackUtil;
import mods.gregtechmod.api.GregTechAPI;
import mods.gregtechmod.api.util.Reference;
import mods.gregtechmod.core.GregTechMod;
import mods.gregtechmod.init.FluidLoader;
import mods.gregtechmod.objects.BlockItems;
import mods.gregtechmod.util.GtUtil;
import mods.railcraft.api.fuel.FluidFuelManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModCompat {

    public static void registerTools() {
        GregTechMod.logger.info("Registering various tools to be usable on GregTech machines");
        if (ModHandler.projectredCore) {
            ItemStack screwdriver = ModHandler.getPRItem("screwdriver", OreDictionary.WILDCARD_VALUE);
            GregTechAPI.registerScrewdriver(screwdriver);
        }
        if (ModHandler.railcraft) {
            ItemStack ironCrowbar = ModHandler.getRCItem("tool_crowbar_iron", OreDictionary.WILDCARD_VALUE);
            ItemStack steelCrowbar = ModHandler.getRCItem("tool_crowbar_steel", OreDictionary.WILDCARD_VALUE);
            GregTechAPI.registerCrowbar(ironCrowbar);
            GregTechAPI.registerCrowbar(steelCrowbar);
        }
    }

    public static void registerBoilerFuels() {
        if (ModHandler.railcraft) _registerBoilerFuels();
    }

    @Optional.Method(modid = "railcraft")
    private static void _registerBoilerFuels() {
        GregTechMod.logger.info("Adding fuels to Railcraft's boiler");
        FluidFuelManager.addFuel(FluidLoader.Gas.HYDROGEN.getFluid(), 2000);
        FluidFuelManager.addFuel(FluidLoader.Gas.METHANE.getFluid(), 3000);
        if (GregTechMod.classic) FluidFuelManager.addFuel(FluidLoader.Liquid.NITRO_COALFUEL.getFluid(), 18000);
        FluidFuelManager.addFuel(FluidLoader.Liquid.LITHIUM.getFluid(), 24000);
    }

    public static void addRollingMachineRecipes() {
        if (!ModHandler.railcraft) return;
        GregTechMod.logger.info("Adding Rolling Machine recipes");

        addRollingMachineRecipe("coil_kanthal", new ItemStack(BlockItems.Component.COIL_KANTHAL.getInstance(), 3), "AAA", "BCC", "BBC", 'A', GregTechMod.classic ? "ingotRefinedIron" : "ingotIron", 'B', "ingotChrome", 'C', "ingotAluminium");
        addRollingMachineRecipe("coil_nichrome", new ItemStack(BlockItems.Component.COIL_NICHROME.getInstance()), " B ", "BAB", " B ", 'A', "ingotChrome", 'B', "ingotNickel");
        addRollingMachineRecipe("coil_cupronickel", new ItemStack(BlockItems.Component.COIL_CUPRONICKEL.getInstance()), "BAB", "A A", "BAB", 'A', "ingotCopper", 'B', "ingotNickel");
        ItemStack railStandard = ModHandler.getRCItem("rail");
        addRollingMachineRecipe("rail_standard", StackUtil.setSize(railStandard, 4), "X X", "X X", "X X", 'X', "ingotAluminium");
        addRollingMachineRecipe("rail_standard_2", StackUtil.setSize(railStandard, 32), "X X", "X X", "X X", 'X', "ingotTitanium");
        addRollingMachineRecipe("rail_standard_3", railStandard, "X X", "X X", "X X", 'X', "ingotTungsten");
        ItemStack railReinforced = StackUtil.copyWithSize(railStandard, 32);
        railReinforced.setItemDamage(4);
        addRollingMachineRecipe("rail_reinforced", railStandard, "X X", "X X", "X X", 'X', "ingotTungstenSteel");
        ItemStack rebar = ModHandler.getRCItem("rebar");
        addRollingMachineRecipe("rebar", StackUtil.setSize(rebar, 2), "  X", " X ", "X  ", 'X', "ingotAluminium");
        addRollingMachineRecipe("rebar_2", StackUtil.setSize(rebar, 16), "  X", " X ", "X  ", 'X', "ingotTitanium");
        addRollingMachineRecipe("rebar_3", StackUtil.setSize(rebar, 16), "  X", " X ", "X  ", 'X', "ingotTungsten");
        addRollingMachineRecipe("rebar_4", StackUtil.setSize(rebar, 48), "  X", " X ", "X  ", 'X', "ingotTungstenSteel");
        ItemStack postMetal = ModHandler.getRCItem("post_metal");
        ItemStack postMetalLightBlue = StackUtil.setSize(GtUtil.copyWithMeta(postMetal, 3), 8);
        addRollingMachineRecipe("post_metal_light_blue", postMetalLightBlue, "XXX", " X ", "XXX", 'X', "ingotAluminium");
        addRollingMachineRecipe("post_metal_light_blue_2", postMetalLightBlue, "X X", "XXX", "X X", 'X', "ingotAluminium");
        ItemStack postMetalPurple = StackUtil.setSize(GtUtil.copyWithMeta(postMetal, 10), 64);
        addRollingMachineRecipe("post_metal_purple", postMetalPurple, "XXX", " X ", "XXX", 'X', "ingotTitanium");
        addRollingMachineRecipe("post_metal_purple_2", postMetalPurple, "X X", "XXX", "X X", 'X', "ingotTitanium");
        ItemStack postMetalBlack = StackUtil.setSize(GtUtil.copyWithMeta(postMetal, 15), 64);
        addRollingMachineRecipe("post_metal_black", postMetalBlack, "XXX", " X ", "XXX", 'X', "ingotTungsten");
        addRollingMachineRecipe("post_metal_black", postMetalBlack, "X X", "XXX", "X X", 'X', "ingotTungsten");
    }

    public static void addRollingMachineRecipe(String name, ItemStack output, Object... pattern) {
        if (ModHandler.railcraft) ModHandler.addRollingMachineRecipe(name, output, pattern);
        else GameRegistry.addShapedRecipe(
                new ResourceLocation(Reference.MODID, name),
                null,
                output,
                pattern
        );
    }
}