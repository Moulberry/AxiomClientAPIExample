package com.moulberry.axiomextensionexample;

import com.moulberry.axiomclientapi.pathers.BallShape;
import com.moulberry.axiomclientapi.pathers.ToolPatherUnique;
import com.moulberry.axiomclientapi.regions.BlockRegion;
import com.moulberry.axiomclientapi.regions.BooleanRegion;
import com.moulberry.axiomclientapi.service.RegionProvider;
import com.moulberry.axiomclientapi.service.ToolPatherProvider;
import com.moulberry.axiomclientapi.service.ToolRegistryService;
import com.moulberry.axiomclientapi.service.ToolService;

import java.util.Optional;
import java.util.ServiceLoader;

public class ServiceHelper {

    private static final RegionProvider regionProvider;
    private static final ToolPatherProvider toolPatherProvider;
    private static final ToolService toolService;
    private static final ToolRegistryService toolRegistryService;

    static {
        Optional<RegionProvider> regionProviderOpt = ServiceLoader.load(RegionProvider.class).findFirst();
        if (regionProviderOpt.isEmpty()) throw new Error("Missing RegionProvider! Is Axiom loaded?");

        Optional<ToolPatherProvider> toolPatherProviderOpt = ServiceLoader.load(ToolPatherProvider.class).findFirst();
        if (toolPatherProviderOpt.isEmpty()) throw new Error("Missing ToolPatherProvider! Is Axiom loaded?");

        Optional<ToolService> toolServiceOpt = ServiceLoader.load(ToolService.class).findFirst();
        if (toolServiceOpt.isEmpty()) throw new Error("Missing ToolService! Is Axiom loaded?");

        Optional<ToolRegistryService> toolRegistryServiceOpt = ServiceLoader.load(ToolRegistryService.class).findFirst();
        if (toolRegistryServiceOpt.isEmpty()) throw new Error("Missing ToolRegistryService! Is Axiom loaded?");

        regionProvider = regionProviderOpt.get();
        toolPatherProvider = toolPatherProviderOpt.get();
        toolService = toolServiceOpt.get();
        toolRegistryService = toolRegistryServiceOpt.get();
    }

    public static BlockRegion createBlockRegion() {
        return regionProvider.createBlock();
    }

    public static BooleanRegion createBooleanRegion() {
        return regionProvider.createBoolean();
    }

    public static ToolPatherUnique createToolPatherUnique(int radius, BallShape ballShapeType) {
        return toolPatherProvider.createUnique(radius, ballShapeType);
    }

    public static ToolService getToolService() {
        return toolService;
    }

    public static ToolRegistryService getToolRegistryService() {
        return toolRegistryService;
    }

}
