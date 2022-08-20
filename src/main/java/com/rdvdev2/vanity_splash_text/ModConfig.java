package com.rdvdev2.vanity_splash_text;

import org.quiltmc.config.api.Config;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.config.api.values.ValueList;
import org.quiltmc.loader.api.config.QuiltConfig;

public class ModConfig {
	private static final String SKIP_SEASONAL_SPLASH_TEXT = "skip_seasonal_splash_text";
	private static final String IGNORE_MODID_REGEXS = "ignore_modid_regexs";
	private static final String SPLASH_TEMPLATES = "splash_templates";
	private static final String THE_ANSWER = "the_answer";

	public final TrackedValue<Boolean> skip_seasonal_splash_text = TrackedValue.create(false, SKIP_SEASONAL_SPLASH_TEXT);
	public final TrackedValue<ValueList<String>> ignore_modid_regexs = TrackedValue.create(ValueList.create("",
			"java",
			"minecraft",
			"quilt_.*",
			"quilted_fabric_.*",
			".*api.*"
		), IGNORE_MODID_REGEXS);
	public final TrackedValue<ValueList<String>> splash_templates = TrackedValue.create(ValueList.create("",
			"Now featuring @@MOD@@!",
			"With @@MOD@@ version @@VERSION@@!",
			"@@MOD@@???? Cool!",
			"Can't play without @@MOD@@!",
			"Who's at version @@VERSION@@? That's right, @@MOD@@!",
			"Did you ever play before with @@MOD@@?",
			"@@MOD@@ is @@MOD@@",
			"If you liked @@MOD@@, it's authors will love to hear about it!"
		), SPLASH_TEMPLATES);
	public final TrackedValue<Integer> the_answer = TrackedValue.create(0, THE_ANSWER);

	public ModConfig() {
		QuiltConfig.create("vanity_splash_text", "config", this::creator);
	}

	private void creator(Config.Builder builder) {
		builder
				.field(skip_seasonal_splash_text)
				.field(ignore_modid_regexs)
				.field(splash_templates)
				.field(the_answer);
	}
}
