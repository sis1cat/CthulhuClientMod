package sisicat.cthulhu.mixins;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sisicat.cthulhu.Cthulhu;
import sisicat.cthulhu.minecraft.Base;

import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class MixinChatInputSuggester implements Base {

    @Shadow
    private CompletableFuture<Suggestions> pendingSuggestions;

    @Shadow
    private ChatInputSuggestor.SuggestionWindow window;

    @Shadow
    private ParseResults<CommandSource> parse;

    @Shadow
    boolean completingSuggestions;

    @Shadow
    @Final
    TextFieldWidget textField;

    @Shadow
    protected abstract void showCommandSuggestions();
    // ethanol
    @Inject(method = "refresh", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public final void suggestClientCommands(final CallbackInfo info, final String string, final StringReader reader) {

        if(!Cthulhu.isCthulhu || Cthulhu.commandDispatcher == null) return;

        int length = Cthulhu.commandPrefix.length();

        if (reader.canRead(length) && reader.getString().startsWith(Cthulhu.commandPrefix, reader.getCursor()) && this.mc.currentScreen instanceof ChatScreen) {

            reader.setCursor(reader.getCursor() + length);

            if (parse == null)
                parse = Cthulhu.commandDispatcher.parse(reader, mc.player.getCommandSource());

            if (textField.getCursor() >= length && (window == null || !completingSuggestions)) {

                pendingSuggestions = Cthulhu.commandDispatcher.getCompletionSuggestions(parse, textField.getCursor());
                pendingSuggestions.thenRun(() -> {
                    if (pendingSuggestions.isDone()) showCommandSuggestions();
                });

            }

            info.cancel();

        }
    }

}
