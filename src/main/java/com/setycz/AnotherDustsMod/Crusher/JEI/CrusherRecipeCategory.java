package com.setycz.AnotherDustsMod.Crusher.JEI;

import com.setycz.AnotherDustsMod.ModAnotherDusts;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by setyc on 07.02.2016.
 */
public class CrusherRecipeCategory implements IRecipeCategory {
    public static final String UID = "anotherdusts.crusher";

    private static final int inputSlot = 0;
    private static final int outputSlot = 2;

    private final String localizedName;
    private final IDrawableStatic background;
    private final IDrawableAnimated flame;
    private final IDrawableAnimated arrow;

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        localizedName = Translator.translateToLocal("gui.crusher.name");

        ResourceLocation location = new ResourceLocation(ModAnotherDusts.MODID, "textures/gui/crusher.png");
        background = guiHelper.createDrawable(location, 55, 16, 82, 54);

        IDrawableStatic flameDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 176, 14, 24, 17);
        arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Nonnull
    @Override
    public String getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        flame.draw(minecraft, 2, 20);
        arrow.draw(minecraft, 24, 18);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 0, 0);
        guiItemStacks.init(outputSlot, false, 60, 18);

        guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
    }
}
