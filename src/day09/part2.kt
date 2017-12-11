package day09

import java.io.File

// This file is doing both part 1 and 2. I merged them on purpose to experiment with the data class to return two values

fun main(args: Array<String>) {

    val samples = hashMapOf(
            "<>" to 0,
            "<random characters>" to 17,
            "<<<<>" to 3,
            "<{!>}>" to 2,
            "<!!>" to 0,
            "<!!!>>" to 0,
            "<{o\"i!a,<{i<a>" to 10
    )
    samples.forEach {
        println("sample expected ${it.value} gives ${countGarbage(it.key)} for ${it.key}" )
    }

    val input = File("day09/input.txt")
            .bufferedReader()
            .readLine()!!
    println("input items gives: " + countGarbage(input))
}

// This is cheating a bit because it doesn't count levels anymore
fun countGarbage(input: String): Int {
    var garbageChars = 0
    var ignoreGarbage = false
    var ignoreChar = false
    input.forEach { char ->
        if (ignoreChar) {
            ignoreChar = false
        } else {
            when (char) {
                '!' -> ignoreChar = true
                '<' -> {
                    if (ignoreGarbage)
                        garbageChars ++
                    else
                        ignoreGarbage = true
                }
                '>' -> ignoreGarbage = false
                else -> if (ignoreGarbage) garbageChars ++
            }
        }
    }
    return garbageChars
}
