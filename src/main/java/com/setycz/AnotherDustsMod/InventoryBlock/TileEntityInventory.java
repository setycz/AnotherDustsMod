package com.setycz.AnotherDustsMod.InventoryBlock;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

/**
 * Created by setyc on 29.01.2016.
 */
public abstract class TileEntityInventory extends TileEntity implements IInventory, IInventoryGui {

    ItemStack[] inventory;
    String inventoryTitle;
    boolean hasCustomName;

    protected TileEntityInventory(String name, int size) {
        inventory = new ItemStack[size];
        inventoryTitle = name;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < 0 || index >= inventory.length) {
            return null;
        }
        return inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = getStackInSlot(index);
        if (itemStack == null) {
            return null;
        }

        if (itemStack.stackSize <= count) {
            setInventorySlotContents(index, null);
            markDirty();
            return itemStack;
        }

        ItemStack resultItemStack = itemStack.splitStack(count);
        markDirty();
        return resultItemStack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack itemStack = inventory[index];
        setInventorySlotContents(index, null);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (index < 0 || index >= inventory.length) {
            return;
        }

        inventory[index] = stack;

        int inventoryStackLimit = getInventoryStackLimit();
        if(stack != null && stack.stackSize >= inventoryStackLimit) {
            stack.stackSize = inventoryStackLimit;
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index < getSizeInventory()) {
            ItemStack itemStack = getStackInSlot(index);
            if (itemStack == null || itemStack.stackSize + stack.stackSize <= getInventoryStackLimit()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int index = 0; index < getSizeInventory(); index++) {
            setInventorySlotContents(index, null);
        }
    }

    @Override
    public abstract Container createContainer(InventoryPlayer inventoryplayer, World world, BlockPos pos);

    @Override
    public abstract GuiContainer createGui(InventoryPlayer inventoryplayer, World world, BlockPos pos);


    @Override
    public String getName() {
        return inventoryTitle;
    }

    @Override
    public boolean hasCustomName() {
        return hasCustomName;
    }

    public void setCustomName(String customName) {
        this.hasCustomName = true;
        this.inventoryTitle = customName;
    }

    @Override
    public IChatComponent getDisplayName() {
        if(hasCustomName()) {
            return new ChatComponentText(getName());
        }
        return new ChatComponentTranslation(getName());
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound itemTag = nbttaglist.getCompoundTagAt(i);
            int slot = itemTag.getByte("Slot") & 255;

            if(slot >= 0 && slot < getSizeInventory()) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemTag));
            }
        }

        if(compound.hasKey("CustomName", 8)) {
            this.inventoryTitle = compound.getString("CustomName");
            this.hasCustomName = true;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < getSizeInventory(); i++) {
            if(getStackInSlot(i) != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                getStackInSlot(i).writeToNBT(itemTag);
                nbttaglist.appendTag(itemTag);
            }
        }
        compound.setTag("Items", nbttaglist);

        if(this.hasCustomName()) {
            compound.setString("CustomName", this.inventoryTitle);
        }
    }
}
