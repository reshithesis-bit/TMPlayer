package com.tmplayer.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tmplayer.ui.icons.TmIcons
import com.tmplayer.ui.theme.TmTokens
import com.tmplayer.ui.theme.Tone
import com.tmplayer.ui.theme.collectIsFocusVisibleAsState
import com.tmplayer.ui.theme.focusRing

/*
 * Shared design system components for TMPlayer.
 * Licensed under GNU General Public License v3.0 or later.
 * Visual components and styling patterns adapted from NuvioMobile (GPL-3.0).
 */

/**
 * Standard Nuvio styled surface card with rounded corners and subtle hairline border.
 */
@Composable
fun TmSurfaceCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(TmTokens.Radius.card),
    backgroundColor: Color = Tone.surfaceCard,
    borderColor: Color = Tone.borderSubtle,
    borderWidth: Dp = TmTokens.Border.hairline,
    contentPadding: PaddingValues = PaddingValues(TmTokens.Space.s16),
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = backgroundColor,
        shape = shape,
        border = BorderStroke(borderWidth, borderColor),
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content,
        )
    }
}

/**
 * Clickable Nuvio styled surface card with interaction and TV focus ring support.
 */
@Composable
fun TmClickableCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(TmTokens.Radius.card),
    backgroundColor: Color = Tone.surfaceCard,
    borderColor: Color = Tone.borderSubtle,
    borderWidth: Dp = TmTokens.Border.hairline,
    contentPadding: PaddingValues = PaddingValues(TmTokens.Space.s16),
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .pressScale(interactions)
            .focusRing(focused, shape)
            .clickable(
                interactionSource = interactions,
                indication = null,
                onClick = onClick,
            ),
        color = backgroundColor,
        shape = shape,
        border = BorderStroke(borderWidth, if (focused) Tone.accent else borderColor),
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content,
        )
    }
}

/**
 * Modern rounded primary button with accent fill and smooth press/focus feedback.
 */
@Composable
fun TmPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(TmTokens.Radius.button),
    icon: ImageVector? = null,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minHeight = TmTokens.Space.s48)
            .pressScale(interactions)
            .focusRing(focused, shape),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Tone.accent,
            contentColor = Tone.onAccent,
            disabledContainerColor = Tone.accent.copy(alpha = TmTokens.Opacity.disabled),
            disabledContentColor = Tone.onAccent.copy(alpha = TmTokens.Opacity.disabled),
        ),
        contentPadding = PaddingValues(horizontal = TmTokens.Space.s20, vertical = TmTokens.Space.s12),
        interactionSource = interactions,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(TmTokens.Space.s8))
            }
            AnimatedContent(
                targetState = text,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "tmButtonText",
            ) { animatedText ->
                Text(
                    text = animatedText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

/**
 * Tonal secondary button with subtle border and muted background.
 */
@Composable
fun TmSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(TmTokens.Radius.button),
    icon: ImageVector? = null,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minHeight = TmTokens.Space.s48)
            .pressScale(interactions)
            .focusRing(focused, shape),
        enabled = enabled,
        shape = shape,
        border = BorderStroke(TmTokens.Border.thin, if (focused) Tone.accent else Tone.borderSubtle),
        colors = ButtonDefaults.buttonColors(
            containerColor = Tone.surfaceHigh,
            contentColor = Tone.text,
            disabledContainerColor = Tone.surfaceHigh.copy(alpha = TmTokens.Opacity.disabled),
            disabledContentColor = Tone.text.copy(alpha = TmTokens.Opacity.disabled),
        ),
        contentPadding = PaddingValues(horizontal = TmTokens.Space.s20, vertical = TmTokens.Space.s12),
        interactionSource = interactions,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(TmTokens.Space.s8))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/**
 * Circular back button in the style of Nuvio.
 */
@Composable
fun TmBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "Back",
    buttonSize: Dp = TmTokens.Space.s40,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    Box(
        modifier = modifier
            .size(buttonSize)
            .pressScale(interactions)
            .clip(CircleShape)
            .background(Tone.surface)
            .border(TmTokens.Border.hairline, Tone.borderSubtle, CircleShape)
            .focusRing(focused, CircleShape)
            .clickable(
                interactionSource = interactions,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = TmIcons.ArrowBack,
            contentDescription = contentDescription,
            tint = Tone.text,
            modifier = Modifier.size(20.dp),
        )
    }
}

/**
 * Circular icon action button.
 */
