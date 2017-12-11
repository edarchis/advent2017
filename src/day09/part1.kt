package day09

import java.io.File

// This file is doing both part 1 and 2. I merged them on purpose to experiment with the data class to return two values

fun main(args: Array<String>) {

    val samples = hashMapOf(
            "{}" to 1,
            "{{{}}}" to 6,
            "{{},{}}" to 5,
            "{{{},{},{{}}}}" to 16,
            "{<a>,<a>,<a>,<a>}" to 1,
            "{{<ab>},{<ab>},{<ab>},{<ab>}}" to 9,
            "{{<!!>},{<!!>},{<!!>},{<!!>}}" to 9,
            "{{<a!>},{<a!>},{<a!>},{<ab>}}" to 3
    )
    samples.forEach {
        println("sample expected ${it.value} gives ${score(it.key)} for ${it.key}" )
    }

    val input = File("day09/input.txt")
            .bufferedReader()
            .readLine()!!
    println("input items gives: " + score(input))
}

fun score(input: String): Int {
    var level = 0
    var points = 0
    var ignoreGarbage = false
    var ignoreChar = false
    input.forEach { char ->
        if (ignoreChar) {
            ignoreChar = false
        } else {
            when (char) {
                '{' -> if (!ignoreGarbage) level++
                '}' -> if (!ignoreGarbage) {
                    points += level
                    level--
                }
                '!' -> ignoreChar = true
                '<' -> ignoreGarbage = true
                '>' -> ignoreGarbage = false
            }
        }
    }
    return points
}
