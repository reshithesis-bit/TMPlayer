# TMPlayer Nuvio-Style Redesign Plan

## 1. Overview and Architecture Principles

This document defines the roadmap for transforming TMPlayer's visual interface and user experience into the premium dark streaming aesthetic of NuvioMobile.

Core tenets:
- Preserve engine: TDLib, Media3/ExoPlayer, MPV, byte-range progressive streaming, and Telegram authorization remain intact.
- Design-system-first: Build foundations in the `:ui` module tokens and components before updating screens.
- TV is a first-class platform: Every control, card, and layout must support D-pad navigation, explicit focus order, and overscan margins.
- Clean room adaptation: Adapt visual styles, token scales, and UX patterns from NuvioMobile under GPL-3.0. Exclude all torrent, scraper, and third-party catalog code.
- Strict typography and syntax: No em dashes or en dashes anywhere in code, comments, or documentation.

---

## 2. Screen Mapping Table

| Screen / Component | Existing TMPlayer File | Nuvio Reference | Redesign Scope | Description |
| --- | --- | --- | --- | --- |
| Design Tokens & Theme | `ui/.../theme/Palette.kt`, `Tone.kt` | `NuvioTokens`, `ThemeColors.kt`, `TypeScale.kt` | Complete redesign | Modern token system: colors (deep dark surfaces), typography scale, spacing, radii, subtle borders, glowing accent swatches (Gold, Jade, RoseGold, ArcticBlue, Crimson, Ocean, Violet, Graphite). |
| Shared Components | `ui/.../components/*.kt` | `Components.kt`, `ShelfComponents.kt` | Complete redesign | Nuvio-styled buttons, text inputs, chips, surface cards, progress bars, shimmer skeleton loaders, badges, and icon buttons. |
| Navigation Shell (Phone) | `MainActivity.kt`, `TouchNav.kt`, `TouchChrome.kt` | `NavigationBar.kt`, `FloatingNavigationBar.kt` | Complete redesign | Floating translucent bottom navigation bar with scroll-aware auto-collapsing labels. |
| Navigation Shell (TV) | `MainActivity.kt`, `TvTopBar.kt`, `TvBarFrame.kt` | Nuvio TV navigation patterns | Complete redesign | Sleek top bar with active destination pills, profile trigger, and crisp D-pad focus indicators. |
| Navigation Shell (Desktop) | `desktop/.../ui/Shell.kt`, `NavBar.kt` | Nuvio sidebar / navigation rail | Complete redesign | Collapsible persistent left sidebar with icon badges, active highlight pill, and smooth width transition. |
| Auth: QR Code Login | `ui/.../auth/QrCode.kt`, `LoginScreen.kt` | Nuvio Auth styling | Partial | Premium dark card for QR code display with glowing accent frame, status indicator, and help action. |
| Auth: Phone & 2FA Code | `ui/.../auth/PhoneEntry.kt`, `AuthFields.kt` | `NuvioInputField`, `NuvioPrimaryButton` | Partial | Clean text fields with rounded borders, smooth focus ring, country picker chip, and error states. |
| Setup & Onboarding | `ui/.../onboarding/SetupScreen.kt`, `Tour.kt` | Nuvio setup modal styling | Partial | Multi-step setup cards with card depth effects, clear choice cards, and progress indicator. |
| App Lock PIN Pad | `ui/.../lock/PinPad.kt` | Nuvio PIN dialog styling | Partial | Minimalist circular number keypad with dot indicators and haptic feedback triggers. |
| Home Screen: Hero Banner | `ui/.../browse/HomeCarousel.kt`, `FeaturedHero.kt` | `HomeHeroSection.kt` | Complete redesign | Cinematic backdrop with bottom/edge gradient fade, metadata badge line, Play and Details action buttons, and pagination indicators. Real Telegram media only. |
| Home Screen: Continue Watching | `ui/.../browse/ContinueCard.kt`, `HomePane.kt` | `HomeContinueWatchingSection.kt` | Complete redesign | Landscape card carousel with real thumbnail/art, prominent watched progress line, remaining time badge, and quick play action. |
| Home Screen: Media Shelves | `ui/.../browse/RowFeed.kt`, `HomeViewModel.kt` | `NuvioShelfSection.kt` | Complete redesign | Horizontal scrolling rows for Recently Added, Watched, Watch Later, and Channels with "View All" pill. |
| Chat Media Browser (Grid) | `app/.../ui/browse/MediaGridScreen.kt`, `desktop/.../MediaGrid.kt` | `PosterGrid.kt`, `ShelfComponents.kt` | Complete redesign | Responsive poster/landscape grid with smooth scrolling, metadata scrim overlay, and watched markers. |
| Search Screen | `app/.../ui/browse/PhoneSearch.kt`, `TvSearch.kt` | Nuvio search interface | Complete redesign | Search header with integrated clear/voice actions, filter chips, and instant results shelf. |
| Sort & Filter Sheets | `VideoSortSheet.kt`, `SizeQuickSheet.kt` | `NuvioBottomSheet.kt`, `DropdownChip.kt` | Complete redesign | Modal bottom sheet with segmented controls, pill selectors, and clean sliders for size limits. |
| Video Details Screen | `ui/.../browse/MediaDetailScreen.kt`, `DetailHero.kt` | `MetaDetailsScreen.kt` | Complete redesign | Immersive header backdrop, Telegram metadata badges (resolution, size, duration, container), Play/Resume primary button, secondary actions (Watch Later, Download, Share), and "More from this chat" carousel. |
| Player Controls Overlay | `app/.../player/PlayerControls.kt`, `desktop/.../PlayerControls.kt` | `PlayerControls.kt`, `PlayerControlsShell` | Complete redesign | Sleek bottom control bar, fine-scrub timebar with buffer indicator, time elapsed/remaining toggle, track selector sheets (audio, subtitles), playback speed sheet, TV remote navigation support. |
| Downloads & Storage | `app/.../ui/downloads/DownloadsScreen.kt`, `desktop/.../Downloads.kt` | Nuvio library & storage cards | Complete redesign | Storage usage donut/bar, active download cards with linear progress and speed, paused/finished list, Free Up Space review sheet. |
| Settings Screen | `app/.../ui/settings/SettingsScreen.kt`, `desktop/.../Settings.kt` | `SettingsScreen.kt`, `SettingsComponents.kt` | Complete redesign | Grouped settings inside rounded surface cards with hairline borders, toggle switches, accent pickers, and clear descriptive labels. |
| About & Updates Dialog | `ui/.../about/About.kt`, `ui/.../update/UpdateDialog.kt` | `AboutSettingsPage.kt` | Partial | Clean branding lockup, release notes card, and action buttons for changelog and update install. |

