package com.rdvdev2.vanity_splash_text.mixin;

import com.rdvdev2.vanity_splash_text.CustomSplashTextResourceSupplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Redirect(
			method = "<init>",
			at = @At(value = "NEW", target = "net/minecraft/client/resource/SplashTextResourceSupplier")
	)
	public SplashTextResourceSupplier injectCustomSplashText(Session session) {
		return new CustomSplashTextResourceSupplier(session);
	}
}
