package io.github.redrain0o0.bedrock.client.screens;

import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.gui.components.FlatOreUIButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class OreUIConfirmExitScreen extends OreUIConfirmScreen {
    private ResourceLocation panel = Bedrockmod.createId("tiles/panel");
    private ResourceLocation square_entity_panel = Bedrockmod.createId("tiles/square_entity_panel");
    public OreUIConfirmExitScreen(Screen screen, Component component) {
        super(screen, component);
        this.lastScreen = screen;
        this.title = component;
    }

    @Override
    protected void init() {
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("gui.ok").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.stop();
        }).bounds(this.width / 2 - 100, this.height / 2 + 17, 200, 31).build());
        this.addRenderableWidget(FlatOreUIButton.builder(Component.translatable("gui.cancel").withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.setScreen(this.lastScreen);
        }).bounds(this.width / 2 - 100, this.height / 2 + 50, 200, 31).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        lastScreen.render(guiGraphics, i, j, f);
        guiGraphics.blitSprite(RenderType::guiTextured, this.panel, this.width / 2 - 107, this.height / 2 - 87, 214, 175);
        guiGraphics.blitSprite(RenderType::guiTextured, this.square_entity_panel, this.width / 2 - 100, this.height / 2 - 65, 200, 78);


        //this.minecraft.setScreen(this.lastScreen);
    }
    // 214, 175
    // 200, 78
    // 200, 31
}
