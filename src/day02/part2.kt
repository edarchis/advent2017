package day02

fun main(args: Array<String>) {

    val sampleSheet = loadSpreadsheet("day02/sample2.txt")
    println("Sample: " + computeCrc2(sampleSheet))

    val inputSheet = loadSpreadsheet("day02/input.txt")
    println("Part2: " + computeCrc2(inputSheet))
}

fun computeCrc2(sheet: MutableList<List<Int>>) : Int {
    return sheet.map { line ->
        for (i in line.indices) {
            for (j in 0 until i) {
                if (line[i] % line[j] == 0) {
                    println("FOUND ${line[i]/line[j]}")
                    return@map line[i]/line[j]
                }
                if (line[j] % line[i] == 0) {
                    println("FOUND ${line[j]/line[i]}")
                    return@map line[j]/line[i]
                }
            }
        }
        println("We didn't find any divisible number, this should not be possible in this exercise")
        throw IllegalStateException()
    }.sum()
}
