package com.tmplayer.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * Design tokens for TMPlayer.
 * Licensed under GNU General Public License v3.0 or later.
 * Design aesthetic and token scales adapted from NuvioMobile (GPL-3.0).
 */

/**
 * Standard design tokens for spacing, corner radius, borders, opacity, and motion.
 * Shared across phone, TV, and desktop form factors.
 */
object TmTokens {

    /** Spacing scale from 0 to 64 dp. */
    object Space {
        val none = 0.dp
        val hairline = 0.5.dp
        val s1 = 1.dp
        val s2 = 2.dp
        val s4 = 4.dp
        val s6 = 6.dp
        val s8 = 8.dp
        val s10 = 10.dp
        val s12 = 12.dp
        val s14 = 14.dp
        val s16 = 16.dp
        val s18 = 18.dp
        val s20 = 20.dp
        val s24 = 24.dp
        val s28 = 28.dp
        val s32 = 32.dp
        val s40 = 40.dp
        val s48 = 48.dp
        val s56 = 56.dp
        val s64 = 64.dp
    }

    /** Corner radius scale matching Nuvio's rounded streaming aesthetic. */
    object Radius {
        val none = 0.dp
        val xs = 4.dp
        val sm = 6.dp
        val md = 8.dp
        val lg = 12.dp
        val xl = 16.dp
        val xxl = 24.dp
        val full = 999.dp

        val card = xxl
        val compactCard = lg
        val sheet = xxl
        val dialog = 28.dp
        val button = xl
        val chip = full
        val poster = lg
        val avatar = full
        val playerPanel = xxl
    }

    /** Subtle border widths: no heavy borders. */
    object Border {
        val hairline = 0.5.dp
        val thin = 1.dp
        val medium = 2.dp
        val focus = medium
    }

    /** Elevation steps. */
    object Elevation {
        val none = 0.dp
        val raised = 2.dp
        val modal = 8.dp
        val overlay = 12.dp
        val playerControls = 4.dp
    }

    /** Standard opacity levels. */
    object Opacity {
        const val invisible = 0f
        const val disabled = 0.38f
        const val secondary = 0.70f
        const val muted = 0.60f
        const val selected = 0.15f
        const val hover = 0.08f
        const val pressed = 0.12f
        const val subtle = 0.06f
        const val strong = 0.75f
        const val overlayLight = 0.35f
        const val overlayMedium = 0.56f
        const val overlayHeavy = 0.82f
        const val visible = 1f
    }

    /** Motion tokens: duration and easing. */
    object Motion {
        const val instantMillis = 0
        const val fastMillis = 150
        const val normalMillis = 220
        const val sheetEnterMillis = 300
        const val sheetExitMillis = 250
        const val slowMillis = 400
        const val cinematicMillis = 700

        val standard: Easing = CubicBezierEasing(0.2f, 0f, 0f, 1f)
        val decelerate: Easing = CubicBezierEasing(0f, 0f, 0f, 1f)
        val accelerate: Easing = CubicBezierEasing(0.3f, 0f, 1f, 1f)
    }

    /** Responsive layout breakpoints. */
    object Breakpoint {
        val phone = 0.dp
        val largePhone = 420.dp
        val tablet = 600.dp
        val largeTablet = 840.dp
        val desktop = 1024.dp
        val playerWide = 1280.dp
    }
}
