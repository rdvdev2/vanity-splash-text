package com.rdvdev2.vanity_splash_text;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Vanity Splash Text");
	public static ModConfig CONFIG = null;

	@Override
	public void onInitialize(ModContainer mod) {
		CONFIG = new ModConfig();
	}
}
