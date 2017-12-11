package day08

import java.io.File

fun main(args: Array<String>) {
    val sampleNodes = loadProgram("day08/sample.txt")
    println("sample biggest register (should be a=1): " + execute2(sampleNodes))

    val input = loadProgram("day08/input.txt")
    println("input has biggest register: " + execute2(input))
}

fun execute2(program: List<Instruction>): String {
    val registers = mutableMapOf<String, Int>()
    var maxReg = "none"
    var maxVal = Int.MIN_VALUE
    program.forEach {
        // This could be one-lined but it would become really hard to read & understand
        val registerValue = registers.getOrDefault(it.conditionReg, 0)
        if (operator[it.condition]!!(registerValue, it.conditionValue)) {
            registers[it.actionReg] =
                action[it.action]!!(registers.getOrDefault(it.actionReg, 0), it.actionValue)
            if (registers[it.actionReg]!! > maxVal) {
                maxVal = registers[it.actionReg]!!
                maxReg = it.actionReg
            }
        }
    }
    return "$maxReg=$maxVal"
}