---

## 3. Form-Factor Specifications and Edge Cases

### Phone
- Layout: Single column vertical scroll with edge-to-edge content.
- Navigation: Floating bottom navigation bar with auto-collapsing labels on scroll downward, expanding on scroll upward.
- Touch Targets: Minimum 48dp interactive areas.
- Sheets: Bottom modal sheets for details, filters, track selections, and context menus.
- Rotation: Preserve active screen, scroll position, and playback state seamlessly across portrait and landscape.

### TV (Android TV & Media PC)
- Layout: 10-foot widescreen layout strictly respecting overscan safe areas (`Tv.SafeH = 48.dp`, `Tv.SafeV = 27.dp`).
- Navigation: Fixed top bar or left rail navigable entirely with D-pad.
- Focus Management: High contrast focus ring (`Tone.accent` / `focusRingColor()`), subtle scale up on focus (1.05x where motion allowed), clear focus return when exiting player or sub-screen.
- Text Size: Strictly 14sp minimum across the entire UI.
- No Complex Gestures: Everything accessible via Directional keys, Center/Select, and Back. Back never traps.

### Desktop (Windows, macOS, Linux)
- Layout: Responsive multi-column layout adapting to window resizing from 800px to ultrawide 4K.
- Navigation: Persistent collapsible sidebar navigation rail with icon tips and active state indicators.
- Pointer & Keyboard: Mouse hover states on cards and buttons, right-click context menus, standard keyboard shortcuts (Space to play, F for fullscreen, Esc to exit).
- Window Chrome: Integrated custom title bar respecting OS window buttons.

