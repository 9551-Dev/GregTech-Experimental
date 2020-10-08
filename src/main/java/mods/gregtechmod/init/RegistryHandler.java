package mods.gregtechmod.init;

import mods.gregtechmod.core.GregTechMod;
import mods.gregtechmod.core.GregTechTEBlock;
import mods.gregtechmod.cover.RenderTeBlock;
import mods.gregtechmod.objects.blocks.tileentities.TileEntityLightSource;
import mods.gregtechmod.util.IModelInfoProvider;
import mods.gregtechmod.util.JsonHandler;
import mods.gregtechmod.util.ModelInformation;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        BlockItemLoader.init();
        event.getRegistry().registerAll(BlockItemLoader.BLOCKS.toArray(new Block[0]));
        GameRegistry.registerTileEntity(TileEntityLightSource.class, new ResourceLocation(GregTechMod.MODID, "light_source"));
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(BlockItemLoader.ITEMS.toArray(new Item[0]));
    }

    public static void registerFluids() {
        FluidLoader.init();
        GregTechMod.LOGGER.info("Registering fluids");
        FluidLoader.FLUIDS.forEach(fluid -> {
            FluidRegistry.registerFluid(fluid);
            FluidRegistry.addBucketForFluid(fluid);
        });
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        BlockItemLoader.BLOCKS
                .forEach(block -> registerModel(Item.getItemFromBlock(block)));

        BlockItemLoader.ITEMS.stream()
                .filter(item -> item instanceof IModelInfoProvider)
                .forEach(item -> {
                    ModelInformation info = ((IModelInfoProvider) item).getModelInformation();
                    registerModel(item, info.metadata, info.path);
                });
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item) {
        registerModel(item, 0, item.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item, int metadata, ResourceLocation path) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(path, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerBakedModels() {
        GregTechMod.LOGGER.info("Registering baked models");
        BakedModelLoader loader = new BakedModelLoader();
        for (GregTechTEBlock teBlock : GregTechTEBlock.values()) {
            try {
                if (teBlock.hasBakedModel()) {
                    String name = teBlock.getName();
                    JsonHandler json = new JsonHandler(name);
                    loader.register("models/block/"+name, new RenderTeBlock(json.textures, json.particle));
                    if (teBlock.hasActive()) {
                        json = new JsonHandler(name+"_active");
                        loader.register("models/block/"+name+"_active", new RenderTeBlock(json.textures, json.particle));
                    }
                }
            } catch (Exception e) {
                GregTechMod.LOGGER.error(e.getMessage());
            }
        }
        ModelLoaderRegistry.registerLoader(loader);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerIcons(TextureStitchEvent.Pre event) {
        TextureMap map = event.getMap();
        String path = "blocks/covers/";
        String centrifuge = "blocks/machines/centrifuge/";
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"adv_machine_vent"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"adv_machine_vent_rotating"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, centrifuge+"centrifuge_top_active2"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, centrifuge+"centrifuge_top_active3"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, centrifuge+"centrifuge_side_active2"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, centrifuge+"centrifuge_side_active3"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, "blocks/machines/adv_machine_screen_random")); //TODO: Remove when implemented in another machine
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"machine_vent_rotating"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"drain"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"active_detector"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"eu_meter"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"item_meter"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"liquid_meter"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"normal"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"noredstone"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"machine_controller"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"solar_panel"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"crafting"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"conveyor"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"pump"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"valve"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"energy_only"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"redstone_only"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"redstone_conductor"));
        map.registerSprite(new ResourceLocation(GregTechMod.MODID, path+"redstone_signalizer"));

        for (FluidLoader.Liquids type : FluidLoader.Liquids.values()) {
            map.registerSprite(type.texture);
        }
        for (FluidLoader.Gases type : FluidLoader.Gases.values()) {
            map.registerSprite(type.texture);
        }
    }
}
