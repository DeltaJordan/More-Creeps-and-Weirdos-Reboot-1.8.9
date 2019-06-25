package fr.elias.morecreeps.client.gui;

import java.io.IOException;
import java.util.Random;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import fr.elias.morecreeps.common.entity.CREEPSEntitySnowDevil;

public class CREEPSGUISnowdevilname extends GuiScreen
{
    private CREEPSEntitySnowDevil snowdevil;
    private GuiTextField namescreen;
    private boolean field_28217_m;
    protected int xSize;
    protected int ySize;

    public CREEPSGUISnowdevilname(CREEPSEntitySnowDevil creepsentitysnowdevil)
    {
        snowdevil = creepsentitysnowdevil;
        xSize = 256;
        ySize = 180;
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
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 52 + 12, I18n.format("gui.cancel", new Object[0])));
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 32, "Save"));
        namescreen = new GuiTextField(2, fontRendererObj, width / 2 - 100, height / 4, 200, 20);
        namescreen.setMaxStringLength(31);
        namescreen.setCanLoseFocus(true);
        namescreen.setText(snowdevil.name);
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
            String s = namescreen.getText();
            snowdevil.name = s;
            mc.displayGuiScreen(null);
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        namescreen.textboxKeyTyped(c, i);

        if (c == '\r')
        {
            actionPerformed((GuiButton)buttonList.get(0));
        }

        if (i == 1)
        {
            mc.displayGuiScreen(null);
            return;
        }
        else
        {
            return;
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int i, int j, int k)
    {
        try {
			super.mouseClicked(i, j, k);
		} catch (IOException e) {
			e.printStackTrace();
		}
        namescreen.mouseClicked(i, j, k);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(new ResourceLocation("morecreeps:textures/gui/gui-screen.png"));
        int l = (width - xSize) / 2;
        int i1 = (height - (ySize + 16)) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        drawCenteredString(fontRendererObj, "NAME YOUR SNOW DEVIL", width / 2, (height / 4 - 40) + 20, 0xffffff);
        namescreen.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
