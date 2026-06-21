package io.github.yazilie.fancyname;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.exceptions.AuthenticationException;
import net.kyori.adventure.platform.modcommon.MinecraftClientAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.network.chat.Component;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.yazilie.fancyname.FancyName.LOGGER;
import static io.github.yazilie.fancyname.FancyName.MOD_ID;

public class FancyNameAPI {
    private static final String FANCY_NAME_API = "https://api.fancyname.yazilie.com";

    private static final String CHALLENGE_ENDPOINT = "/challenge";
    private static final Request CHALLENGE_REQUEST = new Request.Builder()
            .url(FANCY_NAME_API + CHALLENGE_ENDPOINT)
            .get()
            .build();

    private static final String NAME_ENDPOINT = "/name";
    private static final String NAME_PARAMETERS = "?username=%s";

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();
    private static final Map<String, Optional<Component>> NAME_CACHE = new ConcurrentHashMap<>();

    private static volatile long API_COOLDOWN_TIME = Long.MIN_VALUE;

    private static Optional<String> fetchChallenge() {
        try(Response response = HTTP_CLIENT.newCall(CHALLENGE_REQUEST).execute()) {
            if(response.code() != 200) throw new IllegalStateException("Response code != 200: " + response);

            JsonObject responseBody = JsonParser.parseString(response.body().string()).getAsJsonObject();
            String serverId = responseBody.get("serverId").getAsString();

            return Optional.of(serverId);
        } catch (Exception e) {
            LOGGER.warn("Failed to fetch challenge", e);
        }

        return Optional.empty();
    }

    private static Optional<Component> fetchName(String username) {
        Request request = new Request.Builder()
                .url(FANCY_NAME_API + NAME_ENDPOINT + NAME_PARAMETERS.formatted(username))
                .get()
                .build();

        try(Response response = HTTP_CLIENT.newCall(request).execute()) {
            if(response.code() == 429) {
                NAME_CACHE.remove(username);
                API_COOLDOWN_TIME = System.currentTimeMillis() + 1_000L;
                throw new IOException("Rate limit exceeded");
            } else if(response.code() != 200) {
                throw new IllegalStateException("Unknown response: " + response);
            }

            JsonObject responseBody = JsonParser.parseString(response.body().string()).getAsJsonObject();
            String fancyname = responseBody.get("fancyname").getAsString();
            Component text = MinecraftClientAudiences.of().asNative(MiniMessage.miniMessage().deserialize(fancyname));

            return Optional.of(text);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static int setName(String fancyname, String serverId) {
        String username = Minecraft.getInstance().getUser().getName();

        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("serverId", serverId);
        requestJson.addProperty("fancyname", fancyname);
        requestJson.addProperty("username", username);

        RequestBody requestBody = RequestBody.create(GSON.toJson(requestJson), MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(FANCY_NAME_API + NAME_ENDPOINT)
                .post(requestBody)
                .build();

        try(Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.code();
        } catch (Exception e) {
            LOGGER.warn("Failed to set name", e);
            return -1;
        }
    }

    public static int setName(String fancyname) {
        if(isOnCooldown()) return 429;

        Optional<String> serverId = fetchChallenge();
        if(serverId.isEmpty()) return -2;

        Minecraft minecraft = Minecraft.getInstance();
        User user = minecraft.getUser();
        try {
            //? if <=1.21.8 {
            /*minecraft.getMinecraftSessionService().joinServer(user.getProfileId(), user.getAccessToken(), serverId.get());
            *///?} else
            minecraft.services().sessionService().joinServer(user.getProfileId(), user.getAccessToken(), serverId.get());
        } catch (AuthenticationException e) {
            LOGGER.warn("Failed to authenticate user with challenge {}", serverId.get(), e);
            return -3;
        }

        return setName(fancyname, serverId.get());
    }

    public static Optional<Component> getName(String username) {
        if(NAME_CACHE.containsKey(username)) return NAME_CACHE.get(username);
        if(isOnCooldown()) return Optional.empty();

        Thread.ofVirtual()
                .name(MOD_ID + "-fetch-" + username)
                .start(() -> NAME_CACHE.put(username, fetchName(username)));

        NAME_CACHE.put(username, Optional.empty());
        return Optional.empty();
    }

    public static void refreshNames() {
        NAME_CACHE.clear();
    }

    private static boolean isOnCooldown() {
        return System.currentTimeMillis() < API_COOLDOWN_TIME;
    }
}
