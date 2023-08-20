package com.moulberry.axiomextensionexample;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AxiomExtensionExample implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("axiom_extension_example");

    @Override
    public void onInitialize() {
        LOGGER.info("Registering StoneTool...");
        ServiceHelper.getToolRegistryService().register(new StoneTool());
    }

}
