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

import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;

public class CREEPSGUIHotdog extends GuiScreen
{
    private CREEPSEntityHotdog hotdog;
    private GuiTextField namescreen;
    private boolean field_28217_m;
    private float xSize_lo;
    private float ySize_lo;

    public CREEPSGUIHotdog(CREEPSEntityHotdog creepentityhotdog)
    {
        hotdog = creepentityhotdog;
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
        namescreen.setText(hotdog.name);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        char c = '\226';
        byte byte0 = 100;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
        int j = (width - c) / 2;
        int k = (height - byte0) / 2;
        drawTexturedModalRect(j, k, 0, 0, (int)xSize_lo, (int)ySize_lo);
        drawEntityOnScreen(j + 51, k + 75, 30, (float)(j + 51) - mouseX, (float)(k + 75 - 50) - mouseY, this.mc.thePlayer);
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
            (new Random()).nextLong();
            String s1 = namescreen.getText();
            hotdog.name = s1;
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 2)
        {
            if (field_28217_m)
            {
                return;
            }

            field_28217_m = true;
            (new Random()).nextLong();
            String s2 = namescreen.getText();
            hotdog.name = s2;
            hotdog.wanderstate = 0;
            hotdog.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 3)
        {
            String s = namescreen.getText();
            hotdog.name = s;
            hotdog.wanderstate = 1;
            hotdog.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 4)
        {
            if (field_28217_m)
            {
                return;
            }

            field_28217_m = true;
            (new Random()).nextLong();
            String s3 = namescreen.getText();
            hotdog.name = s3;
            hotdog.wanderstate = 2;
            hotdog.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 5)
        {
            Minecraft.getMinecraft().displayGuiScreen(new CREEPSGUIHotdogTraining(hotdog));
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
        drawCenteredString(fontRendererObj, "HOTDOG COMMAND CENTER", width / 2, height / 4 - 40, 0xffffff);
        namescreen.drawTextBox();
        fontRendererObj.drawStringWithShadow((new StringBuilder()).append("\2476LEVEL  \247f").append(String.valueOf(hotdog.level)).toString(), (width / 2 - 100) + k, height / 4 + 28 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476NEXT LVL  \247f").append(String.valueOf((int)hotdog.totaldamage)).append("\2473/\247f").append(String.valueOf(CREEPSEntityHotdog.leveldamage[hotdog.level])).toString(), (width / 2 + 2 + k) - 10, height / 4 + 28 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476HEALTH  \247f").append(String.valueOf(hotdog.getHealth())).append("\2473/\247f").append(String.valueOf(hotdog.basehealth)).toString(), (width / 2 - 100) + k, height / 4 + 43 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476EXPERIENCE  \247f").append(String.valueOf(hotdog.totalexperience)).toString(), (width / 2 + 2 + k) - 10, height / 4 + 43 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476ATTACK  \247f").append(String.valueOf(hotdog.attackStrength)).toString(), (width / 2 - 100) + k, height / 4 + 58 + byte0, 0xff8d13);
        drawString(fontRendererObj, (new StringBuilder()).append("\2476SPEED  \247f").append(String.valueOf((int)(hotdog.baseSpeed * 100F) - 50)).toString(), (width / 2 + 2 + k) - 10, height / 4 + 58 + byte0, 0xff8d13);
        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\2473").append(String.valueOf(CREEPSEntityHotdog.levelname[hotdog.level])).toString(), width / 2 + 2 + k, height / 4 + 73 + byte0, 0xff8d13);
        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\2477ATTACK \2476[\247f").append(String.valueOf(hotdog.skillattack)).append("\2476]  \2477DEFENSE \2476[\247f").append(String.valueOf(hotdog.skilldefend)).append("\2476] \2477  HEALING \2476[\247f").append(String.valueOf(hotdog.skillhealing)).append("\2476]  \2477SPEED \2476[\247f").append(String.valueOf(hotdog.skillspeed)).append("\2476]").toString(), width / 2 + 2 + k, height / 4 + 90 + byte0, 0xff8d13);
        super.drawScreen(i, j, f);
        xSize_lo = i;
        ySize_lo = j;
    }
}
