package io.github.redrain0o0.bedrock.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class JsonUIMarketScreen extends JsonUIScreen {

    public JsonUIMarketScreen(Screen screen, Component component) {
        super(screen, component);
        this.lastScreen = screen;
        this.title = Component.translatable("bedrock.menu.marketplace").withStyle(Style.EMPTY.withShadowColor(0));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.renderSideBar(guiGraphics, i, j, f);
        super.renderWidgets(guiGraphics, i, j, f);
    }
}
