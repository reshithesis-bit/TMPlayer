package com.tmplayer.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ManualSeriesTest {

    private var nextId = 1L

    private fun video(fileName: String = "") = MediaItem(
        chatId = 7,
        messageId = nextId++,
        fileId = 0,
        title = fileName.ifEmpty { "Video" },
        sizeBytes = 1,
        durationSec = 60,
        mimeType = "video/mp4",
        thumbnailFileId = 0,
        miniThumbnail = null,
        date = nextId.toInt(),
        fileName = fileName,
    )

    @Test
    fun `a name alone starts at season one, episode one`() {
        assertEquals(ManualSeries.Start("Harbour Notes", 1, 1), ManualSeries.start("Harbour Notes"))
        assertEquals(ManualSeries.Start("Harbour Notes", 2, 5), ManualSeries.start("Harbour Notes S02E05"))
        assertEquals(ManualSeries.Start("Harbour Notes (2024)", 1, 1), ManualSeries.start("Harbour Notes (2024)"))
        assertNull(ManualSeries.start("   "))
        assertNull(ManualSeries.start("S01E02"))
    }

    @Test
    fun `ticked videos are numbered in the order given`() {
        val files = listOf(video("IMG_0003.mp4"), video("IMG_0001.mp4"), video("IMG_0002.mp4"))
        val titles = ManualSeries.titles(files, "Mevlana Rumi")!!
        assertEquals(listOf("Mevlana Rumi S01E01", "Mevlana Rumi S01E02", "Mevlana Rumi S01E03"), titles.map { it.second })
        assertEquals(files.map { it.id }, titles.map { it.first.id })
        assertEquals("Mevlana Rumi S02E07", ManualSeries.titles(files.take(1), "Mevlana Rumi S02E07")!!.single().second)
    }

    @Test
    fun `nothing usable gives nothing`() {
        assertNull(ManualSeries.titles(emptyList(), "Show"))
        assertNull(ManualSeries.titles(listOf(video()), ""))
        assertNull(ManualSeries.titles(List(5) { video() }, "Show S01E998"))
    }

    @Test
    fun `made episodes group into one show, with the chat's own episodes of it`() {
        val own = (1..3).map { video("Mevlana.Rumi.S01E0$it.mkv") }
        val odd = listOf(video("a1.mp4"), video("a2.mp4"))
        val corrections = ManualSeries.titles(odd, "Mevlana Rumi S01E04")!!
            .associate { (item, title) -> item.id to VideoCorrection(title = title) }
        val shows = SeriesShelf.arrange(own + odd, corrections).filterIsInstance<ShelfEntry.Show>()
        assertEquals(1, shows.size)
        assertEquals(5, shows[0].series.episodeCount)
    }
}