### Edge Cases Matrix

| Case | Phone Behavior | TV Behavior | Desktop Behavior |
| --- | --- | --- | --- |
| Empty State | Centered Nuvio styled empty illustration with explanatory text and primary action button (e.g. "Choose Chats"). | Same as phone, D-pad focus positioned directly on action button. | Centered card within content pane with action button. |
| Loading State | Shimmer skeleton cards matching exact dimensions of upcoming content. | Shimmer skeleton cards; first card holds initial focus to prevent focus loss. | Shimmer skeleton cards. |
| Network / Stream Error | Non-intrusive connection pill at screen top with retry action. Retry without looping. | Warning card with explicit "Try Again" and "Proxy Settings" D-pad focus targets. | Banner with "Retry" and "Connection Settings" actions. |
| Missing Thumbnail | Procedural gradient card with video title initial, duration badge, and container pill. No blank gray boxes. | Same procedural card with sharp text and focus ring. | Same procedural card. |
| Rapid Seeks & Buffering | Buffered progress displayed along time bar. Speed indicator shown when loading is constrained. | Same; stable buffer rendering without flicker on low-spec hardware. | Same; smooth timeline scrubbing. |
| Process Re-creation / Rotation | SavedState handles active tab, chat selection, and filter criteria. No full reload flash. | Orientation locked to landscape; configuration changes handled safely. | Window state (size, position, split pane widths) restored from desktop prefs. |

---

## 4. Nuvio Patterns: Adapted vs Excluded

### Patterns to Adapt
1. Dark Cinematic Palette: OLED-friendly dark background (`#0D0D0D`) with layered surfaces (`#1A1A1A`, `#242424`).
2. Accent Color System: Multi-hue theme engine (Gold, Jade, RoseGold, ArcticBlue, Crimson, Ocean, Violet, Graphite) applied across focus rings, active pills, and progress bars.
3. Card Depth & Borders: Rounded corners (`24.dp` for large cards, `12.dp` for compact cards) with subtle hairline borders (`0.5.dp` to `1.dp`) instead of heavy outlines.
4. Floating Navigation: Pill-style floating navigation bar with backdrop blur and responsive label visibility.
5. Shimmer Skeleton Feed: Pulsing shimmer placeholders mimicking final layout structure during initial load.
6. Settings Card Grouping: Cohesive card-grouped settings with embedded switches, pickers, and action rows.
7. Polished Player Controls: Clean transport overlay, floating slider bar, and dedicated modal panels for track and speed configuration.

### Excluded Patterns (Strict Hard Rules)
1. Torrent and P2P: No torrents, magnet links, BitTorrent engine, DHT, or peer discovery.
2. Debrid Services: No Real-Debrid, AllDebrid, Premiumize, or Torbox integrations.
3. Third-party Scrapers / Addons: No Stremio addons, external video scraping engines, or community scraper repositories.
4. External Video Providers: TMPlayer remains strictly powered by TDLib and the user's Telegram chats and channels.
5. Trakt / Simkl Sync: Excluded from core playback, preserving TMPlayer's private local playback state.

---

## 5. Implementation Roadmap (Phases)

