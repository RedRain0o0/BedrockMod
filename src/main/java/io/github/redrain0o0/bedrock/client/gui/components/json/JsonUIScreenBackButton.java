package io.github.redrain0o0.bedrock.client.gui.components.json;

import io.github.redrain0o0.bedrock.Bedrockmod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class JsonUIScreenBackButton extends AbstractButton {
    private static final WidgetSprites SPRITES_BACK = new WidgetSprites(Bedrockmod.createId("widget/ore_ui_back_button"), Bedrockmod.createId("widget/ore_ui_back_button_disabled"), Bedrockmod.createId("widget/ore_ui_back_button_pressed"));
    protected static final CreateNarration DEFAULT_NARRATION = (supplier) -> {
        return (MutableComponent)supplier.get();
    };
    protected final OnPress onPress;
    protected final CreateNarration createNarration;
    private static final Component component = Component.empty();

    public static Builder builder() {
        return new Builder(component);
    }

    protected JsonUIScreenBackButton(int i, int j, int k, int l, OnPress onPress, CreateNarration createNarration) {
        super(i, j, k, l, component);
        this.onPress = onPress;
        this.createNarration = createNarration;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.blitSprite(RenderType::guiTextured, SPRITES_BACK.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        this.renderString(guiGraphics, minecraft.font, 16777215);
    }

    protected MutableComponent createNarrationMessage() {
        return this.createNarration.createNarrationMessage(() -> {
            return super.createNarrationMessage();
        });
    }

    public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Component message = Component.empty();
        private final Minecraft minecraft = Minecraft.getInstance();
        private OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private Screen lastScreen;
        private CreateNarration createNarration;

        public Builder(Component component) {
            this.createNarration = DEFAULT_NARRATION;
        }

        public Builder screen(@Nullable Screen screen) {
            this.lastScreen = screen;
            this.onPress = (button) -> {this.minecraft.setScreen(this.lastScreen);};
            return this;
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder createNarration(CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public JsonUIScreenBackButton build() {
            JsonUIScreenBackButton oreUIScreenBackButton = new JsonUIScreenBackButton(2, 2, 16, 16, this.onPress, this.createNarration);
            oreUIScreenBackButton.setTooltip(this.tooltip);
            return oreUIScreenBackButton;
        }
    }

    @Environment(EnvType.CLIENT)
    public interface OnPress {
        void onPress(JsonUIScreenBackButton oreUIScreenBackButton);
    }

    @Environment(EnvType.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> supplier);
    }
}
