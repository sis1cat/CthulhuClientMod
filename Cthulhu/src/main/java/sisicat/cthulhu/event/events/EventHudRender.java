package sisicat.cthulhu.event.events;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import sisicat.cthulhu.Cthulhu;
import sisicat.cthulhu.minecraft.Base;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class EventHudRender implements HudRenderCallback, Base {

    @Override
    public final void onHudRender(final DrawContext drawContext, final RenderTickCounter tickCounter) {
        if (Cthulhu.isCthulhu) {
            final TextRenderer textRenderer = this.mc.textRenderer;

                drawContext.drawText(
                        textRenderer,
                        Text.of("Cthulhu detected"),
                        20,
                        20,
                        0x51a7da,
                        true
                );

            if (Cthulhu.isVanished)

                drawContext.drawText(
                        textRenderer,
                        Text.of("Vanished"),
                        20,
                        30,
                        0x5874ff,
                        true
                );


        }

    }

}
