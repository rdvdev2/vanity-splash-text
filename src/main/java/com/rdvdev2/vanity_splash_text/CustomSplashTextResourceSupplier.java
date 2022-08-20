package com.rdvdev2.vanity_splash_text;

import com.rdvdev2.vanity_splash_text.mixin.SplashTextResourceSupplierAccessor;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.client.util.Session;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.random.RandomGenerator;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CustomSplashTextResourceSupplier extends SplashTextResourceSupplier {
	public CustomSplashTextResourceSupplier(Session session) {
		super(session);
	}

	@Override
	protected List<String> prepare(ResourceManager resourceManager, Profiler profiler) {
		String stitched_pattern = "\\A" + String.join("|", Mod.CONFIG.ignore_modid_regexs.value()) + "\\Z";
		Pattern pattern = Pattern.compile(stitched_pattern);

		Mod.LOGGER.info("Started generating splash messages");
		List<String> templates = Mod.CONFIG.splash_templates.value();
		List<String> splash_texts = QuiltLoader.getAllMods()
				.parallelStream()
				.filter(mod -> !pattern.matcher(mod.metadata().id()).find())
				.flatMap(mod -> templates.parallelStream().map(template -> Pair.of(template, mod.metadata())))
				.map(data -> {
					ModMetadata meta = data.second();
					String splash = data.first()
							.replaceAll("@@MOD@@", meta.name())
							.replaceAll("@@VERSION@@", meta.version().raw());
					Mod.LOGGER.debug("Generated splash message: {}", splash);
					return splash;
				})
				.toList();

		Mod.LOGGER.info("Generated {} splash messages from {} mods", splash_texts.size(), QuiltLoader.getAllMods().size());
		return splash_texts;
	}

	@Nullable
	@Override
	public String get() {
		if (!Mod.CONFIG.skip_seasonal_splash_text.value()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());

			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			if (month == Calendar.DECEMBER && day == 24) {
				return "Merry X-mas!";
			} else if (month == Calendar.JUNE && day == 1) {
				return "Happy new year!";
			} else if (month == Calendar.OCTOBER && day == 31) {
				return "OOoooOOOoooo! Spooky!";
			}
		}

		List<String> splashTexts = ((SplashTextResourceSupplierAccessor) this).getSplashTexts();
		RandomGenerator random = SplashTextResourceSupplierAccessor.getRandom();

		if (splashTexts.isEmpty()) {
			return null;
		} else {
			return splashTexts.get(random.nextInt(splashTexts.size()));
		}
	}
}
