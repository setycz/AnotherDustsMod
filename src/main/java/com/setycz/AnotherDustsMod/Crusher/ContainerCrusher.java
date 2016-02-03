package com.setycz.AnotherDustsMod.Crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 29.01.2016.
 */
public class ContainerCrusher extends Container {

    private final IInventory tileEntity;
    private int playerInventoryStart;

    public ContainerCrusher(InventoryPlayer playerInventory, IInventory tileEntity) {
        this.tileEntity = tileEntity;

        this.addSlotToContainer(new Slot(tileEntity, 0, 56, 17));
        this.addSlotToContainer(new Slot(tileEntity, 1, 56, 53));
        this.addSlotToContainer(new Slot(tileEntity, 2, 116, 35));

        playerInventoryStart = addPlayerInventory(playerInventory);
    }

    private int addPlayerInventory(InventoryPlayer playerInventory) {
        int start = this.inventorySlots.size();

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        return start;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return null;
        }

        ItemStack slotItemStack = slot.getStack();
        ItemStack previousItemStack = slotItemStack.copy();

        int end = inventorySlots.size();
        if (index < playerInventoryStart) {
            if (!mergeItemStack(slotItemStack, playerInventoryStart, end, true)) {
                return null;
            }
        }
        else {
            if (!mergeItemStack(slotItemStack, 0, playerInventoryStart, false)) {
                return null;
            }
        }

        if (slotItemStack.stackSize == 0) {
            slot.putStack(null);
        }
        else {
            slot.onSlotChanged();
        }

        if (slotItemStack.stackSize == previousItemStack.stackSize) {
            return null;
        }
        slot.onPickupFromSlot(playerIn, slotItemStack);
        return previousItemStack;
    }
}
