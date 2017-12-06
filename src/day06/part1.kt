package day06

import java.io.File

// This file is doing both part 1 and 2. I merged them on purpose to experiment with the data class to return two values

fun main(args: Array<String>) {

    val sample = "0 2 7 0"
    val sampleBanks = sample.split(' ').map { it.toInt() }.toIntArray()
    println("sample $sample gives: " + relocate(sampleBanks))

    val input = File("day06/input.txt")
            .bufferedReader()
            .readLine()
            .split("\t".toRegex())
            .map { it.toInt() }
            .toIntArray()
    println("input ${input.count()} items gives: " + relocate(input))
}

data class Loop(val steps: Int, val cycles: Int)
fun relocate(banks: IntArray): Loop {
    var steps = 0
    var banksTrail: MutableList<List<Int>> = mutableListOf()

    // I chose the do while approach while my colleague Youri preferred the break to avoid adding a value at the
    // beginning and comparing to an altered version in the condition. After doing some Groovy, I'm too
    // happy to use a do while :)
    do {
        banksTrail.add(banks.toList())
        val maxBankIndex = getMaxBankIndex(banks)
        val valueToRelocate = banks[maxBankIndex]
        // println("maxBankIndex: $maxBankIndex with value $valueToRelocate")
        banks[maxBankIndex] = 0
        for (i in 1 .. valueToRelocate) {
            banks[(maxBankIndex+i)%banks.size]++
        }
        //println("step $step banks: ${banks.joinToString()}")
        steps++
    } while (banks.toList() !in banksTrail)

    return Loop(steps, steps-banksTrail.indexOf(banks.toList()))
}

fun getMaxBankIndex(banks: IntArray) : Int {
    // This is not the easiest method but it allowed me to try the withIndex()
    return banks.withIndex().maxWith (Comparator { x, y ->
        when {
            x.value == y.value -> y.index - x.index
            else -> x.value - y.value
        }
    })!!.index
}