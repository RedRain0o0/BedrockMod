package io.github.redrain0o0.bedrock;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bedrockmod implements ModInitializer {
    public static final String MODID = "bedrock";
    public static final Logger LOGGER = LoggerFactory.getLogger("Bedrock");
    @Override
    public void onInitialize() {
        LOGGER.info("Common initialized");
    }

    public static ResourceLocation createId(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }
}
