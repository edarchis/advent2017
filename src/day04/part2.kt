package day04

import java.io.File

fun main(args: Array<String>) {

    println("abcde fghij should be valid: " + isValidPassPhrase2("abcde fghij"))
    println("abcde xyz ecdab should NOT be valid: " + isValidPassPhrase2("abcde xyz ecdab"))

    val passPhraseValidity = File("day04/input.txt").bufferedReader()
            .readLines().associateBy({ it }, { isValidPassPhrase2(it) })
    println("Total passphrases: ${passPhraseValidity.size}")
    println("Valid passphrases: " + passPhraseValidity.count { it.value })
}

fun isValidPassPhrase2(passPhrase: String): Boolean {
    val words = passPhrase.split(" ")
    val map = words.associateBy({ it.toSortedSet() }, { 1 })
    return map.size == words.size
}
