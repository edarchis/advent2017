package day02

import java.io.File

fun main(args: Array<String>) {

    val sampleSheet = loadSpreadsheet("day02/sample.txt")
    println("Sample: " + computeCrc(sampleSheet))

    val inputSheet = loadSpreadsheet("day02/input.txt")
    println("Part1: " + computeCrc(inputSheet))
}

fun computeCrc(sheet: MutableList<List<Int>>) : Int {
    return sheet.map { line ->
        // Suboptimal approach as we scan the content multiple times
        line.max()!! - line.min()!!
    }.sum()
}

fun loadSpreadsheet(fileName: String): MutableList<List<Int>> {
    val reader = File(fileName).bufferedReader()
    val spreadsheet: MutableList<List<Int>> = mutableListOf()
    reader.useLines { lines ->
        lines.forEach {
            spreadsheet.add(
                    it.split("\\s".toRegex())
                            .map { it.toInt() }
            )
        }
    }
    return spreadsheet
}