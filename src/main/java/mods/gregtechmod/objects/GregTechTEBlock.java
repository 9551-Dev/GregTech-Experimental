package mods.gregtechmod.objects;

import ic2.api.item.ITeBlockSpecialItem;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.item.block.ItemBlockTileEntity;
import ic2.core.profile.Version;
import ic2.core.ref.IC2Material;
import ic2.core.ref.TeBlock;
import ic2.core.ref.TeBlock.DefaultDrop;
import ic2.core.ref.TeBlock.HarvestTool;
import ic2.core.ref.TeBlock.ITePlaceHandler;
import ic2.core.util.Util;
import mods.gregtechmod.api.util.Reference;
import mods.gregtechmod.core.GregTechMod;
import mods.gregtechmod.objects.blocks.teblocks.*;
import mods.gregtechmod.objects.blocks.teblocks.computercube.TileEntityComputerCube;
import mods.gregtechmod.objects.blocks.teblocks.energy.*;
import mods.gregtechmod.objects.blocks.teblocks.generator.*;
import mods.gregtechmod.objects.blocks.teblocks.multiblock.*;
import mods.gregtechmod.objects.blocks.teblocks.struct.*;
import mods.gregtechmod.util.LazyValue;
import mods.gregtechmod.util.nbt.NBTSaveHandler;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

