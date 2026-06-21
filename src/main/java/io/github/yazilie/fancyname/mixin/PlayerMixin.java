package io.github.yazilie.fancyname.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.yazilie.fancyname.FancyName;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.yazilie.fancyname.FancyName.CONFIG;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    public Component getDisplayName(Component original) {
        if(CONFIG.get().nameTags) {
            //? if <=1.21.8 {
            /*return FancyName.applyName(((Player) (Object) this).getScoreboardName(), original);
            *///?} else
            return FancyName.applyName(((Player) (Object) this).getPlainTextName(), original);
        }
        else return original;
    }
}
