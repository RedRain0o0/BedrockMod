package io.github.redrain0o0.bedrock.mixin.client;

import com.mojang.realmsclient.RealmsMainScreen;
import io.github.redrain0o0.bedrock.Bedrockmod;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    @Shadow @Nullable private SplashRenderer splash;
    //@Shadow @Nullable private Component getMultiplayerDisabledReason;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "<init>(ZLnet/minecraft/client/gui/components/LogoRenderer;)V", at = @At("RETURN"))
    public void init(boolean bl, LogoRenderer logoRenderer, CallbackInfo ci) {
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    protected void init(CallbackInfo ci) {
        ci.cancel();
        super.init();;
        int l = this.height / 4 + 48;
        this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer").withColor(5000268).withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 74, l, 148, 30).build());
        this.addRenderableWidget(Button.builder(Component.translatable("menu.options").withColor(5000268).withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build());
        ((Button)this.addRenderableWidget(Button.builder(Component.translatable("menu.online").withColor(16777215).withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
        //    this.minecraft.setScreen(new RealmsMainScreen(this));
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build())).active = true;
        this.addRenderableWidget(Button.builder(Component.translatable("bedrock.menu.marketplace").withColor(16761343).withStyle(Style.EMPTY.withShadowColor(0)), (button) -> {
            this.minecraft.stop();
        }).bounds(this.width / 2 - 74, l += 32, 148, 30).build());
        //if (this.minecraft.isDemo()) {
        //    l = TitleScreen.createDemoMenuOptions(l, 24);
        //} else {
        //    l = TitleScreen.createNormalMenuOptions(l, 24);
        //}
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)I", ordinal = 0))
    private String onRender(String string) {
        return "";
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void addVersion(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        super.render(guiGraphics, i, j, f);
        String version = "v" + SharedConstants.getCurrentVersion().getName();
        guiGraphics.fill(this.width - this.font.width(version) - 2, this.height - 12, this.width, this.height, 0xAA000000);
        guiGraphics.drawString(this.font, Component.literal(version).withStyle(Style.EMPTY.withShadowColor(0)), this.width - this.font.width(version) - 1, this.height - 10, 16777215);
        String copyright = "©Mojang AB";
        guiGraphics.fill(0, this.height - 12, this.font.width(copyright) + 3, this.height, 0xAA000000);
        guiGraphics.drawString(this.font, Component.literal(copyright).withStyle(Style.EMPTY.withShadowColor(0)), 1, this.height - 10, 16777215);
    }
}