public enum GregTechTEBlock implements ITeBlock, ITeBlock.ITeBlockCreativeRegisterer, ITeBlockSpecialItem {
    INDUSTRIAL_CENTRIFUGE(TileEntityIndustrialCentrifuge.class, 1, true, Collections.singleton(EnumFacing.NORTH), false, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    DIGITAL_CHEST(TileEntityDigitalChest.class, 2, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, -1, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    QUANTUM_CHEST(TileEntityQuantumChest.class, 3, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, -1, 30, EnumRarity.UNCOMMON, IC2Material.MACHINE),
    QUANTUM_TANK(TileEntityQuantumTank.class, 4, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, -1, 30, EnumRarity.UNCOMMON, IC2Material.MACHINE),
    SONICTRON(TileEntitySonictron.class, 5, true, Collections.emptySet(), false, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.DEFAULT),
    AUTO_MACERATOR(TileEntityAutoMacerator.class, 6, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AUTO_EXTRACTOR(TileEntityAutoExtractor.class, 7, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AUTO_COMPRESSOR(TileEntityAutoCompressor.class, 8, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AUTO_RECYCLER(TileEntityAutoRecycler.class, 9, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AUTO_ELECTRIC_FURNACE(TileEntityAutoElectricFurnace.class, 10, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    WIREMILL(TileEntityWiremill.class, 11, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    ALLOY_SMELTER(TileEntityAlloySmelter.class, 12, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AUTO_CANNER(TileEntityAutoCanner.class, 13, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    BENDER(TileEntityBender.class, 14, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    UNIVERSAL_MACERATOR(TileEntityUniversalMacerator.class, 15, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    MICROWAVE(TileEntityMicrowave.class, 16, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    PRINTER(TileEntityPrinter.class, 17, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    ASSEMBLER(TileEntityAssembler.class, 18, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    LATHE(TileEntityLathe.class, 19, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    INDUSTRIAL_ELECTROLYZER(TileEntityIndustrialElectrolyzer.class, 20, true, Collections.singleton(EnumFacing.NORTH), false, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    CHEMICAL_REACTOR(TileEntityChemicalReactor.class, 21, true, Collections.singleton(EnumFacing.NORTH), true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    INDUSTRIAL_BLAST_FURNACE(TileEntityIndustrialBlastFurnace.class, 22, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    INDUSTRIAL_GRINDER(TileEntityIndustrialGrinder.class, 23, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    IMPLOSION_COMPRESSOR(TileEntityImplosionCompressor.class, 24, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    VACUUM_FREEZER(TileEntityVacuumFreezer.class, 25, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    DISTILLATION_TOWER(TileEntityDistillationTower.class, 26, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    THERMAL_GENERATOR(TileEntityThermalGenerator.class, 27, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    DIESEL_GENERATOR(TileEntityDieselGenerator.class, 28, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    GT_SEMIFLUID_GENERATOR(TileEntitySemifluidGenerator.class, 29, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    GAS_TURBINE(TileEntityGasTurbine.class, 30, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    PLASMA_GENERATOR(TileEntityPlasmaGenerator.class, 31, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    MAGIC_ENERGY_CONVERTER(TileEntityMagicEnergyConverter.class, 32, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    LIGHTNING_ROD(TileEntityLightningRod.class, 33, false, Collections.singleton(EnumFacing.NORTH), true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    MAGIC_ENERGY_ABSORBER(TileEntityMagicEnergyAbsorber.class, 34, true, Collections.singleton(EnumFacing.NORTH), true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    DRAGON_EGG_ENERGY_SIPHON(TileEntityDragonEggEnergySiphon.class, 35, true, Collections.singleton(EnumFacing.NORTH), true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    HATCH_INPUT(TileEntityHatchInput.class, 36, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    HATCH_OUTPUT(TileEntityHatchOutput.class, 37, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    HATCH_MAINTENANCE(TileEntityHatchMaintenance.class, 38, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    HATCH_DYNAMO(TileEntityHatchDynamo.class, 39, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    HATCH_MUFFLER(TileEntityHatchMuffler.class, 40, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    INDUSTRIAL_SAWMILL(TileEntityIndustrialSawmill.class, 41, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.BAKED, true),
    THERMAL_BOILER(TileEntityThermalBoiler.class, 42, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    LARGE_STEAM_TURBINE(TileEntityLargeSteamTurbine.class, 43, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    LARGE_GAS_TURBINE(TileEntityLargeGasTurbine.class, 44, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    SUPERCONDENSATOR(TileEntitySupercondensator.class, 45, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    SUPERCONDUCTOR_WIRE(TileEntitySuperconductorWire.class, 46, false, Util.noFacings, false, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE, ModelType.CONNECTED),
    LESU(TileEntityLESU.class, 47, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    AESU(TileEntityAESU.class, 48, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    IDSU(TileEntityIDSU.class, 49, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    COMPUTER_CUBE(TileEntityComputerCube.class, 50, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    CHARGE_O_MAT(TileEntityChargeOMat.class, 51, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    ADVANCED_PUMP(TileEntityAdvancedPump.class, 52, true, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    ADVANCED_SAFE(TileEntityAdvancedSafe.class, 53, false, Util.horizontalFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    MATTER_FABRICATOR(TileEntityMatterFabricator.class, 54, true, Collections.singleton(EnumFacing.NORTH), true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    GT_TELEPORTER(TileEntityGtTeleporter.class, 55, true, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    TESSERACT_GENERATOR(TileEntityTesseractGenerator.class, 56, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE),
    TESSERACT_TERMINAL(TileEntityTesseractTerminal.class, 57, false, Util.allFacings, true, HarvestTool.Wrench, DefaultDrop.Self, 10, 30, EnumRarity.COMMON, IC2Material.MACHINE);

    public static final ResourceLocation LOCATION = new ResourceLocation(Reference.MODID, "teblock");
    public static final GregTechTEBlock[] VALUES = values();
    public static BlockTileEntity blockTE;
    
    private final Class<? extends TileEntityBlock> teClass;
    private final int itemMeta;
    private final boolean hasActive;
    private final Set<EnumFacing> supportedFacings;
    private final boolean allowWrenchRotating;
    private final HarvestTool harvestTool;
    private final DefaultDrop defaultDrop;
    private final float hardness;
    private final float explosionResistance;
    private final EnumRarity rarity;
    private final Material material;
    private final ModelType modelType;
    private final boolean isStructure;
    
    private final LazyValue<TileEntityBlock> dummyTe = new LazyValue<>(this::buildDummyTeBlock);
    private ITePlaceHandler placeHandler;

    GregTechTEBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material) {
        this(teClass, itemMeta, hasActive, supportedFacings, allowWrenchRotating, harvestTool, defaultDrop, hardness, explosionResistance, rarity, material, ModelType.BAKED, false);
    }
        
    GregTechTEBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material, ModelType modelType) {
        this(teClass, itemMeta, hasActive, supportedFacings, allowWrenchRotating, harvestTool, defaultDrop, hardness, explosionResistance, rarity, material, modelType, false);
    }
    
    GregTechTEBlock(Class<? extends TileEntityBlock> teClass, int itemMeta, boolean hasActive, Set<EnumFacing> supportedFacings, boolean allowWrenchRotating, HarvestTool harvestTool, DefaultDrop defaultDrop, float hardness, float explosionResistance, EnumRarity rarity, Material material, ModelType modelType, boolean isStructure) {
        this.teClass = teClass;
        this.itemMeta = itemMeta;
        this.hasActive = hasActive;
        this.supportedFacings = supportedFacings;
        this.allowWrenchRotating = allowWrenchRotating;
        this.harvestTool = harvestTool;
        this.defaultDrop = defaultDrop;
        this.hardness = hardness;
        this.explosionResistance = explosionResistance;
        this.rarity = rarity;
        this.material = material;
        this.modelType = modelType;
        this.isStructure = isStructure;

        GameRegistry.registerTileEntity(teClass, new ResourceLocation(Reference.MODID, getName()));
        NBTSaveHandler.initClass(teClass);
 	}
 	
 	public boolean isStructure() {
        return this.isStructure;
    }

    public ModelType getModelType() {
        return this.modelType;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return LOCATION;
    }

    @Override
    public boolean hasItem() {
        return true;
    }

    @Nonnull
    @Override
    public Class<? extends TileEntityBlock> getTeClass() {
        return this.teClass;
    }

    @Override
    public boolean hasActive() {
        return this.hasActive;
    }

    @Override
    public Set<EnumFacing> getSupportedFacings() {
        return this.supportedFacings;
    }

    @Override
    public float getHardness() {
        return this.hardness;
    }

    @Override
    public float getExplosionResistance() {
        return this.explosionResistance;
    }

    @Override
    public TeBlock.HarvestTool getHarvestTool() {
        return this.harvestTool;
    }

    @Override
    public DefaultDrop getDefaultDrop() {
        return this.defaultDrop;
    }

    @Override
    public EnumRarity getRarity() {
        return this.rarity;
    }

    @Override
    public boolean allowWrenchRotating() {
        return this.allowWrenchRotating;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public void setPlaceHandler(ITePlaceHandler handler) {
        if (this.placeHandler != null) throw new RuntimeException("Value already present");
        this.placeHandler = handler;
    }

    @Nullable
    @Override
    public ITePlaceHandler getPlaceHandler() {
        return this.placeHandler;
    }

    @Nullable
    @Override
    public TileEntityBlock getDummyTe() {
        return this.dummyTe.get();
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public int getId() {
        return this.itemMeta;
    }

    @Override
    public void addSubBlocks(NonNullList<ItemStack> list, BlockTileEntity block, ItemBlockTileEntity item, CreativeTabs tab) {
        if (tab == CreativeTabs.SEARCH) {
            Arrays.stream(VALUES)
                    .filter(teblock -> Version.shouldEnable(teblock.teClass))
                    .map(block::getItemStack)
                    .forEach(list::add);
        }
    }
    
    public TileEntityBlock buildDummyTeBlock() {
        if (this.teClass != null) {
            try {
                GregTechMod.LOGGER.info("Building dummy TeBlock for {}", this.name().toLowerCase(Locale.ROOT));
                return this.teClass.newInstance();
            } catch (Exception e) {
                GregTechMod.LOGGER.catching(e);
            }
        }
        return null;
 	}

    @Override
    public boolean doesOverrideDefault(ItemStack itemStack) {
        return this.modelType != ModelType.DEFAULT;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack) {
        String name = getName();
        String location = Reference.MODID + ":teblock/" + name;
        if (isStructure) location += "_valid";
        else if (this.hasActive) location += "_active";
        return new ModelResourceLocation(location, name);
    }
    
    public enum ModelType {
        DEFAULT,
        BAKED,
        CONNECTED
    }
}
