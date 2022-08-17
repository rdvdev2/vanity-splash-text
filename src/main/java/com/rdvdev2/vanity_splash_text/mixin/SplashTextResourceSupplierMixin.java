package com.rdvdev2.vanity_splash_text.mixin;

import com.rdvdev2.vanity_splash_text.Mod;
import com.rdvdev2.vanity_splash_text.ModConfig;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.regex.Pattern;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {

	@Shadow
	@Final
	private List<String> splashTexts;
	@Shadow
	@Final
	private static RandomGenerator RANDOM;

	/**
	 * Replace the generated list of possible splash texts
	 * @author rdvdev2 (me@rdvdev2.com)
	 * @reason It's more efficient to not parse the original list of splash texts, as it won't be used
	 */
	@Overwrite()
	public List<String> prepare(ResourceManager resourceManager, Profiler profiler) {
		Pattern pattern = Pattern.compile("\\A" + ModConfig.get_ignore_modid_regex() + "\\Z");

		Mod.LOGGER.info("Started generating splash messages");
		List<String> splash_texts = QuiltLoader.getAllMods()
				.parallelStream()
				.filter(mod -> !pattern.matcher(mod.metadata().id()).find())
				.map(mod -> mod.metadata().name())
				.map(mod_name -> String.format("Now featuring %s!", mod_name))
				.toList();

		Mod.LOGGER.info("Generated {} splash messages from {} mods", splash_texts.size(), QuiltLoader.getAllMods().size());
		return splash_texts;
	}

	@Inject(
			method = "get",
			at = @At("HEAD"),
			cancellable = true,
			require = 1
	)
	private void skip_seasonal_splash_text(CallbackInfoReturnable<String> cir) {
		if (ModConfig.get_skip_seasonal_splash_text()) get_custom_splash_text(cir);
	}

	@Inject(
			method = "get",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/client/resource/SplashTextResourceSupplier;session:Lnet/minecraft/client/util/Session;",
					ordinal = 0),
			cancellable = true,
			require = 1)
	private void get_custom_splash_text(CallbackInfoReturnable<String> cir) {
		if (this.splashTexts.isEmpty()) {
			Mod.LOGGER.warn("No splash texts were generated!");
			cir.setReturnValue(null);
		} else {
			String ret = this.splashTexts.get(RANDOM.nextInt(this.splashTexts.size()));
			cir.setReturnValue(ret);
		}
	}
}
