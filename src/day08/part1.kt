package day08

import java.io.File

fun main(args: Array<String>) {
    val sampleNodes = loadProgram("day08/sample.txt")
    println("sample biggest register (should be a=1): " + execute(sampleNodes))

    val input = loadProgram("day08/input.txt")
    println("input has biggest register: " + execute(input))
}

data class Instruction(
        val actionReg: String,
        val action: String,
        val actionValue: Int,
        val conditionReg: String,
        val condition: String,
        val conditionValue: Int)

val operator = hashMapOf(
        ">" to { a: Int, b: Int -> a > b },
        ">=" to { a: Int, b: Int -> a >= b },
        "<" to { a: Int, b: Int -> a < b },
        "<=" to { a: Int, b: Int -> a <= b },
        "==" to { a: Int, b: Int -> a == b },
        "!=" to { a: Int, b: Int -> a != b }
        )

val action = hashMapOf(
        "inc" to { a: Int, b : Int -> a + b },
        "dec" to { a: Int, b : Int -> a - b }
)

fun execute(program: List<Instruction>): String {
    val registers = mutableMapOf<String, Int>()
    program.forEach {
        // This could be one-lined but it would become really hard to read & understand
        val registerValue = registers.getOrDefault(it.conditionReg, 0)
        if (operator[it.condition]!!(registerValue, it.conditionValue)) {
            registers[it.actionReg] =
                action[it.action]!!(registers.getOrDefault(it.actionReg, 0), it.actionValue)
        }
    }
    return registers.maxBy { it.value }.toString()
}

fun loadProgram(fileName: String): List<Instruction> {
    val reader = File(fileName).bufferedReader()
    return reader.readLines().map {
        val values = "([a-z]*) (inc|dec) (-?\\d*) if ([a-z]*) ([<>=!]*) (-?\\d*)".toRegex().matchEntire(it)!!.groupValues
        Instruction(values[1], values[2], values[3].toInt(), values[4], values[5], values[6].toInt())
    }
}