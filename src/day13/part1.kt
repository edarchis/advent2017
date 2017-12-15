package day13

import java.io.File

fun main(args: Array<String>) {
    val sampleFirewall = loadSpreadsheet("day13/sample.txt")
    println("Sample: " + computePrice(sampleFirewall))

    val inputFirewall = loadSpreadsheet("day13/input.txt")
    println("Part1: " + computePrice(inputFirewall))
}

fun computePrice(firewall: MutableMap<Int, Int>) {
    val max = firewall.keys.max()!!
    (0..max)
            .filter { firewall.containsKey(it) && positionAtPico(firewall[it]!!, it) == 0 }
            .sumBy { it * firewall[it]!! }
}

// Computes the position of the scanner at a given picosecond. This only works for size > 1, ignored for this exercise
fun positionAtPico(size: Int, pico: Int): Int {
    val computeSize = size - 1
    val pos = pico % (computeSize * 2)
    return if (pos >= size)
        computeSize - (pos % computeSize)
    else
        pos
}

fun loadSpreadsheet(fileName: String): MutableMap<Int, Int> {
    val reader = File(fileName).bufferedReader()
    val spreadsheet: MutableMap<Int, Int> = mutableMapOf()
    reader.useLines { lines ->
        lines.forEach {
            val item = it.split(": ")
                    .map { it.toInt() }
            spreadsheet[item[0]] = item[1]
        }
    }
    return spreadsheet
}
