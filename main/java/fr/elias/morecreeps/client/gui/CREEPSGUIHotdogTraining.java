package fr.elias.morecreeps.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntityHotdog;

public class CREEPSGUIHotdogTraining extends GuiScreen
{
    private CREEPSEntityHotdog hotdog;
    private float xSize_lo;
    private float ySize_lo;

    public CREEPSGUIHotdogTraining(CREEPSEntityHotdog creepsentityhotdog)
    {
        hotdog = creepsentityhotdog;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        byte byte0 = -16;
        buttonList.add(new GuiButton(2, width / 2 - 110, height / 4 + 8 + byte0, 98, 20, "\2476<-\247f ATTACK \2476->"));
        buttonList.add(new GuiButton(3, width / 2 + 12, height / 4 + 8 + byte0, 98, 20, "\2476>> \247f DEFENSE \2476<<"));
        buttonList.add(new GuiButton(4, width / 2 - 110, height / 4 + 65 + byte0, 98, 20, "\2476++\247f HEALING \2476++"));
        buttonList.add(new GuiButton(5, width / 2 + 12, height / 4 + 65 + byte0, 98, 20, "\2476((\247f SPEED \2476))"));
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 158 + byte0, 98, 20, "BACK"));
        buttonList.add(new GuiButton(1, width / 2 + 2, height / 4 + 158 + byte0, 98, 20, "DONE"));
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
            Minecraft.getMinecraft().displayGuiScreen(new CREEPSGUIHotdog(hotdog));
        }

        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        World world = Minecraft.getMinecraft().theWorld;

        if (guibutton.id == 2 && hotdog.skillattack < 5)
        {
            if (!checkLevel(hotdog.skillattack))
            {
                return;
            }

            if (checkBones())
            {
                hotdog.skillattack++;

                if (hotdog.skillattack < 4)
                {
                    hotdog.attackStrength += 2;
                }
                else
                {
                    hotdog.attackStrength += 4;
                }

                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdogtrain", 1.0F, 1.0F);
            }
            else
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdognobones", 1.0F, 1.0F);
            }
        }

        if (guibutton.id == 3 && hotdog.skilldefend < 5)
        {
            if (!checkLevel(hotdog.skilldefend))
            {
                return;
            }

            if (checkBones())
            {
                hotdog.skilldefend++;
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdogtrain", 1.0F, 1.0F);
            }
            else
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdognobones", 1.0F, 1.0F);
            }
        }

        if (guibutton.id == 4 && hotdog.skillhealing < 5)
        {
            if (!checkLevel(hotdog.skillhealing))
            {
                return;
            }

            if (checkBones())
            {
                hotdog.skillhealing++;
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdogtrain", 1.0F, 1.0F);
            }
            else
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdognobones", 1.0F, 1.0F);
            }
        }

        if (guibutton.id == 5 && hotdog.skillspeed < 5)
        {
            if (!checkLevel(hotdog.skillspeed))
            {
                return;
            }

            if (checkBones())
            {
                hotdog.skillspeed++;
                hotdog.baseSpeed += 0.05F;
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdogtrain", 1.0F, 1.0F);
            }
            else
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdognobones", 1.0F, 1.0F);
            }
        }
    }

    public boolean checkLevel(int i)
    {
        i *= 5;

        if (hotdog.level < i)
        {
            World world = Minecraft.getMinecraft().theWorld;
            EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;

            if (i == 5)
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdog5level", 1.0F, 1.0F);
            }

            if (i == 10)
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdog10level", 1.0F, 1.0F);
            }

            if (i == 15)
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdog15level", 1.0F, 1.0F);
            }

            if (i == 20)
            {
                world.playSoundAtEntity(entityplayersp, "morecreeps:hotdog20level", 1.0F, 1.0F);
            }

            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean checkBones()
    {
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        ItemStack aitemstack[] = ((EntityPlayer)(entityplayersp)).inventory.mainInventory;
        int i = 0;

        for (int j = 0; j < aitemstack.length; j++)
        {
            ItemStack itemstack = aitemstack[j];

            if (itemstack != null && itemstack.getItem() == Items.bone)
            {
                i += itemstack.stackSize;
            }
        }

        if (i >= 5)
        {
            int k = 5;
            label0:

            for (int i1 = 0; i1 < aitemstack.length; i1++)
            {
                ItemStack itemstack1 = aitemstack[i1];

                if (itemstack1 == null || itemstack1.getItem() != Items.bone)
                {
                    continue;
                }

                do
                {
                    if (itemstack1.stackSize <= 0 || k <= 0)
                    {
                        continue label0;
                    }

                    k--;

                    if (itemstack1.stackSize - 1 == 0)
                    {
                        ((EntityPlayer)(entityplayersp)).inventory.mainInventory[i1] = null;
                        continue label0;
                    }

                    itemstack1.stackSize--;
                }
                while (true);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public String buildStat(int i)
    {
        String s = "";

        for (int j = 0; j < i; j++)
        {
            s = (new StringBuilder()).append(s).append("\2473(*) ").toString();
        }

        if (i < 5)
        {
            for (int k = i; k < 5; k++)
            {
                s = (new StringBuilder()).append(s).append("\2478(*) ").toString();
            }
        }

        return s;
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
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        byte byte0 = -16;
        int k = 0;
        drawWorldBackground(1);
        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\2476").append(String.valueOf(hotdog.name)).append("'s TRAINING").toString(), width / 2, height / 4 - 40, 0xffffff);
        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\247fHOTDOG LEVEL : \2473").append(String.valueOf(hotdog.level)).toString(), width / 2, height / 4 - 25, 0xffffff);
        drawString(fontRendererObj, buildStat(hotdog.skillattack), (width / 2 - 107) + k, height / 4 + 38 + byte0, 0xff8d13);
        drawString(fontRendererObj, buildStat(hotdog.skilldefend), width / 2 + 16 + k, height / 4 + 38 + byte0, 0xff8d13);
        drawString(fontRendererObj, buildStat(hotdog.skillhealing), (width / 2 - 107) + k, height / 4 + 95 + byte0, 0xff8d13);
        drawString(fontRendererObj, buildStat(hotdog.skillspeed), width / 2 + 16 + k, height / 4 + 95 + byte0, 0xff8d13);
        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().thePlayer;
        ItemStack aitemstack[] = ((EntityPlayer)(entityplayersp)).inventory.mainInventory;
        int l = 0;

        for (int i1 = 0; i1 < aitemstack.length; i1++)
        {
            ItemStack itemstack = aitemstack[i1];

            if (itemstack != null && itemstack.getItem() == Items.bone)
            {
                l += itemstack.stackSize;
            }
        }

        drawCenteredString(fontRendererObj, (new StringBuilder()).append("\247fBONES REMAINING: \2473").append(String.valueOf(l)).toString(), width / 2 + 2 + k, height / 4 + 120 + byte0, 0xff8d13);
        drawCenteredString(fontRendererObj, "\2476Each level costs five bones", width / 2 + 2 + k, height / 4 + 140 + byte0, 0xff8d13);
        super.drawScreen(i, j, f);
        xSize_lo = i;
        ySize_lo = j;
    }
}
