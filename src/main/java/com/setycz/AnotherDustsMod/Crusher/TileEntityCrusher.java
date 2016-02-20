package com.setycz.AnotherDustsMod.Crusher;

import com.setycz.AnotherDustsMod.InventoryBlock.TileEntityInventory;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by setyc on 27.01.2016.
 */
public class TileEntityCrusher extends TileEntityInventory implements ITickable {
    public static final int NEEDED_PROGRESS = 200;
    private static final int inputStackIndex = 0;
    private static final int fuelStackIndex = 1;
    private static final int outputStackIndex = 2;

    private int energy;
    private int energyCapacity;
    private int progress;

    public TileEntityCrusher() {
        super("gui.crusher.name", 3);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        switch (side) {
            case UP:
                return new int[] { inputStackIndex };
            case DOWN:
                return new int[] { outputStackIndex };
            default:
                return new int[] { fuelStackIndex };
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

    @Override
    public Container createContainer(InventoryPlayer inventoryplayer, World world, BlockPos pos) {
        return new ContainerCrusher(inventoryplayer, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(InventoryPlayer inventoryplayer, World world, BlockPos pos) {
        return new GuiCrusher(inventoryplayer, this);
    }

    @Override
    public void update() {
        boolean change = false;
        boolean hadEnergy = hasEnergy();

        if (hasEnergy()) {
            consumeEnergy();
            if (canProcessCurrentItem()) {
                if (!isFinished()) {
                    progress();
                }

                if (isFinished()) {
                    if (canAddToOutput()) {
                        addToOutput();
                        resetProgress();
                    }
                }
            }
            else {
                if (isProgressing()) {
                    resetProgress();
                }
            }
            change = true;
        }
        else {
            if (isProgressing()) {
                resetProgress();
                change = true;
            }
        }

        if (!hasEnergy() && hasFuel() && canProcessCurrentItem()) {
            consumeFuel();
            change = true;
        }

        if (!hadEnergy && hasEnergy()) {
            setIsWorking(true);
        }
        else if (hadEnergy && !hasEnergy()) {
            setIsWorking(false);
        }

        if (change) {
            markDirty();
        }
    }

    private void consumeEnergy() {
        energy--;
    }

    private void resetProgress() {
        progress = 0;
    }

    private void addToOutput() {
        ItemStack inputItemStack = getStackInSlot(inputStackIndex);
        ItemStack processedItemStack = inputItemStack.splitStack(1);
        if (inputItemStack.stackSize <= 0) {
            setInventorySlotContents(inputStackIndex, null);
        }

        ItemStack outputItemStack = getStackInSlot(outputStackIndex);
        ItemStack resultItemStack = CrusherRegistry.createItemsForBlock(Block.getBlockFromItem(processedItemStack.getItem()), processedItemStack.getMetadata());
        if (outputItemStack == null) {
            setInventorySlotContents(outputStackIndex, resultItemStack);
        }
        else {
            outputItemStack.stackSize += resultItemStack.stackSize;
        }
    }

    private boolean canAddToOutput() {
        ItemStack outputItemStack = getStackInSlot(outputStackIndex);
        if (outputItemStack == null) {
            return true;
        }

        ItemStack inputItemStack = getStackInSlot(inputStackIndex);
        ItemStack resultItemStack = CrusherRegistry.createItemsForBlock(Block.getBlockFromItem(inputItemStack.getItem()), inputItemStack.getMetadata());
        if (!outputItemStack.isItemEqual(resultItemStack)) {
            return false;
        }

        return outputItemStack.stackSize + resultItemStack.stackSize <= getInventoryStackLimit();
    }

    private void setIsWorking(boolean value) {
        BlockCrusher.setState(value, worldObj, pos);
    }

    private void consumeFuel() {
        ItemStack fuelItemStack = getStackInSlot(fuelStackIndex);

        ItemStack fuelToLoad = fuelItemStack.splitStack(1);
        int itemBurnTime = TileEntityFurnace.getItemBurnTime(fuelToLoad);
        energy += itemBurnTime;
        energyCapacity = itemBurnTime;

        if (fuelItemStack.stackSize == 0) {
            setInventorySlotContents(fuelStackIndex, null);
        }
    }

    private boolean hasFuel() {
        ItemStack fuelItemStack = getStackInSlot(fuelStackIndex);
        if (fuelItemStack == null || fuelItemStack.stackSize <= 0) {
            return false;
        }
        return TileEntityFurnace.getItemBurnTime(fuelItemStack) > 0;
    }

    private boolean canProcessCurrentItem() {
        ItemStack inputItemStack = getStackInSlot(inputStackIndex);
        if (inputItemStack == null || inputItemStack.stackSize <= 0) {
            return false;
        }

        Item inputItem = inputItemStack.getItem();
        if (!(inputItem instanceof ItemBlock)) {
            return false;
        }

        Block inputBlock = Block.getBlockFromItem(inputItem);
        ItemStack resultItemStack = CrusherRegistry.createItemsForBlock(inputBlock, inputItemStack.getMetadata());
        return resultItemStack != null;
    }

    private void progress() {
        progress++;
    }

    private boolean isFinished() {
        return progress >= NEEDED_PROGRESS;
    }

    public boolean hasEnergy() {
        return energy > 0;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProgress() {
        return progress;
    }

    public int getNeededProgress() {
        return NEEDED_PROGRESS;
    }

    private boolean isProgressing() {
        return progress > 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("Energy", this.energy);
        compound.setInteger("EnergyCapacity", this.energyCapacity);
        compound.setInteger("Progress", this.progress);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.energy = compound.getInteger("Energy");
        this.energyCapacity = compound.getInteger("EnergyCapacity");
        this.progress = compound.getInteger("Progress");
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0: return energy;
            case 1: return energyCapacity;
            case 2: return progress;
            default: return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0: energy = value; break;
            case 1: energyCapacity = value; break;
            case 2: progress = value; break;
        }
    }
}
