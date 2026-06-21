package io.github.yazilie.fancyname;

import io.github.yazilie.fancyname.command.FancyNameCommand;
import io.github.yazilie.fancyname.config.FancyNameConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

//? if >=26.1 {
/*import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLevelEvents;
*///?} else
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;

import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

public class FancyName implements ClientModInitializer {
    public static final String MOD_ID = "fancyname";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Supplier<FancyNameConfig> CONFIG = () -> FancyNameConfig.HANDLER.instance();

    @Override
    public void onInitializeClient() {
        FancyNameConfig.HANDLER.load();

        registerCommands();
        registerCacheRefresher();
        LOGGER.info("FancyName by yazilie initialized!");
    }

    private static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> FancyNameCommand.register(dispatcher));
    }

    private static void registerCacheRefresher() {
        //? if >=26.1 {
        /*ClientLevelEvents.AFTER_CLIENT_LEVEL_CHANGE.register((_, _) -> {
        *///?} else
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> {
            FancyNameAPI.refreshNames();
            TextEditor.cleanCache();
        });
    }

    public static Component applyName(String username, Component original) {
        if(!CONFIG.get().enabled) return original;

        Optional<Component> fancyName = FancyNameAPI.getName(username);
        if(fancyName.isEmpty()) return original;
        if(CONFIG.get().onlyStyles && !fancyName.get().getString().equals(username)) return original;
        return TextEditor.modifyText(original, username, fancyName.get());
    }
}
