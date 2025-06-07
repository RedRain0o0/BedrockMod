package io.github.redrain0o0.bedrock.client.screens;

import io.github.redrain0o0.bedrock.Bedrockmod;
import io.github.redrain0o0.bedrock.client.gui.components.OreUIScreenBackButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;

public class OreUIConfirmScreen extends Screen {
    protected Screen lastScreen;
    public Component title;
    public OreUIConfirmScreen(Screen screen, Component component) {
        super(component);
        this.lastScreen = screen;
        this.title = component;
    }

    @Override
    protected void init() {
        this.lastScreen.init(Minecraft.getInstance(), this.width, this.height);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        lastScreen.render(guiGraphics, i, j, f);
        guiGraphics.drawCenteredString(this.font, Component.literal("Test"), this.width / 2, this.height / 2, 16777215);

        //this.minecraft.setScreen(this.lastScreen);
    }

    //public ConfirmationScreen(Screen parent, Function<Screen,Panel> panelConstructor, Component title, MultiLineLabel messageLabel, Consumer<ConfirmationScreen> okAction) {
    //    super(parent, panelConstructor, title);
    //    this.messageLabel = messageLabel;
    //    this.okAction = okAction;
    //    this.parent = parent;
    //}
}
