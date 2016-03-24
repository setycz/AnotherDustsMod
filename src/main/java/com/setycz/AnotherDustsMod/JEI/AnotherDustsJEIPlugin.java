package com.setycz.AnotherDustsMod.JEI;

import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeCategory;
import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeHandler;
import com.setycz.AnotherDustsMod.Crusher.JEI.CrusherRecipeMaker;
import com.setycz.AnotherDustsMod.ModAnotherDusts;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

/**
 * Created by setyc on 07.02.2016.
 */
@JEIPlugin
public class AnotherDustsJEIPlugin implements IModPlugin{
    // inspired by https://github.com/mezz/JustEnoughItems

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
        registry.addRecipeHandlers(new CrusherRecipeHandler());
        registry.addRecipes(CrusherRecipeMaker.getRecipes(jeiHelpers));

        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModAnotherDusts.crusher_on));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
