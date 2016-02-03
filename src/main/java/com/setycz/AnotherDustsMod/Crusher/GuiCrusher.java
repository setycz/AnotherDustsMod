package com.setycz.AnotherDustsMod.Crusher;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by setyc on 28.01.2016.
 */
@SideOnly(Side.CLIENT)
public class GuiCrusher extends GuiContainer {
    private static final ResourceLocation crusherTexture = new ResourceLocation("anotherdusts:textures/gui/crusher.png");
    private final InventoryPlayer playerInventory;
    private final TileEntityCrusher crusherInventory;

    public GuiCrusher(InventoryPlayer playerInv, TileEntityCrusher crusherInv) {
        super(new ContainerCrusher(playerInv, crusherInv));
        this.playerInventory = playerInv;
        this.crusherInventory = crusherInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.crusherInventory.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(crusherTexture);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int k = this.getBurnLeftScaled(13);
        this.drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }


    private int getCookProgressScaled(int pixels)
    {
        int i = crusherInventory.getProgress();
        int j = crusherInventory.getNeededProgress();
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = crusherInventory.getEnergyCapacity();

        if (i == 0)
        {
            i = 200;
        }

        return crusherInventory.getEnergy() * pixels / i;
    }
}