@Composable
fun TmIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    buttonSize: Dp = TmTokens.Space.s40,
    iconSize: Dp = 20.dp,
    tint: Color = Tone.text,
    containerColor: Color = Tone.surfaceCard,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    Box(
        modifier = modifier
            .size(buttonSize)
            .pressScale(interactions)
            .clip(CircleShape)
            .background(containerColor)
            .border(TmTokens.Border.hairline, Tone.borderSubtle, CircleShape)
            .focusRing(focused, CircleShape)
            .clickable(
                interactionSource = interactions,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(iconSize),
        )
    }
}

/**
 * Modern outlined input text field with rounded compact shape.
 */
@Composable
fun TmInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(TmTokens.Radius.compactCard),
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        shape = shape,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = placeholder,
                color = Tone.muted,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Tone.text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Tone.accent,
            unfocusedBorderColor = Tone.borderDefault,
            focusedContainerColor = Tone.surfaceCard,
            unfocusedContainerColor = Tone.surfaceCard,
            cursorColor = Tone.accent,
        ),
    )
}

/**
 * Filter / topic pill chip with smooth state transitions.
 */
@Composable
fun TmChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    val shape = CircleShape
    val containerColor = if (selected) Tone.accent else Tone.surfaceCard
    val contentColor = if (selected) Tone.onAccent else Tone.muted
    val borderColor = if (selected) Tone.accent else Tone.borderSubtle

    Box(
        modifier = modifier
            .pressScale(interactions)
            .clip(shape)
            .background(containerColor)
            .border(TmTokens.Border.hairline, borderColor, shape)
            .focusRing(focused, shape)
            .clickable(
                interactionSource = interactions,
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = TmTokens.Space.s14, vertical = TmTokens.Space.s8),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(Modifier.width(TmTokens.Space.s6))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = contentColor,
            )
        }
    }
}

/**
 * Compact metadata badge for video specs (e.g. 4K, HDR, duration).
 */
@Composable
fun TmBadge(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = Tone.borderSubtle,
                shape = RoundedCornerShape(TmTokens.Radius.xs),
            )
            .padding(horizontal = TmTokens.Space.s8, vertical = TmTokens.Space.s4),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = Tone.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/**
 * Watched progress bar with rounded ends and accent fill.
 */
@Composable
fun TmProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: Dp = 4.dp,
    trackColor: Color = Tone.track,
    fillColor: Color = Tone.accent,
) {
    val clamped = progress.coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(trackColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(clamped)
                .height(height)
                .clip(CircleShape)
                .background(fillColor),
        )
    }
}

/**
 * Navigation item for desktop sidebar, phone bottom nav, and TV focusable rail.
 * Adopts Nuvio styling with rounded pill highlight, smooth scale, and active accent glow.
 */
@Composable
fun TmNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: String? = null,
    compact: Boolean = false,
    horizontal: Boolean = true,
) {
    val interactions = remember { MutableInteractionSource() }
    val focused by interactions.collectIsFocusVisibleAsState()
    val shape = RoundedCornerShape(TmTokens.Radius.lg)
    val background = when {
        selected -> Tone.accent.copy(alpha = TmTokens.Opacity.selected)
        focused -> Tone.accent.copy(alpha = TmTokens.Opacity.hover)
        else -> Color.Transparent
    }
    val contentColor = when {
        selected -> Tone.accent
        focused -> Tone.text
        else -> Tone.muted
    }

    if (horizontal && !compact) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 44.dp)
                .pressScale(interactions)
                .clip(shape)
                .background(background)
                .focusRing(focused, shape)
                .clickable(
                    interactionSource = interactions,
                    indication = null,
                    role = Role.Tab,
                    onClick = onClick,
                )
                .semantics {
                    contentDescription = label
                    this.selected = selected
                    this.role = Role.Tab
                }
                .padding(horizontal = TmTokens.Space.s12, vertical = TmTokens.Space.s10),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TmTokens.Space.s12),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            if (badge != null) {
                TmBadge(text = badge)
            }
        }
    } else {
        Box(
            modifier = modifier
                .size(44.dp)
                .pressScale(interactions)
                .clip(shape)
                .background(background)
                .focusRing(focused, shape)
                .clickable(
                    interactionSource = interactions,
                    indication = null,
                    role = Role.Tab,
                    onClick = onClick,
                )
                .semantics {
                    contentDescription = label
                    this.selected = selected
                    this.role = Role.Tab
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = contentColor,
                modifier = Modifier.size(22.dp),
            )
            if (badge != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(TmTokens.Space.s2)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Tone.caution),
                )
            }
        }
    }
}

