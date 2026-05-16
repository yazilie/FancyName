package io.github.yazilie.fancyname;

import io.github.yazilie.fancyname.command.FancyNameCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FancyName implements ClientModInitializer {
    public static final String MOD_ID = "fancyname";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        registerCommands();
        registerCacheRefresher();
        LOGGER.info("FancyName by yazilie initialized!");
    }

    private static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, context) -> FancyNameCommand.register(dispatcher));
    }

    private static void registerCacheRefresher() {
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> FancyNameAPI.refreshNames());
    }
}
