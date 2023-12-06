package day6

class Day6 {
    private val timesDistancesMap: Map<Long, Long> = mapOf(Pair(46, 214), Pair(80, 1177), Pair(78, 1402), Pair(66, 1024))

    private fun getRaceWinningCombinations(time: Long, distance: Long): Long {
        var combinations: Long = 0
        val minimumDistance = distance + 1

        for (i in 0..time) {
            if(i * (time - i) >= minimumDistance) combinations++
        }

        return combinations
    }

    fun getMarginOfError(): Long {
        var marginOfError: Long = 1

        timesDistancesMap.forEach { (time, distance) ->  marginOfError *= getRaceWinningCombinations(time, distance)}

        return marginOfError
    }

    fun getMarginOfErrorForSingleRace(): Long {
        val time = timesDistancesMap.keys.joinToString("").toLong()
        val distance = timesDistancesMap.values.joinToString("").toLong()

        return getRaceWinningCombinations(time, distance)
    }
}