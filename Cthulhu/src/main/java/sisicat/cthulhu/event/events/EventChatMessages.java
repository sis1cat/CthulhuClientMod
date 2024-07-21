package sisicat.cthulhu.event.events;

import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import sisicat.cthulhu.Cthulhu;
import sisicat.cthulhu.minecraft.Base;
import sisicat.cthulhu.payload.payloads.CthulhuCheckInstalledPayload;
import sisicat.cthulhu.payload.payloads.CthulhuCommandPayload;

public class EventChatMessages implements ClientSendMessageEvents.AllowChat, Base {

    @Override
    public boolean allowSendChatMessage(String message) {

        if(message.startsWith("=") && Cthulhu.isCthulhu) {
            mc.getNetworkHandler().sendPacket(new CustomPayloadC2SPacket(new CthulhuCommandPayload(mc.getGameProfile().getName() + message)));
            return false;
        }

        return true;

    }
}
