package com.tmplayer.data

/**
 * "Make series": the viewer ticks videos in a chat, types a show name and they become its
 * episodes, for files whose names and captions the parser could not read.
 *
 * It is a rename, not a new kind of record: each file gets the title "Show S01E05" as its
 * [VideoCorrection.title], which [SeriesShelf] already reads for grouping and naming, and which
 * the correction store already keeps, backs up and restores. Typing the name of a show the chat
 * already has puts the files into it.
 */
object ManualSeries {

    const val MAX_SEASON = 99
    const val MAX_EPISODE = 999

    /** What the viewer typed, read: the show's name and where the numbering starts. */
    data class Start(val show: String, val season: Int, val first: Int)

    /**
     * "Harbour Notes" starts at season 1, episode 1; "Harbour Notes S02E05" at season 2, episode
     * 5; "Harbour Notes (2024)" keeps its year. Null for a blank name or one with no title in it.
     */
    fun start(text: String): Start? {
        val typed = text.trim().replace('\n', ' ').take(VideoCorrections.TITLE_MAX)
        if (typed.isEmpty()) return null
        val parsed = MediaName.parse(typed)
        val title = parsed.title.trim().ifEmpty { return null }
        val show = if (parsed.year != null) "$title (${parsed.year})" else title
        return Start(
            show = show,
            season = (parsed.season ?: 1).coerceIn(1, MAX_SEASON),
            first = (parsed.episode ?: 1).coerceIn(1, MAX_EPISODE),
        )
    }

    /** The title a file gets as [episode] of [show]'s [season]: "Harbour Notes S01E05". */
    fun titleFor(show: String, season: Int, episode: Int): String =
        "$show S${season.toString().padStart(2, '0')}E${episode.toString().padStart(2, '0')}"

    /**
     * Each of [ordered] with its new title, numbered from the start [text] gives, in the order
     * given. Null when there is nothing to do: no videos, no usable name, more episodes than a
     * season holds, or a name that would not read back as the show typed (the files would land
     * under some other title).
     */
    fun titles(ordered: List<MediaItem>, text: String): List<Pair<MediaItem, String>>? {
        val files = ordered.distinctBy { it.id }
        if (files.isEmpty()) return null
        val start = start(text) ?: return null
        if (start.first + files.size - 1 > MAX_EPISODE) return null
        val titles = files.mapIndexed { index, item -> item to titleFor(start.show, start.season, start.first + index) }
        val check = MediaName.parse(titles.first().second)
        if (check.episode != start.first || check.season != start.season) return null
        if (check.titleKey != MediaName.parse(start.show).titleKey) return null
        return titles
    }
}
