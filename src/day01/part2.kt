package day01

import java.io.File

fun main(args: Array<String>) {
    println("1212 -> " + day01b("1212"))
    println("1221 -> " + day01b("1221"))
    println("123425 -> " + day01b("123425"))
    println("123123 -> " + day01b("123123"))
    println("12131415 -> " + day01b("12131415"))

    val input: String = File("day01/input.txt").bufferedReader().use { it.readText() }

    println(day01b(input))
}

fun day01b(input: String) : Int {
    val shift = input.length/2
    return input.filterIndexed { i, ch -> ch == input[(i + shift) % input.length] }
            .sumBy { Character.getNumericValue(it) }
}