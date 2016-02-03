package com.setycz.AnotherDustsMod.Crusher;

import com.setycz.AnotherDustsMod.ModAnotherDusts;
import com.setycz.AnotherDustsMod.InventoryBlock.BlockInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by setyc on 27.01.2016.
 */
public class BlockCrusher extends BlockInventory {
    private static boolean keepInventory;

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityCrusher();
    }

    public static void setState(boolean isOn, World worldObj, BlockPos pos) {
        IBlockState iblockstate = worldObj.getBlockState(pos);
        TileEntity tileentity = worldObj.getTileEntity(pos);
        keepInventory = true;
        if (isOn) {
            worldObj.setBlockState(pos, ModAnotherDusts.crusher_on.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldObj.setBlockState(pos, ModAnotherDusts.crusher_on.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else {
            worldObj.setBlockState(pos, ModAnotherDusts.crusher.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldObj.setBlockState(pos, ModAnotherDusts.crusher.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldObj.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // we have to preserve inventory if the block is turned on/off
        super.breakBlock(worldIn, pos, state, keepInventory);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // always drop crusher in off state
        return Item.getItemFromBlock(ModAnotherDusts.crusher);
    }
}
