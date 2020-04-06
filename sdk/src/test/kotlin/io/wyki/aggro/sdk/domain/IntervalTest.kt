package io.wyki.aggro.sdk.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IntervalTest {
    @Test
    fun toString_complete() {
        val interval = Interval(1, 2, 3, 4)
        assertEquals("4M3d2h1m", interval.toString())
    }

    @Test
    fun toString_empty() {
        val interval = Interval()
        assertEquals("0m", interval.toString())
    }

    @Test
    fun toString_month() {
        val interval = Interval(_month = 4)
        assertEquals("4M", interval.toString())
    }

    @Test
    fun toString_day() {
        val interval = Interval(_day = 3)
        assertEquals("3d", interval.toString())
    }

    @Test
    fun toString_hour() {
        val interval = Interval(_hour = 2)
        assertEquals("2h", interval.toString())
    }

    @Test
    fun toString_minute() {
        val interval = Interval(_minute = 1)
        assertEquals("1m", interval.toString())
    }

    @Test
    fun toString_overflow() {
        val interval = Interval(234, 28, 45, 12)
        assertEquals("13M16d7h54m", interval.toString())
    }

    @Test
    fun from_complete() {
        val interval = Interval.from("4M1m3d2h")
        assertEquals("4M3d2h1m", interval.toString())
    }

    @Test
    fun from_overflow() {
        val interval = Interval.from("12M234m45d28h")
        assertEquals("13M16d7h54m", interval.toString())
    }

    @Test
    fun from_with_spaces() {
        val interval = Interval.from("12M 234m 45d 28h")
        assertEquals("13M16d7h54m", interval.toString())
    }

    @Test
    fun from_random_input() {
        val interval = Interval.from("Lorem ipsum12Mdolorsitamet234mindo lo tem45dcarne vag ni so28h")
        assertEquals("13M16d7h54m", interval.toString())
    }

    @Test
    fun from_huge_string() {
        val interval = Interval.from("1m2h3d4M12M234m45d28h")
        assertEquals("4M3d2h1m", interval.toString())
    }
}
