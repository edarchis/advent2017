package day04

import java.io.File

fun main(args: Array<String>) {

    println("abcde fghij should be valid: " + isValidPassPhrase2("abcde fghij"))
    println("abcde xyz ecdab should NOT be valid: " + isValidPassPhrase2("abcde xyz ecdab"))
    println("abb xyz aab SHOULD be valid if you count letters properly (not a Set): "
            + isValidPassPhrase2("abb xyz aab"))

    val passPhraseValidity = File("day04/input.txt").bufferedReader()
            .readLines().associateBy({ it }, { isValidPassPhrase2(it) })
    println("Total passphrases: ${passPhraseValidity.size}")
    println("Valid passphrases: " + passPhraseValidity.count { it.value })
}

fun isValidPassPhrase2(passPhrase: String): Boolean {
    val words = passPhrase.split(" ")
    val map = words.associateBy({ it.toList().sorted() }, { 1 })
    return map.size == words.size
}
