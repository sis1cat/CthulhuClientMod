package sisicat.cthulhu.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sisicat.cthulhu.Cthulhu;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void onSetScreen(Screen screen, CallbackInfo ci) {

        if (screen instanceof ChatScreen && Cthulhu.isCthulhu)
            Cthulhu.updateCommandDispatcher();

    }

}