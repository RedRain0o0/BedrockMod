package io.github.redrain0o0.bedrock.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Shadow public static @Final @Mutable ResourceLocation MINECRAFT_LOGO;
    @Shadow public static @Final @Mutable ResourceLocation EASTER_EGG_LOGO;
    @Shadow public static @Final @Mutable ResourceLocation MINECRAFT_EDITION;
    @Shadow private @Final boolean keepLogoThroughFade;
    @Shadow private @Final boolean showEasterEgg;

    @WrapMethod(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V")
    public void renderLogo(GuiGraphics guiGraphics, int i, float f, int j, Operation<Void> original) {
        int k = i / 2 - (int)(256 * 0.7);
        float g = this.keepLogoThroughFade ? 1.0F : f;
        int l = ARGB.white(g);
        guiGraphics.blit(RenderType::guiTextured, this.showEasterEgg ? EASTER_EGG_LOGO : MINECRAFT_LOGO, k, j+18, 0.0F, 0.0F, (int)(512 * 0.7), (int)(88 * 0.7), (int)(512 * 0.7), (int)(128 * 0.7), l);
    }
}