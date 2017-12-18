package day18

import java.io.File

fun main(args: Array<String>) {
    val sampleProgram = loadProgram("day18/sample.txt")
    println("sample latest rcv value (should be 4): " + execute(sampleProgram))

    val input = loadProgram("day18/input.txt")
    println("input latest rcv: " + execute(input))
}

data class Instruction(
        val operation: String,
        val operand1: String,
        val operand2: String)

data class CPU(
        var registers: MutableMap<String, Int> = mutableMapOf(),
        var lastSound: Int = 0,
        var end: Boolean = false
)

val action = hashMapOf(
        "set" to { cpu: CPU, inst: Instruction ->
            cpu.registers[inst.operand1] = getValueFromExpression(inst.operand2, cpu.registers)
            1
        },
        "add" to { cpu: CPU, inst: Instruction ->
            val value = getValueFromExpression(inst.operand1, cpu.registers)
            cpu.registers[inst.operand1] = value + getValueFromExpression(inst.operand2, cpu.registers)
            1
        },
        "mul" to { cpu: CPU, inst: Instruction ->
            val value = getValueFromExpression(inst.operand1, cpu.registers)
            cpu.registers[inst.operand1] = value * getValueFromExpression(inst.operand2, cpu.registers)
            1
        },
        "mod" to { cpu: CPU, inst: Instruction ->
            val value = getValueFromExpression(inst.operand1, cpu.registers)
            cpu.registers[inst.operand1] = value % getValueFromExpression(inst.operand2, cpu.registers)
            1
        },
        "snd" to { cpu: CPU, inst: Instruction ->
            cpu.lastSound = getValueFromExpression(inst.operand1, cpu.registers)
            1
        },
        "rcv" to { cpu: CPU, inst: Instruction ->
            if (getValueFromExpression(inst.operand1, cpu.registers) != 0) {
                cpu.registers[inst.operand1] = cpu.lastSound
                cpu.end = true
            }
            1
        },
        "jgz" to { cpu: CPU, inst: Instruction ->
            if (getValueFromExpression(inst.operand1, cpu.registers) > 0) {
                getValueFromExpression(inst.operand2, cpu.registers)
            } else {
                1
            }
        }
)

fun getValueFromExpression (expression: String, registers: MutableMap<String, Int>): Int {
    return expression.toIntOrNull() ?: registers.getOrDefault(expression, 0)
}

fun execute(program: List<Instruction>): Int {
    val cpu = CPU()
    var eip = 0

    while (!cpu.end) {
        val instruction = program[eip]
        val increment = action[instruction.operation]!!(cpu, instruction)
        eip += increment
    }
    return cpu.lastSound
}

fun loadProgram(fileName: String): List<Instruction> {
    val reader = File(fileName).bufferedReader()
    return reader.readLines().map {
        try {
            val values = "([a-z]*) (-?[a-z0-9]*) ?(-?[a-z0-9]*)?".toRegex().matchEntire(it)!!.groupValues
            Instruction(values[1], values[2], values[3])
        } catch (e: Exception) {
            println("could not parse: " + it)
            throw e
        }

    }
}
