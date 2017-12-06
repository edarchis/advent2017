package day06

import java.io.File

fun main(args: Array<String>) {

    val sample = "0 2 7 0"
    val sampleBanks = sample.split(' ').map { it.toInt() }.toIntArray()
    println("sample $sample gives: " + relocate(sampleBanks))

    val input = File("day06/input.txt")
            .bufferedReader()
            .use { it.readText() }
            .split("\\s".toRegex())
            .map { it.toInt() }
            .toIntArray()
    println("input ${input.count()} items gives: " + relocate(input))
}

fun relocate(banks: IntArray): Int {
    var steps = 0
    var banksTrail: MutableList<IntArray> = mutableListOf()

    do {
        banksTrail.add(banks.copyOf())
        val maxBankIndex = getMaxBankIndex(banks)
        val valueToRelocate = banks[maxBankIndex]
        // println("maxBankIndex: $maxBankIndex with value $valueToRelocate")
        banks[maxBankIndex] = 0
        for (i in 1 .. valueToRelocate) {
            banks[(maxBankIndex+i)%banks.size]++
        }
        //println("step $step banks: ${banks.joinToString()}")
        steps++
    } while (banks !in banksTrail)

    return steps
}

fun getMaxBankIndex(banks: IntArray) : Int {
    return banks.withIndex().maxWith (Comparator { x, y ->
        when {
            x.value == y.value -> y.index - x.index
            else -> x.value - y.value
        }
    })!!.index
}