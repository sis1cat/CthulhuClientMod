package sisicat.cthulhu.mixins;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sisicat.cthulhu.Cthulhu;
import sisicat.cthulhu.utilities.CustomCommandDispatcher;

import java.util.HashMap;
import java.util.Map;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Unique
    private static final String BASE_PROTOCOL = "<-cthulhu-protocol->";
    @Unique
    private static final String VANISHED_PROTOCOL = "<-cthulhu-protocol-vanished-true->";
    @Unique
    private static final String NOT_VANISHED_PROTOCOL = "<-cthulhu-protocol-vanished-false->";
    @Unique
    private static final String TAB_COMPLETER_PROTOCOL = "<-cthulhu-protocol-tab-completer->";


    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    private void onGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {

        String content = packet.content().getString();

        if(content == null) return;

        if(content.contains(TAB_COMPLETER_PROTOCOL)){

            // TAB_COMPLETER_PROTOCOL key/name:sug,sug;name:sug,sug>
            //                        key/name:sug,sug;name:sug,sug

            Map<String, String> commandsMap = new HashMap<>(); // key = command / value = sug

            String toParse = content.replace(TAB_COMPLETER_PROTOCOL + " ", "");

            for(String command : toParse.split(">"))
                commandsMap.put(command.split("/")[0], command.split("/").length == 1 ? "" : command.split("/")[1]);

            CustomCommandDispatcher.commandsMap = commandsMap;

            ci.cancel();
        }

        switch(content) {

            case BASE_PROTOCOL:
                Cthulhu.isCthulhu = true;
                ci.cancel();
                break;

            case VANISHED_PROTOCOL:
                Cthulhu.isVanished = true;
                ci.cancel();
                break;

            case NOT_VANISHED_PROTOCOL:
                Cthulhu.isVanished = false;
                ci.cancel();
                break;

        }

    }
}
