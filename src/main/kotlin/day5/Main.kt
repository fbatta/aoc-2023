package day5

fun main(args: Array<String>) {
    val day5 = Day5()

    println("Lowest location: ${day5.mapSeedToLocations(day5.readSeeds())}")
    println("Lowest location as ranges: ${day5.mapSeedToLocations(day5.readSeedsAsRanges())}")
}