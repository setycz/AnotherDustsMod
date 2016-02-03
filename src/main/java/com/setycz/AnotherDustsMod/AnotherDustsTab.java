package com.setycz.AnotherDustsMod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by setyc on 29.01.2016.
 */
public class AnotherDustsTab extends CreativeTabs {

    public AnotherDustsTab() {
        super("another_dusts");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(ModAnotherDusts.crusher);
    }
}
