package io.github.yazilie.fancyname.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.yazilie.fancyname.FancyName;
import io.github.yazilie.fancyname.FancyNameAPI;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import okhttp3.*;

import static io.github.yazilie.fancyname.FancyName.MOD_ID;

//? if >=26.1 {
/*import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal;
*///?} else {
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;
//?}



public class FancyNameCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("fancy")
                .then(literal("set").then(argument("fancy", StringArgumentType.greedyString()).executes(FancyNameCommand::executeSet)))
                .then(literal("refresh").executes(FancyNameCommand::executeRefresh))
                .then(literal("on").executes(context -> executeToggle(context, true)))
                .then(literal("off").executes(context -> executeToggle(context, false)))
        );
    }

    private static int executeSet(CommandContext<FabricClientCommandSource> context) {
        String fancyname = StringArgumentType.getString(context, "fancy");
        Component text = MinecraftClientAudiences.of().asNative(MiniMessage.miniMessage().deserialize(fancyname));

        Thread.ofVirtual()
                .name(MOD_ID + "-command-fancy-set")
                .start(() -> {
                    int response = FancyNameAPI.setName(fancyname);

                    MutableComponent feedback = switch (response) {
                        case 200 -> Component.translatable("command.fancyname.set", text);
                        case 429 -> Component.translatable("exception.fancyname.ratelimit");
                        case -2, -3, 404 -> Component.translatable("exception.fancyname.authentication");
                        case -1 -> Component.translatable("exception.fancyname.set");
                        default -> Component.translatable("exception.fancyname.api");
                    };

                    if(response != 200) feedback.withStyle(ChatFormatting.RED);

                    Minecraft.getInstance().execute(() -> context.getSource().sendFeedback(feedback));

                    FancyNameAPI.refreshNames();
                });

        return Command.SINGLE_SUCCESS;
    }

    private static int executeRefresh(CommandContext<FabricClientCommandSource> context) {
        FancyNameAPI.refreshNames();
        context.getSource().sendFeedback(Component.translatable("command.fancyname.refresh"));
        return Command.SINGLE_SUCCESS;
    }

    private static int executeToggle(CommandContext<FabricClientCommandSource> context, boolean enabled) {
        FancyName.CONFIG.enabled(enabled);
        if(enabled) context.getSource().sendFeedback(Component.translatable("command.fancyname.enabled"));
        else context.getSource().sendFeedback(Component.translatable("command.fancyname.disabled"));
        return Command.SINGLE_SUCCESS;
    }
}
