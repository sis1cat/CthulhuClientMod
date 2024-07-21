package sisicat.cthulhu;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import sisicat.cthulhu.event.Events;
import sisicat.cthulhu.minecraft.Base;
import sisicat.cthulhu.payload.Payloads;
import sisicat.cthulhu.utilities.CustomCommandDispatcher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cthulhu implements ModInitializer, Base {

    public static boolean isCthulhu = false;
    public static boolean isVanished = false;

    public static CustomCommandDispatcher commandDispatcher = new CustomCommandDispatcher();

    public static String commandPrefix = "=";

    @Override
    public void onInitialize() {

        Events.initialize();
        Payloads.initialize();

    }

    public static void updateCommandDispatcher(){

        commandDispatcher = new CustomCommandDispatcher();
        commandDispatcher.inputTabCompleter(CustomCommandDispatcher.commandsMap);

    }

}