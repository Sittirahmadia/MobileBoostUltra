package com.mobileboost.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.MinecraftClient;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * SMART SKY — skip sky render when underground (Y < 60).
 * 1.21.11: renderSky signature uses Matrix4f x2 + float + Runnable.
 */
@Mixin(WorldRenderer.class)
public class MixinSmartSky {

    @Inject(
        method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLjava/lang/Runnable;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void skipSkyUnderground(Matrix4f viewMatrix, Matrix4f projectionMatrix,
                                     float tickDelta, Runnable fogCallback,
                                     CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.getY() < 60.0) {
            ci.cancel();
        }
    }
}
