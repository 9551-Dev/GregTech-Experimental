package mods.gregtechmod.objects.blocks.teblocks.base;

import ic2.core.block.state.Ic2BlockState.Ic2BlockStateInstance;
import ic2.core.util.StackUtil;
import mods.gregtechmod.api.cover.CoverType;
import mods.gregtechmod.api.cover.ICover;
import mods.gregtechmod.api.cover.ICoverable;
import mods.gregtechmod.objects.Cover;
import mods.gregtechmod.objects.blocks.teblocks.component.CoverHandler;
import mods.gregtechmod.objects.covers.CoverGeneric;
import mods.gregtechmod.objects.covers.CoverVent;
import mods.gregtechmod.util.GtUtil;
import mods.gregtechmod.util.PropertyHelper;
import mods.gregtechmod.util.PropertyHelper.VerticalRotation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Let's you add/remove covers on a tile entity. <b>Isn't responsible</b> for cover behavior.
 */
public abstract class TileEntityCoverable extends TileEntityAutoNBT implements ICoverable {
    protected final CoverHandler coverHandler;
    protected Set<CoverType> coverBlacklist = new HashSet<>();

    public TileEntityCoverable() {
        this.coverHandler = addComponent(new CoverHandler(this, this::rerender));
    }

    @Override
    protected boolean onActivated(EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (beforeActivated(player.inventory.getCurrentItem(), player, side, hitX, hitY, hitZ)) return true;

        return super.onActivated(player, hand, side, hitX, hitY, hitZ);
    }
    
    protected boolean beforeActivated(ItemStack stack, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (CoverGeneric.isGenericCover(stack)) {
            placeCover(Cover.GENERIC, player, side, stack);
            return true;
        } else if (CoverVent.isVent(stack)) {
            placeCover(Cover.VENT, player, side, stack);
            return true;
        } else if (GtUtil.isScrewdriver(stack)) {
            return onScrewdriverActivated(stack, side, player, hitX, hitY, hitZ);
        } else return attemptUseCrowbar(stack, side, player);
    }
    
    protected boolean onScrewdriverActivated(ItemStack stack, EnumFacing side, EntityPlayer player, float hitX, float hitY, float hitZ) {
        ICover cover = getCoverAtSide(side);
        if (cover != null) {
            if (cover.onScrewdriverClick(player)) {
                stack.damageItem(1, player);
                return true;
            }
        } else return placeCoverAtSide(Cover.NORMAL.instance.get().constructCover(side, this, ItemStack.EMPTY), player, side, false);
        
        return false;
    }

    public boolean attemptUseCrowbar(ItemStack stack, EnumFacing side, EntityPlayer player) {
        if (GtUtil.isCrowbar(stack)) {
            if (removeCover(side, false)) {
                stack.damageItem(1, player);
                return true;
            }
        }
        return false;
    }

    private void placeCover(Cover cover, EntityPlayer player, EnumFacing side, ItemStack stack) { //For generic covers and vents
        ItemStack coverStack = StackUtil.copyWithSize(stack, 1);
        if (placeCoverAtSide(cover.instance.get().constructCover(side, this, coverStack), player, side, false) && !player.capabilities.isCreativeMode) stack.shrink(1);
    }

    @Override
    protected ItemStack getPickBlock(EntityPlayer player, RayTraceResult target) {
        if (target != null) {
            ICover cover = getCoverAtSide(target.sideHit);
            if (cover != null) return cover.getItem();
        }
        return super.getPickBlock(player, target);
    }

    @Override
    protected Ic2BlockStateInstance getExtendedState(Ic2BlockStateInstance state) {
        Ic2BlockStateInstance ret = state.withProperty(PropertyHelper.VERTICAL_ROTATION_PROPERTY, getVerticalRotation());
        return this.coverHandler != null ? ret.withProperty(CoverHandler.COVER_HANDLER_PROPERTY, this.coverHandler) : ret;
    }
    
    protected VerticalRotation getVerticalRotation() {
        return VerticalRotation.MIRROR_BACK;
    }

    @Override
    public ICover getCoverAtSide(EnumFacing side) {
        return this.coverHandler.covers.get(side);
    }
    
    @Override
    public void getNetworkedFields(List<? super String> list) {
        list.add("coverHandler");
    }

    @Override
    public Collection<? extends ICover> getCovers() {
        return this.coverHandler.covers.values();
    }

    @Override
    public boolean placeCoverAtSide(ICover cover, EntityPlayer player, EnumFacing side, boolean simulate) {
        if (this.coverBlacklist.contains(cover.getType())) return false;
        return this.coverHandler.placeCoverAtSide(cover, side, simulate);
    }

    @Override
    public boolean removeCover(EnumFacing side, boolean simulate) {
        if (this.coverHandler.covers.containsKey(side)) {
            ICover cover = this.coverHandler.covers.get(side);
            ItemStack coverItem = cover.getItem();
            if (this.coverHandler.removeCover(side, false)) {
                if (coverItem != null) {
                    EntityItem entity = new EntityItem(this.world, pos.getX() + side.getXOffset() + 0.5, pos.getY() + side.getYOffset() + 0.5, pos.getZ()+side.getZOffset() + 0.5, coverItem);
                    entity.motionX = 0;
                    entity.motionY = 0;
                    entity.motionZ = 0;
                    if (!this.world.isRemote) this.world.spawnEntity(entity);
                }
                return true;
            }
        }
        
        return false;
    }

    @Override
    protected List<ItemStack> getWrenchDrops(EntityPlayer player, int fortune) {
        List<ItemStack> ret = super.getWrenchDrops(player, fortune);
        for (ICover cover : coverHandler.covers.values()) ret.add(cover.getItem());
        return ret;
    }

    @Override
    public void updateRender() {
        rerender();
    }
}