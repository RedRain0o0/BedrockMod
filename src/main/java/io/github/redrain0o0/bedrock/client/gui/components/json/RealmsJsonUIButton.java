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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class RealmsJsonUIButton extends AbstractButton {
    public static final int SMALL_WIDTH = 120;
    public static final int DEFAULT_WIDTH = 150;
    public static final int BIG_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_SPACING = 8;
    private static final WidgetSprites SPRITES_REALMS = new WidgetSprites(Bedrockmod.createId("widget/button_realms"), Bedrockmod.createId("widget/button_realms_disabled"), Bedrockmod.createId("widget/button_realms_highlighted"));
    protected static final CreateNarration DEFAULT_NARRATION = (supplier) -> {
        return (MutableComponent)supplier.get();
    };
    protected final OnPress onPress;
    protected final CreateNarration createNarration;

    public static Builder builder(Component component, OnPress onPress) {
        return new Builder(component, onPress);
    }

    protected RealmsJsonUIButton(int i, int j, int k, int l, Component component, OnPress onPress, CreateNarration createNarration) {
        super(i, j, k, l, component);
        this.onPress = onPress;
        this.createNarration = createNarration;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.blitSprite(RenderType::guiTextured, SPRITES_REALMS.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        int k = this.active ? 16777215 : 10526880;
        this.renderString(guiGraphics, minecraft.font, ARGB.color(255, 16777215));
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
        private final Component message;
        private final OnPress onPress;
        @Nullable
        private Tooltip tooltip;
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private CreateNarration createNarration;

        public Builder(Component component, OnPress onPress) {
            this.createNarration = DEFAULT_NARRATION;
            this.message = component;
            this.onPress = onPress;
        }

        public Builder pos(int i, int j) {
            this.x = i;
            this.y = j;
            return this;
        }

        public Builder width(int i) {
            this.width = i;
            return this;
        }

        public Builder size(int i, int j) {
            this.width = i;
            this.height = j;
            return this;
        }

        public Builder bounds(int i, int j, int k, int l) {
            return this.pos(i, j).size(k, l);
        }

        /*public Builder texture(String texture) {
            if (texture.matches("realms")) {
                return SPRITES_REALMS;
            } else if (texture.matches("market")) {
                    return SPRITES_MARKET;
            } else {
                return SPRITES_DEFAULT;
            }
        }*/

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder createNarration(CreateNarration createNarration) {
            this.createNarration = createNarration;
            return this;
        }

        public RealmsJsonUIButton build() {
            RealmsJsonUIButton oreUIButton = new RealmsJsonUIButton(this.x, this.y, this.width, this.height, this.message, this.onPress, this.createNarration);
            oreUIButton.setTooltip(this.tooltip);
            return oreUIButton;
        }
    }

    @Environment(EnvType.CLIENT)
    public interface OnPress {
        void onPress(RealmsJsonUIButton oreUIButton);
    }

    @Environment(EnvType.CLIENT)
    public interface CreateNarration {
        MutableComponent createNarrationMessage(Supplier<MutableComponent> supplier);
    }
}
