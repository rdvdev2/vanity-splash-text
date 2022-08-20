package com.rdvdev2.vanity_splash_text;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.QuiltConfig;

public class ModConfig {
	private static final String SKIP_SEASONAL_SPLASH_TEXT = "skip_seasonal_splash_text";
	private static final String IGNORE_MODID_REGEX = "ignore_modid_regex";

	public final TrackedValue<Boolean> skip_seasonal_splash_text = TrackedValue.create(false, SKIP_SEASONAL_SPLASH_TEXT);
	public final TrackedValue<String> ignore_modid_regex = TrackedValue.create("java|minecraft|quilt_.*|quilted_fabric_.*|.*api.*", IGNORE_MODID_REGEX);

	public ModConfig() {
		QuiltConfig.create("vanity_splash_text", "config", this::creator);
	}

	private void creator(Config.Builder builder) {
		builder
				.field(skip_seasonal_splash_text)
				.field(ignore_modid_regex);
	}
}
