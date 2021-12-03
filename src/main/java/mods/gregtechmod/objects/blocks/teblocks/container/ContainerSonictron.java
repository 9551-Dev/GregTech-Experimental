package mods.gregtechmod.objects.blocks.teblocks.container;

import ic2.core.ContainerBase;
import ic2.core.util.StackUtil;
import mods.gregtechmod.api.GregTechAPI;
import mods.gregtechmod.api.util.SonictronSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ContainerSonictron extends ContainerBase<IInventory> {

    public ContainerSonictron(IInventory base) {
        super(base);

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                addSlotToContainer(new Slot(base, i + j * 8, 24 + 16 * i, 19 + 16 * j));
            }
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        if (slotId < 0) return super.slotClick(slotId, dragType, clickType, player);
        
        Slot slot = this.inventorySlots.get(slotId);
        ItemStack content = slot.getStack();
        List<SonictronSound> sonictronSounds = GregTechAPI.instance().getSonictronSounds();

        if (clickType == ClickType.QUICK_MOVE) slot.putStack(ItemStack.EMPTY);
        else if (clickType == ClickType.PICKUP && dragType < 1) {
            if (content.isEmpty()) slot.putStack(sonictronSounds.get(0).item.copy());
            else {
                for (int i = 1; i < sonictronSounds.size(); i++) {
                    if (StackUtil.checkItemEquality(content, sonictronSounds.get(i - 1).item)) {
                        slot.putStack(sonictronSounds.get(i).item.copy());
                        return ItemStack.EMPTY;
                    }
                }
                slot.putStack(ItemStack.EMPTY);
            }
        }
        else if (clickType != ClickType.PICKUP_ALL && !content.isEmpty()) {
            for (SonictronSound sonictronSound : sonictronSounds) {
                if (StackUtil.checkItemEquality(content, sonictronSound.item)) {
                    content.grow(1);
                    content.setCount(content.getCount() % (sonictronSound.count + 1));
                    if (content.getCount() == 0) content.grow(1);
                    break;
                }
            }
        }

        return ItemStack.EMPTY;
    }
}