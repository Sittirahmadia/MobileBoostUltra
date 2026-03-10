package com.mobileboost.mixin;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * REBUILD THROTTLE — throttle non-important chunk rebuilds every 2 ticks.
 * Prevents the rebuild queue from flooding the GPU on large block events.
 * Player-caused (important) rebuilds always pass through immediately.
 */
@Mixin(ChunkBuilder.class)
public class MixinReduceBlockUpdates {

    private static int rebuildCounter = 0;

    @Inject(
        method = "scheduleRebuild",
        at = @At("HEAD"),
        cancellable = true
    )
    private void throttleRebuilds(int x, int y, int z, boolean important, CallbackInfo ci) {
        if (important) return;
        if (++rebuildCounter % 2 != 0) {
            ci.cancel();
        }
    }
}
