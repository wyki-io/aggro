package io.wyki.aggro.sdk.domain

class Interval(
    _minute: Int = 0,
    _hour: Int = 0,
    _day: Int = 0,
    _month: Int = 0
) {
    var minute = _minute
        private set
    var hour = _hour
        private set
    var day = _day
        private set
    var month = _month
        private set
    init {
        // Minutes
        var ret = minute / 60
        minute %= 60

        // Hours
        hour += ret
        ret = hour / 24
        hour %= 24

        // Days
        day += ret
        ret = day / 30
        day %= 30

        // Month
        month += ret
    }

    override fun toString(): String {
        var ret = if (month == 0) "" else "${month}M"
        ret += if (day == 0) "" else "${day}d"
        ret += if (hour == 0) "" else "${hour}h"
        if (ret.isEmpty() || minute != 0) {
            ret += "${minute}m"
        }
        return ret
    }

    companion object {
        fun from(input: String): Interval {
            return Interval(
                _minute = Regex("[0-9]+m").find(input)?.value?.dropLast(1)?.toInt() ?: 0,
                _hour = Regex("[0-9]+h").find(input)?.value?.dropLast(1)?.toInt() ?: 0,
                _day = Regex("[0-9]+d").find(input)?.value?.dropLast(1)?.toInt() ?: 0,
                _month = Regex("[0-9]+M").find(input)?.value?.dropLast(1)?.toInt() ?: 0
            )
        }
    }
}
