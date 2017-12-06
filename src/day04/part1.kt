package day04

import java.io.File

fun main(args: Array<String>) {

    println("aa bb cc dd ee should be valid: " + isValidPassPhrase("aa bb cc dd ee"))
    println("aa bb cc dd aa should NOT be valid: " + isValidPassPhrase("aa bb cc dd aa"))

    val passPhraseValidity = File("day04/input.txt").bufferedReader()
            .readLines().associateBy({ it }, { isValidPassPhrase(it) })
    println("Total passphrases: ${passPhraseValidity.size}")
    println("Valid passphrases: " + passPhraseValidity.count { it.value })
}

fun isValidPassPhrase(passPhrase: String): Boolean {
    val words = passPhrase.split(" ")
    val map = words.associateBy({ it }, { 1 })
    return map.size == words.size
}
