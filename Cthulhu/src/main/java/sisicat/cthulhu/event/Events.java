package sisicat.cthulhu.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import sisicat.cthulhu.Cthulhu;
import sisicat.cthulhu.event.events.EventChatMessages;
import sisicat.cthulhu.event.events.EventHudRender;
import sisicat.cthulhu.minecraft.Base;
import sisicat.cthulhu.payload.payloads.CthulhuCheckInstalledPayload;
import sisicat.cthulhu.utilities.CustomCommandDispatcher;

public class Events implements Base {

    public static boolean sent = false;

    public static void initialize() {

        ClientTickEvents.START_WORLD_TICK.register(clientWorld -> {

            if(mc.getNetworkHandler() != null && !sent) {
                mc.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new CthulhuCheckInstalledPayload()));
                sent = true;
            }

        });

        ClientPlayConnectionEvents.DISCONNECT.register((clientPlayNetworkHandler, minecraftClient) -> {

            sent = false;
            Cthulhu.isCthulhu = false;
            Cthulhu.isVanished = false;

        });

        ClientSendMessageEvents.ALLOW_CHAT.register(new EventChatMessages());

        HudRenderCallback.EVENT.register(new EventHudRender());


    }
}
