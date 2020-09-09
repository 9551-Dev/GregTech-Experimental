package mods.gregtechmod.common.objects.items.base;

import ic2.core.item.BaseElectricItem;
import mods.gregtechmod.common.core.GregtechMod;
import mods.gregtechmod.common.util.IModelInfoProvider;
import mods.gregtechmod.common.util.ModelInformation;

@SuppressWarnings("NullableProblems")
public abstract class ItemElectricBase extends BaseElectricItem implements IModelInfoProvider {
    private final String name;

    public ItemElectricBase(String name, double maxCharge, double transferLimit, int tier) {
        super(null, maxCharge, transferLimit, tier);
        setRegistryName(name);
        this.name = name;
    }

    @Override
    public String getTranslationKey() {
        return "item."+this.name; //TODO: Can this be exchanged for a setTranslationKey?
    }

    @Override
    public ModelInformation getModelInformation() {
        return new ModelInformation(GregtechMod.getModelResourceLocation(this.name, "tool"));
    }
}
