package com.mobileboost.mixin;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * FAST CHUNK — maximizes chunk builder threads.
 * Vanilla: cores/2. Ultra: cores-1.
 * Helio G85 (8 cores): 4 → 7 threads.
 */
@Mixin(ChunkBuilder.class)
public class MixinFastChunk {

    @ModifyArg(
        method = "<init>",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/concurrent/Executors;newFixedThreadPool(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;"
        ),
        index = 0
    )
    private static int boostChunkThreads(int original) {
        int cores = Runtime.getRuntime().availableProcessors();
        int boosted = Math.max(1, cores - 1);
        System.out.println("[MobileBoost] ChunkBuilder threads: " + original + " → " + boosted);
        return boosted;
    }
}
