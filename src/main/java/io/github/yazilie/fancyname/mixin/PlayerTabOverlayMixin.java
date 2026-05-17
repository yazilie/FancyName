package io.github.yazilie.fancyname.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.yazilie.fancyname.FancyName;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.yazilie.fancyname.FancyName.CONFIG;

@Mixin(PlayerTabOverlay.class)
public class PlayerTabOverlayMixin {
    @ModifyReturnValue(method = "getNameForDisplay", at = @At("RETURN"))
    public Component getNameForDisplay(Component original, PlayerInfo info) {
        if(CONFIG.playerList()) return FancyName.applyName(info.getProfile().name(), original);
        else return original;
    }
}
