package io.github.yazilie.fancyname.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

//? if <=1.21.8 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else
import net.minecraft.resources.Identifier;

import static io.github.yazilie.fancyname.FancyName.MOD_ID;

public class FancyNameConfig {
    public static ConfigClassHandler<FancyNameConfig> HANDLER = ConfigClassHandler.createBuilder(FancyNameConfig.class)
            //? if <=1.21.8 {
            /*.id(ResourceLocation.fromNamespaceAndPath(MOD_ID, "config"))
            *///? } else
            .id(Identifier.fromNamespaceAndPath(MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json"))
                    .build()
            ).build();

    @CustomDescription("FancyName rendering")
    @AutoGen(category = "main")
    @SerialEntry
    @Boolean
    public boolean enabled = true;

    @CustomDescription("Only allow nicknames that keep the original username text, while still allowing custom styles.")
    @AutoGen(category = "main")
    @SerialEntry
    @Boolean
    public boolean onlyStyles = false;

    @CustomDescription("FancyName rendering in the player list")
    @AutoGen(category = "main")
    @TickBox
    @SerialEntry
    @Boolean
    public boolean playerList = true;

    @CustomDescription("FancyName rendering on player name tags")
    @TickBox
    @AutoGen(category = "main")
    @SerialEntry()
    @Boolean
    public boolean nameTags = true;
}
