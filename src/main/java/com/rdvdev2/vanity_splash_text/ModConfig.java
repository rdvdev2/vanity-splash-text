package com.rdvdev2.vanity_splash_text;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.values.TrackedValue;

import java.util.List;

public class ModConfig {
	private static final String SKIP_SEASONAL_SPLASH_TEXT = "skip_seasonal_splash_text";
	private static final String IGNORE_MODID_REGEX = "ignore_modid_regex";

	public static void creator(Config.Builder builder) {
		builder
				.field(TrackedValue.create(false, SKIP_SEASONAL_SPLASH_TEXT))
				.field(TrackedValue.create("java|minecraft|quilt_.*|quilted_fabric_.*|.*api.*", IGNORE_MODID_REGEX));
	}

	public static boolean get_skip_seasonal_splash_text() {
		return (Boolean) Mod.CONFIG.getValue(List.of(SKIP_SEASONAL_SPLASH_TEXT)).value();
	}

	public static String get_ignore_modid_regex() {
		return (String) Mod.CONFIG.getValue(List.of(IGNORE_MODID_REGEX)).value();
	}
}
