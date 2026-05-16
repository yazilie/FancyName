package io.github.yazilie.fancyname.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.yazilie.fancyname.FancyNameAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Player.class)
public class PlayerMixin {
    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    public Component getDisplayName(Component original) {
        Optional<Component> name = FancyNameAPI.getName(((Player) (Object) this).getPlainTextName());
        return name.orElse(original);
    }
}
