package day05

import java.io.File

fun main(args: Array<String>) {

    val sample = "0 3 0 1 -3"
    val sampleInstructions = sample.split(' ').map { it.toInt() }.toIntArray()
    println("sample $sample gives: " + execute1(sampleInstructions))

    val input = File("day05/input.txt").bufferedReader().readLines().map { it.toInt() }.toIntArray()
    println("input ${input.count()} items gives: " + execute1(input))
}

fun execute1(instructions: IntArray): Int {
    var steps = 0
    var eip = 0 // In a x86 CPU, EIP is the pointer to the current instruction

    while (eip in instructions.indices) {
        val newEip = eip + instructions[eip]
        instructions[eip]++
        eip=newEip
        steps++
    }

    return steps
}
