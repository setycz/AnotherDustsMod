package com.setycz.AnotherDustsMod.Crusher.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

/**
 * Created by setyc on 07.02.2016.
 */
public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipe> {
    @Nonnull
    @Override
    public Class<CrusherRecipe> getRecipeClass() {
        return CrusherRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return CrusherRecipeCategory.UID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CrusherRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull CrusherRecipe recipe) {
        return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
    }
}