- Phase 0: Analysis and design mapping (PLAN.md created, feature branch setup).
- Phase 1: Design system foundations in `:ui` (colors, typography, shapes, tokens, shared buttons, cards, skeletons).
- Phase 2: Navigation shells (Phone floating bar, TV top bar, Desktop sidebar) and redesigned Home screen.
- Phase 3: Media browser, grids, card components, sort/filter sheets, and search UI.
- Phase 4: Video details screen with backdrop hero, Telegram metadata layout, and related media row.
- Phase 5: Player UI overhaul (ExoPlayer/Media3 and MPV controls overlay, timebar, track sheets) maintaining 100% playback engine stability.
- Phase 6: Settings reorganization and redesign into Nuvio-style grouped surface cards.
- Phase 7: Polish, edge cases, theme audit, machine verification, and documentation.

---

## 6. Verification and Test Strategy

Each phase will be verified using:
1. Compilation check across modules (`:core`, `:ui`, `:app`, `:desktop`).
2. Unit and JVM tests: `./gradlew :core:jvmTest :ui:jvmTest :desktop:test`.
3. Focus and D-pad tests: Compose test assertions for TV navigation (`performKeyInput`, `assertIsFocused`).
4. Manual verification checklist: Telegram QR/phone auth, media browsing, seeking and progressive streaming, track switching, TV remote handling.
5. Local Git commits on the `redesign/nuvio-style` feature branch.

---

## 7. Progress / Handoff

### Phase 0 Status: Complete
- Created `PLAN.md` documenting complete screen mappings, form factor specs, edge cases, and adapted vs excluded patterns.
- Created and switched to local feature branch `redesign/nuvio-style`.
- Verified repository rules: GPL-3.0 compliance, zero em/en dashes, and strict separation of UI from the streaming engine.

### Phase 1 Status: Complete
- Design system foundation and tokens implemented in `:ui`:
  - `com.tmplayer.ui.theme.TmTokens`: Spacing (0-64dp), Radius (cards 24dp, buttons 16dp, badges 4dp, pills full), Border (hairline 0.5dp, thin 1dp, medium 2dp), Elevation, Opacity, Motion (standard/decelerate/accelerate easings and duration tokens), and Breakpoints.
  - `com.tmplayer.ui.theme.Tone`: Extended with `surfaceCard`, `borderSubtle`, `borderDefault`, `shimmer`, and `skeleton`.
  - `com.tmplayer.ui.components.TmDesignSystem`: Nuvio-style components with focus and press animations: `TmSurfaceCard`, `TmClickableCard`, `TmPrimaryButton`, `TmSecondaryButton`, `TmBackButton`, `TmIconButton`, `TmInputField`, `TmChip`, `TmBadge`, and `TmProgressBar`.
- Machine Verification:
  - OpenJDK 21 configured.
  - `:ui:jvmTest` executed and passed completely (all contrast and component tests green).
  - `:desktop:compileKotlin` compiled successfully.
  - Em/en dash check: verified zero instances in code, comments, or documentation.
- Manual checklist for Phase 1 components:
  - Contrast check: Verified via automated `AccentContrastTest` meeting WCAG AA requirements across all accents.
  - Visual hierarchy: Cards use subtle translucent hairline borders rather than heavy opaque strokes.
  - TV focus indicator: Integrates with `focusRing` and D-pad states.

### Phase 2 Status: Complete
- Navigation and Home redesign implemented across form factors:
  - Desktop persistent sidebar: Added `DesktopSidebar` with Nuvio dark aesthetic, brand lockup, search shortcut (Ctrl+K), primary destinations (Home, Library, Chats, Channels, Continue Watching, History, Downloads, Settings), and profile button with side-placed popover.
  - Adaptive desktop navigation: `NavFrame` automatically renders persistent `DesktopSidebar` on standard desktop windows (height >= 400dp) and seamlessly falls back to floating `NavBar` for compact heights, preserving full backward compatibility with short test fixtures.
  - TV top bar and D-pad focus: Modernized `TvTab` on both desktop and Android TV with Nuvio glowing focus states (1.08x scale, high contrast text, accent background glow) while preserving the full D-pad focus graph.
  - Home hero pills: Enhanced hero action buttons with `pressScale` tactile interaction feedback.
  - Home empty states: Redesigned `FirstVideoCard`, `FavouritesHintCard`, `HomeOffCard`, and `HomeNoneChosenCard` with `TmSurfaceCard`, subtle borders, and Nuvio dark styling.
  - Design system navigation component: Added `TmNavItem` supporting both horizontal and compact rail modes with accessibility semantics, focus rings, and selection indicators.
