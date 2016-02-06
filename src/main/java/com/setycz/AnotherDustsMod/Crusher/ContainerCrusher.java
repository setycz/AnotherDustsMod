package com.setycz.AnotherDustsMod.Crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by setyc on 29.01.2016.
 */
public class ContainerCrusher extends Container {

    private final IInventory crusherInventory;
    private int playerInventoryStart;
    private int progress;
    private int energy;
    private int energyCapacity;

    public ContainerCrusher(InventoryPlayer playerInventory, IInventory crusherInventory) {
        this.crusherInventory = crusherInventory;

        this.addSlotToContainer(new Slot(crusherInventory, 0, 56, 17));
        this.addSlotToContainer(new Slot(crusherInventory, 1, 56, 53));
        this.addSlotToContainer(new Slot(crusherInventory, 2, 116, 35));

        playerInventoryStart = addPlayerInventory(playerInventory);
    }

    public void onCraftGuiOpened(ICrafting listener)
    {
        super.onCraftGuiOpened(listener);
        listener.sendAllWindowProperties(this, crusherInventory);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.crusherInventory.setField(id, data);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (ICrafting icrafting : this.crafters) {
            if (this.progress != this.crusherInventory.getField(2)) {
                icrafting.sendProgressBarUpdate(this, 2, this.crusherInventory.getField(2));
            }
            if (this.energy != this.crusherInventory.getField(0)) {
                icrafting.sendProgressBarUpdate(this, 0, this.crusherInventory.getField(0));
            }
            if (this.energyCapacity != this.crusherInventory.getField(1)) {
                icrafting.sendProgressBarUpdate(this, 1, this.crusherInventory.getField(1));
            }
        }

        this.progress = this.crusherInventory.getField(2);
        this.energy = this.crusherInventory.getField(0);
        this.energyCapacity = this.crusherInventory.getField(1);
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
        return crusherInventory.isUseableByPlayer(playerIn);
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
