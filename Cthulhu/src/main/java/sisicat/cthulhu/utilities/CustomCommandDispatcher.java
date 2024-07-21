package sisicat.cthulhu.utilities;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.command.CommandSource;

import net.minecraft.text.Text;
import sisicat.cthulhu.minecraft.Base;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

    // shitcode
public class CustomCommandDispatcher extends CommandDispatcher<CommandSource> implements Base {

    public static Map<String, String> commandsMap;


    public void inputTabCompleter(Map<String, String> commands) {
        commandsMap = commands;

        for (Map.Entry<String, String> mapEntry : commands.entrySet()) {
            LiteralArgumentBuilder<CommandSource> literalArgumentBuilder = LiteralArgumentBuilder.literal(mapEntry.getKey());

            if (!mapEntry.getValue().isEmpty()) {
                processArguments(literalArgumentBuilder, mapEntry.getValue());
            }

            this.register(literalArgumentBuilder);
        }
    }

    private void processArguments(LiteralArgumentBuilder<CommandSource> builder, String args) {
        if (args.contains("|")) {
            String[] subCommands = args.split("\\|");
            for (String subCommand : subCommands) {
                // rm:file:empty|ls:directory:empty|download:url:empty:output:empty
                String[] parts = subCommand.split(":");

                String subCommandName = parts[0];
                String subCommandArgument = parts[1];
                String subCommandSuggestions = parts[2];

                boolean isGreedy = subCommandArgument.contains("%greedy%");
                subCommandArgument = subCommandArgument.replace("%greedy%", "");

                String subCommandArgument2 = null;
                String subCommandSuggestions2 = "empty";

                boolean isGreedy2 = false;

                if(parts.length == 5){
                    subCommandArgument2 = parts[3];
                    subCommandSuggestions2 = parts[4];
                    isGreedy2 = subCommandArgument2.contains("%greedy%");
                    subCommandArgument2 = subCommandArgument2.replace("%greedy%", "");
                }

                LiteralArgumentBuilder<CommandSource> subCommandBuilder = LiteralArgumentBuilder.literal(subCommandName);

                SuggestionProvider<CommandSource> suggestionProvider = getSuggestions(subCommandSuggestions);
                SuggestionProvider<CommandSource> suggestionProvider2 = getSuggestions(subCommandSuggestions2);

                if(subCommandArgument2 != null)
                    subCommandBuilder.then(RequiredArgumentBuilder.<CommandSource, String>argument(subCommandArgument, isGreedy ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider)
                            .then(RequiredArgumentBuilder.<CommandSource, String>argument(subCommandArgument2, isGreedy2 ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider2)));
                else subCommandBuilder.then(RequiredArgumentBuilder.<CommandSource, String>argument(subCommandArgument, isGreedy ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider));

                builder.then(subCommandBuilder);
            }
        } else {// enchantment:e1,e2,e3;level:empty"

            if(!args.contains(";")){

                String argumentName = args.split(":")[0];
                String argumentSuggestions = args.split(":")[1];

                boolean isGreedy = argumentName.contains("%greedy%");
                argumentName = argumentName.replace("%greedy%", "");

                SuggestionProvider<CommandSource> suggestionProvider = getSuggestions(argumentSuggestions);
                builder.then(RequiredArgumentBuilder.<CommandSource, String>argument(argumentName, isGreedy ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider));
            }else if(args.split(";").length == 2) {
                // action:enable,disable;plugin:empty
                String argumentName = args.split(";")[0].split(":")[0];
                String argumentSuggestions = args.split(";")[0].split(":")[1];

                boolean isGreedy = argumentName.contains("%greedy%");
                argumentName = argumentName.replace("%greedy%", "");

                String argumentName2 = args.split(";")[1].split(":")[0];
                String argumentSuggestions2 = args.split(";")[1].split(":")[1];

                boolean isGreedy2 = argumentName2.contains("%greedy%");
                argumentName2 = argumentName2.replace("%greedy%", "");

                SuggestionProvider<CommandSource> suggestionProvider = getSuggestions(argumentSuggestions);
                SuggestionProvider<CommandSource> suggestionProvider2 = getSuggestions(argumentSuggestions2);

                builder.then(RequiredArgumentBuilder.<CommandSource, String>argument(argumentName, isGreedy ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider)
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument(argumentName2, isGreedy2 ? StringArgumentType.greedyString() : StringArgumentType.string()).suggests(suggestionProvider2)));

            }

        }
    }

    private SuggestionProvider<CommandSource> getSuggestions(String suggestions) {
        switch (suggestions) {
            case "players":
                return PLAYERS_SUGGESTIONS;
            case "empty":
                return (context, builder) -> builder.buildFuture();
            default:
                return (context, builder) -> {
                    String[] suggestionArray = suggestions.split(",");
                    for (String suggestion : suggestionArray) {
                        builder.suggest(suggestion);
                    }
                    return builder.buildFuture();
                };
        }
    }
    private static final SuggestionProvider<CommandSource> PLAYERS_SUGGESTIONS = (context, builder) -> {
        String remaining = builder.getRemaining().toLowerCase();

        List<String> suggestions = mc.world.getPlayers().stream()
                .map(player -> player.getName().getString())
                .toList();

        suggestions.stream()
                .filter(s -> s.toLowerCase().startsWith(remaining))
                .forEach(builder::suggest);

        return builder.buildFuture();
    };


}
