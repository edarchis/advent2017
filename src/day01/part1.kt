package day01

import java.io.File

fun main(args: Array<String>) {
    println("1212 -> " + day01b("1212"))
    println("1221 -> " + day01b("1221"))
    println("1234 -> " + day01b("1234"))
    println("91212129 -> " + day01b("91212129"))

    val input: String = File("day01/input.txt").bufferedReader().use { it.readText() }

    println(day01a(input))
}

fun day01a(input: String) : Int {
    return input.filterIndexed { i, ch -> ch == input[(i + 1) % input.length] }
            .sumBy { Character.getNumericValue(it) }
}