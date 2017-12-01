package day01

import java.io.File

fun main(args: Array<String>) {
    val input: String = File("day01/input.txt").bufferedReader().use { it.readText() }

    val output = input.filterIndexed { i, ch -> ch == input[(i + 1) % input.length] }
            .sumBy { Character.getNumericValue(it) }

    println(output)
}
