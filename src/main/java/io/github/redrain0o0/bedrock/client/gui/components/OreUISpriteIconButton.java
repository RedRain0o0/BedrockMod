package io.github.redrain0o0.bedrock.client.gui.components;

import io.github.redrain0o0.bedrock.Bedrockmod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;

public class OreUISpriteIconButton extends Button {
    private static final WidgetSprites SPRITES_DEFAULT = new WidgetSprites(Bedrockmod.createId("widget/button"), Bedrockmod.createId("widget/button_disabled"), Bedrockmod.createId("widget/button_highlighted"));
    protected final ResourceLocation sprite;
    protected final int spriteWidth;
    protected final int spriteHeight;

    OreUISpriteIconButton(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress, @Nullable Button.CreateNarration createNarration) {
        super(0, 0, i, j, component, onPress, createNarration == null ? DEFAULT_NARRATION : createNarration);
        this.spriteWidth = k;
        this.spriteHeight = l;
        this.sprite = resourceLocation;
    }

    protected void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.blitSprite(RenderType::guiTextured, SPRITES_DEFAULT.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));
        int k = this.isHoveredOrFocused() ? 16777215 : 10526880;
        this.renderString(guiGraphics, minecraft.font, this.isHoveredOrFocused() ? ARGB.color(255, 16777215) : ARGB.color(255, 5000268));
    }

    public static Builder builder(Component component, Button.OnPress onPress, boolean bl) {
        return new Builder(component, onPress, bl);
    }

    @Environment(EnvType.CLIENT)
    public static class Builder {
        private final Component message;
        private final Button.OnPress onPress;
        private final boolean iconOnly;
        private int width = 150;
        private int height = 20;
        @Nullable
        private ResourceLocation sprite;
        private int spriteWidth;
        private int spriteHeight;
        @Nullable
        Button.CreateNarration narration;

        public Builder(Component component, Button.OnPress onPress, boolean bl) {
            this.message = component;
            this.onPress = onPress;
            this.iconOnly = bl;
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

        public Builder sprite(ResourceLocation resourceLocation, int i, int j) {
            this.sprite = resourceLocation;
            this.spriteWidth = i;
            this.spriteHeight = j;
            return this;
        }

        public Builder narration(Button.CreateNarration createNarration) {
            this.narration = createNarration;
            return this;
        }

        public OreUISpriteIconButton build() {
            if (this.sprite == null) {
                throw new IllegalStateException("Sprite not set");
            } else {
                return (OreUISpriteIconButton)(this.iconOnly ? new CenteredIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress, this.narration) : new OreUISpriteIconButton.TextAndIcon(this.width, this.height, this.message, this.spriteWidth, this.spriteHeight, this.sprite, this.onPress, this.narration));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class TextAndIcon extends OreUISpriteIconButton {
        protected TextAndIcon(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress, @Nullable Button.CreateNarration createNarration) {
            super(i, j, component, k, l, resourceLocation, onPress, createNarration);
        }

        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            super.renderWidget(guiGraphics, i, j, f);
            int k = this.getX() + this.getWidth() - this.spriteWidth - 2;
            int l = this.getY() + this.getHeight() / 2 - this.spriteHeight / 2;
            guiGraphics.blitSprite(RenderType::guiTextured, this.sprite, k, l, this.spriteWidth, this.spriteHeight);
        }

        public void renderString(GuiGraphics guiGraphics, Font font, int i) {
            int j = this.getX() + 2;
            int k = this.getX() + this.getWidth() - this.spriteWidth - 4;
            int l = this.getX() + this.getWidth() / 2;
            renderScrollingString(guiGraphics, font, this.getMessage(), l, j, this.getY(), k, this.getY() + this.getHeight(), i);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class CenteredIcon extends OreUISpriteIconButton {
        protected CenteredIcon(int i, int j, Component component, int k, int l, ResourceLocation resourceLocation, Button.OnPress onPress, @Nullable Button.CreateNarration createNarration) {
            super(i, j, component, k, l, resourceLocation, onPress, createNarration);
        }

        public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
            super.renderWidget(guiGraphics, i, j, f);
            int k = this.getX() + this.getWidth() / 2 - this.spriteWidth / 2;
            int l = this.getY() + this.getHeight() / 2 - this.spriteHeight / 2;
            guiGraphics.blitSprite(RenderType::guiTextured, this.sprite, k, l, this.spriteWidth, this.spriteHeight);
        }

        public void renderString(GuiGraphics guiGraphics, Font font, int i) {
        }
    }
}