- Machine Verification:
  - `:desktop:compileKotlin` compiled cleanly with 0 errors.
  - `:desktop:test` passed (including `FocusAndSizesShotsTest`, `BigScreenKeysTest`, `SelectedAndEmptyShotsTest`, etc.).
  - `:ui:jvmTest` passed cleanly.
  - Em/en dash check: verified 0 instances of unicode dashes in modified files and docs.
- Manual checklist:
  - Phone: Adaptive bottom navigation bar and home shelves with real Telegram data.
  - Desktop: Persistent sidebar on left with Home, Library, Chats, Channels, Continue Watching, History, Downloads, Settings.
  - TV: Top bar navigation with clear D-pad focus scaling and accent highlight.
  - Home empty states: Test with empty library / no chosen chats to verify Nuvio dark surface cards.

### Phase 3 Status: Complete
- Media browser and cards redesign implemented across form factors:
  - Video and poster cards: Redesigned `Poster` in `desktop/ui/MediaGrid.kt` and `VideoTile` in `app/ui/browse/MediaGridScreen.kt` with deep dark OLED surface (`Tone.surfaceCard`), subtle hairline border (`TmTokens.Border.hairline` with `Tone.borderSubtle`), and accent glow on focus/selection.
  - Video typography: Enhanced title and metadata hierarchy with `FontWeight.SemiBold`, responsive font sizing, and dynamic color transitions on hover/focus.
  - Progress bar: Upgraded `ProgressLine` with rounded capsule endpoints (`drawRoundRect` with `size.height / 2f` corner radius) and accent fill for clean Nuvio watch progress indication.
  - Glassmorphic badges: Refined `TileBadge` with subtle hairline border (`Color.White.copy(alpha = 0.16f)`) and dark translucent fill.
  - Media thumbnail fallbacks: Updated `MediaArt` and `PosterArt` to guarantee an attractive dark placeholder (`Tone.surfaceCard` with `PlayCircle` icon) when neither Telegram thumbnail nor online cover is present.
  - Series poster: Modernized `SeriesPoster` in `desktop/ui/SeriesViews.kt` with matching card styling and hairline borders.
  - Search results: Updated `SearchResultCard` and `ResultPoster` with `Tone.surfaceCard`, hairline borders, and semi-bold typography.
  - Dialog and sheet floating borders: Updated `floatingBorder()` in `ui/theme/Surfaces.kt` to use `Tone.borderSubtle` in dark theme.
- Machine Verification:
  - `:desktop:compileKotlin` compiled cleanly with 0 errors.
  - `:desktop:test` passed (including `TileRenderTest`, `BrowseRenderTest`, etc.).
  - `:ui:jvmTest` passed cleanly.
  - Em/en dash check: verified 0 instances of unicode dashes in modified files and docs.
- Manual checklist:
  - Card hover/focus: Hover on desktop card or navigate with TV remote to verify elevation, scale (1.05x), accent ring, and circular play button.
  - Watch progress: Verify rounded capsule progress bar at bottom of partially watched media cards.
  - Missing thumbnails: Check files with no thumbnail to verify clean Nuvio gradient / play icon fallback.
  - Grid sorting and filters: Verify Sort menu (Newest, Oldest, Name, Longest, Size) and Filter options (All, Unwatched, In Progress, Watched, High Definition) in chat media grid.

Next step: Wait for user confirmation to begin Phase 4 (Video details screen).
