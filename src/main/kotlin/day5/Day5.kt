package day5

import java.io.File
import java.util.SortedMap

typealias MyMap = SortedMap<Long, Pair<Long, Long>>

class Day5 {

    private val contents = File("inputs/day5.input.txt").readLines()
    private val seedToSoilMap: MyMap = populateMap(3, 44)
    private val soilToFertilizerMap: MyMap = populateMap(47, 95)
    private val fertilizerToWaterMap: MyMap = populateMap(98, 129)
    private val waterToLightMap: MyMap = populateMap(132, 178)
    private val lightToTemperatureMap: MyMap = populateMap(181, 201)
    private val temperatureToHumidityMap: MyMap = populateMap(204, 240)
    private val humidityToLocationMap: MyMap = populateMap(243, 278)

    private fun parseLines(start: Int, end: Int) = contents.slice(IntRange(start, end)).map { line ->
            line.split(' ').map { it.toLong() }
        }

    private fun populateMap(start: Int, end: Int): MyMap {
        val map: MutableMap<Long, Pair<Long, Long>> = mutableMapOf()
        val lines = parseLines(start, end)

        for (i in lines.indices) {
            val range = lines[i][2]
            val sourceStart = lines[i][1]
            val destinationStart = lines[i][0]

            map[sourceStart] = Pair(range, destinationStart)
        }

        return map.toSortedMap()
    }

    fun readSeeds() = contents[0].substring(7).split(' ').map { it.toLong() }

    fun readSeedsAsRanges(): Map<Long, Long> {
        val map: MutableMap<Long, Long> = mutableMapOf()

        val sources = readSeeds().filterIndexed { index, _ -> index.mod(2) == 0 }
        val ranges  = readSeeds().filterIndexed { index, _ -> index.mod(2) != 0 }

        for (i in sources.indices) {
            map[sources[i]] = ranges[i]
        }

        return map.toMap()
    }

    private fun getDestination(source: Long, map: MyMap): Long {
        val candidateKeyList = map.keys.toList().filter { it <= source }
        if(candidateKeyList.isEmpty()) {
            return source
        }
        val candidateKey = candidateKeyList.last()
        val candidate = map[candidateKey] ?: return source

        val difference = source - candidateKey
        if(difference > candidate.first) return source

        return candidate.second + difference
    }

    private fun seedToLocation(seed: Long): Long {
        val soil = getDestination(seed, seedToSoilMap)
        val fertilizer = getDestination(soil, soilToFertilizerMap)
        val water = getDestination(fertilizer, fertilizerToWaterMap)
        val light = getDestination(water, waterToLightMap)
        val temperature = getDestination(light, lightToTemperatureMap)
        val humidity = getDestination(temperature, temperatureToHumidityMap)

        return getDestination(humidity, humidityToLocationMap)
    }

    private fun mapSeedToLocations(seeds: List<Long>?, seedRanges: Map<Long, Long>?): Long {
        var lowestLocation: Long = Long.MAX_VALUE

        seeds?.forEach { seed ->
            val location = seedToLocation(seed)
            if(location < lowestLocation) lowestLocation = location
        }

        seedRanges?.forEach { (source, range) ->
            println("Source seed: $source")
            for (i in 0..<range) {
                val location = seedToLocation(source + i)
                if(location < lowestLocation) lowestLocation = location
            }
        }

        return lowestLocation
    }

    fun mapSeedToLocations(seeds: List<Long>) = mapSeedToLocations(seeds, null)
    fun mapSeedToLocations(seedRanges: Map<Long, Long>) = mapSeedToLocations(null, seedRanges)
}