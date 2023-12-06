package day2

import java.io.File

class Day2 {
    private val contents = File("inputs/day2.input.txt").readLines()
    private val redRegex = "(\\d+) red".toRegex()
    private val greenRegex = "(\\d+) green".toRegex()
    private val blueRegex = "(\\d+) blue".toRegex()

    fun determineValidGames(): Int {
        var sum = 0

        contents.forEachIndexed { index, line ->
            val isValidRed = redRegex.findAll(line).none { it.groupValues[1].toInt() > 12 }
            val isValidGreen = greenRegex.findAll(line).none { it.groupValues[1].toInt() > 13 }
            val isValidBlue = blueRegex.findAll(line).none { it.groupValues[1].toInt() > 14 }

            if (isValidRed && isValidGreen && isValidBlue) {
                sum += index + 1
            }
        }

        return sum
    }

    fun determinePowerOfCubes(): Long {
        var sum: Long  = 0

        contents.forEach { line ->
            val highestRed = redRegex.findAll(line).map { it.groupValues[1].toInt() }.sortedDescending().first()
            val highestGreen = greenRegex.findAll(line).map { it.groupValues[1].toInt() }.sortedDescending().first()
            val highestBlue = blueRegex.findAll(line).map { it.groupValues[1].toInt() }.sortedDescending().first()

            sum += highestRed * highestGreen * highestBlue
        }

        return sum
    }
}