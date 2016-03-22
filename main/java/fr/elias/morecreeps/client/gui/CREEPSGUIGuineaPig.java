package fr.elias.morecreeps.client.gui;

import java.io.IOException;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fr.elias.morecreeps.common.entity.CREEPSEntityGuineaPig;

public class CREEPSGUIGuineaPig extends GuiScreen
{
    private CREEPSEntityGuineaPig guineapig;
    private GuiTextField namescreen;
    private boolean field_28217_m;
    private float xSize_lo;
    private float ySize_lo;

    public CREEPSGUIGuineaPig(CREEPSEntityGuineaPig creepsentityguineapig)
    {
        guineapig = creepsentityguineapig;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        namescreen.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        byte byte0 = -16;
        buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 108 + byte0, 98, 20, "\2476XX\247f FIGHT \2476XX"));
        buttonList.add(new GuiButton(3, width / 2 + 2, height / 4 + 108 + byte0, 98, 20, "\2476|| \247f STAY \2476||"));
        buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 128 + byte0, 98, 20, "\2476<<\247f WANDER \2476>>"));
        buttonList.add(new GuiButton(5, width / 2 + 2, height / 4 + 128 + byte0, 98, 20, "\2476-[\247f TRAIN \2476]-"));
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 158 + byte0, 98, 20, "Save"));
        buttonList.add(new GuiButton(1, width / 2 + 2, height / 4 + 158 + byte0, 98, 20, I18n.format("gui.cancel", new Object[0])));
        namescreen = new GuiTextField(6, fontRendererObj, width / 2 - 100, height / 4 - 20, 200, 20);
        namescreen.setMaxStringLength(31);
        namescreen.setCanLoseFocus(true);
        namescreen.setText(guineapig.name);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        char c = '\260';
        char c1 = '\246';
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
        int j = (width - c) / 2;
        int k = (height - c1) / 2;
        drawTexturedModalRect(j, k, 0, 0, (int)xSize_lo, (int)ySize_lo);
        drawEntityOnScreen(j + 51, k + 75, 30, (float)(j + 51) - mouseX, (float)(k + 75 - 50) - mouseY, this.mc.thePlayer);

        /*GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(j + 51, k + 75, 50F);
        float f1 = 30F;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        float f2 = mc.thePlayer.renderYawOffset;
        float f3 = mc.thePlayer.rotationYaw;
        float f4 = mc.thePlayer.rotationPitch;
        float f5 = (float)(j + 51) - xSize_lo;
        float f6 = (float)((k + 75) - 50) - ySize_lo;
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-(float)Math.atan(f6 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
        mc.thePlayer.renderYawOffset = (float)Math.atan(f5 / 40F) * 20F;
        mc.thePlayer.rotationYaw = (float)Math.atan(f5 / 40F) * 40F;
        mc.thePlayer.rotationPitch = -(float)Math.atan(f6 / 40F) * 20F;
        GL11.glTranslatef(0.0F, (float)mc.thePlayer.getYOffset(), 0.0F);
        Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        mc.thePlayer.renderYawOffset = f2;
        mc.thePlayer.rotationYaw = f3;
        mc.thePlayer.rotationPitch = f4;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);*/

    }
    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_147046_0_, (float)p_147046_1_, 50.0F);
        GlStateManager.scale((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = p_147046_5_.renderYawOffset;
        float f3 = p_147046_5_.rotationYaw;
        float f4 = p_147046_5_.rotationPitch;
        float f5 = p_147046_5_.prevRotationYawHead;
        float f6 = p_147046_5_.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        p_147046_5_.renderYawOffset = f2;
        p_147046_5_.rotationYaw = f3;
        p_147046_5_.rotationPitch = f4;
        p_147046_5_.prevRotationYawHead = f5;
        p_147046_5_.rotationYawHead = f6;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled)
        {
            return;
        }

        if (guibutton.id == 1)
        {
            mc.displayGuiScreen(null);
            return;
        }

        if (guibutton.id == 0)
        {
            if (field_28217_m)
            {
                return;
            }

            field_28217_m = true;
            long l = (new Random()).nextLong();
            String s1 = namescreen.getText();
            guineapig.name = s1;
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 2)
        {
            if (field_28217_m)
            {
                return;
            }

            field_28217_m = true;
            long l1 = (new Random()).nextLong();
            String s2 = namescreen.getText();
            guineapig.name = s2;
            guineapig.wanderstate = 0;
            double moveSpeed = guineapig.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            moveSpeed = guineapig.speedboost <= 0 ? guineapig.baseSpeed : guineapig.baseSpeed + 0.5F;
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 3)
        {
            String s = namescreen.getText();
            guineapig.name = s;
            guineapig.wanderstate = 1;
            double moveSpeed = guineapig.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();

            moveSpeed = 0.0F;
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 4)
        {
            if (field_28217_m)
            {
                return;
            }

            field_28217_m = true;
            long l2 = (new Random()).nextLong();
            String s3 = namescreen.getText();
            guineapig.name = s3;
            guineapig.wanderstate = 2;
            double moveSpeed = guineapig.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            moveSpeed = guineapig.speedboost <= 0 ? guineapig.baseSpeed : guineapig.baseSpeed + 0.5F;
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 5)
        {
            Minecraft.getMinecraft().displayGuiScreen(new CREEPSGUIGuineaPigTraining(guineapig));
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        namescreen.textboxKeyTyped(c, i);

        if (i == 1)
        {
            mc.displayGuiScreen(null);
            return;
        }

        if (c == '\r')
        {
            actionPerformed((GuiButton)buttonList.get(0));
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        try {
			super.mouseClicked(i, j, k);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        namescreen.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        byte byte0 = -16;
        int k = 0;
        drawWorldBackground(1);
        drawCenteredString(fontRendererObj, "GUINEA PIG COMMAND CENTER", width / 2, height / 4 - 40, 0xffffff);
        namescreen.drawTextBox();
        drawString(fontRendererObj, (new StringBuilder()).append("\2476LEVEL  \247f").append(String.valueOf(guineapig.level)).toString(), (width / 2 - 100) + k, height / 4 + 28 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476NEXT LVL  \247f").append(String.valueOf((int)guineapig.totaldamage)).append("\2473/\247f").append(String.valueOf(CREEPSEntityGuineaPig.leveldamage[guineapig.level])).toString(), width / 2 + 2 + k, height / 4 + 28 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476HEALTH  \247f").append(String.valueOf(guineapig.getHealth())).append("\2473/\247f").append(String.valueOf(guineapig.basehealth)).toString(), (width / 2 - 100) + k, height / 4 + 43 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476EXPERIENCE  \247f").append(String.valueOf(guineapig.totalexperience)).toString(), width / 2 + 2 + k, height / 4 + 43 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476ATTACK  \247f").append(String.valueOf(guineapig.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).getAttributeValue())).toString(), (width / 2 - 100) + k, height / 4 + 58 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476SPEED  \247f").append(String.valueOf((int)(guineapig.baseSpeed * 100F) - 50)).toString(), width / 2 + 2 + k, height / 4 + 58 + byte0, 0xff8d13);
        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\2473").append(String.valueOf(CREEPSEntityGuineaPig.levelname[guineapig.level])).toString(), width / 2 + 2 + k, height / 4 + 78 + byte0, 0xff8d13);
        super.drawScreen(i, j, f);
        xSize_lo = i;
        ySize_lo = j;
    }
}
