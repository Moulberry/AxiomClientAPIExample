package com.moulberry.axiomextensionexample;

import com.mojang.blaze3d.vertex.PoseStack;
import com.moulberry.axiomclientapi.CustomTool;
import com.moulberry.axiomclientapi.Effects;
import com.moulberry.axiomclientapi.pathers.BallShape;
import com.moulberry.axiomclientapi.pathers.ToolPatherUnique;
import com.moulberry.axiomclientapi.regions.BlockRegion;
import com.moulberry.axiomclientapi.regions.BooleanRegion;
import com.moulberry.axiomclientapi.service.ToolService;
import imgui.ImGui;
import net.minecraft.client.Camera;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

public class StoneTool implements CustomTool {

    private final BlockRegion blockRegion = ServiceHelper.createBlockRegion();
    private final BooleanRegion previewRegion = ServiceHelper.createBooleanRegion();
    private ToolPatherUnique pather = null;
    private boolean usingTool = false;

    private final int[] radius = new int[]{10};
    private int oldRadius = -1;

    @Override
    public void reset() {
        this.blockRegion.clear();
        this.pather = null;
        this.usingTool = false;
    }

    @Override
    public String name() {
        return "My Custom Tool";
    }

    @Override
    public boolean callUseTool() {
        this.reset();
        this.pather = ServiceHelper.createToolPatherUnique(this.radius[0], BallShape.SPHERE);
        this.usingTool = true;
        return true;
    }

    @Override
    public void render(Camera camera, float tickDelta, long time, PoseStack poseStack, Matrix4f projection) {
        ToolService toolService = ServiceHelper.getToolService();

        if (!this.usingTool) {
            BlockHitResult hitResult = toolService.raycastBlock();
            if (hitResult == null) {
                return;
            }

            int radius = this.radius[0];
            if (this.oldRadius != radius) {
                this.oldRadius = radius;
                this.previewRegion.clear();
                BallShape.SPHERE.fillRegion(this.previewRegion, radius);
            }

            this.previewRegion.render(camera, Vec3.atLowerCornerOf(hitResult.getBlockPos()), poseStack,
                projection, time, Effects.BLUE);
        } else if (!ServiceHelper.getToolService().isMouseDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {
            toolService.pushBlockRegionChange(this.blockRegion);
            this.reset();
        } else {
            BlockState block = toolService.getActiveBlock();
            this.pather.update((x, y, z) -> {
                this.blockRegion.addBlock(x, y, z, block);
            });

            float opacity = (float) Math.sin(time / 1000000f / 50f / 8f);
            this.blockRegion.render(camera, Vec3.ZERO, poseStack, projection,
                0.75f + opacity*0.25f, 0.3f - opacity*0.2f);
        }
    }

    @Override
    public void displayImguiOptions() {
        ImGui.sliderInt("Radius", this.radius, 0, 32);
    }
}
