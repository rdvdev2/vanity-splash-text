package com.rdvdev2.vanity_splash_text.mixin;

import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.util.random.RandomGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public interface SplashTextResourceSupplierAccessor {

	@Accessor
	List<String> getSplashTexts();

	@Accessor("RANDOM")
	static RandomGenerator getRandom() {
		throw new AssertionError();
	}
}
