package mods.gregtechmod.api.cover;

import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.Collection;

public interface ICoverable {
    boolean removeCover(EnumFacing side, boolean simulate);
    @Nullable
    ICover getCoverAtSide(EnumFacing side);
    Collection<? extends ICover> getCovers();
    boolean placeCoverAtSide(ICover cover, EnumFacing side, boolean simulate);
    void markForRenderUpdate();
}