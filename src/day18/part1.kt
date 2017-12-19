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
        var registers: MutableMap<String, Long> = mutableMapOf(),
        var lastSound: Long = 0,
        var end: Boolean = false
)

val action = hashMapOf(
        "set" to { cpu: CPU, inst: Instruction ->
            cpu.registers[inst.operand1] = getValueFromExpression(inst.operand2, cpu.registers)
            1L
        },
        "add" to { cpu: CPU, inst: Instruction ->
            cpu.registers[inst.operand1] = getValueFromExpression(inst.operand1, cpu.registers) +
                    getValueFromExpression(inst.operand2, cpu.registers)
            1L
        },
        "mul" to { cpu: CPU, inst: Instruction ->
            cpu.registers[inst.operand1] = getValueFromExpression(inst.operand1, cpu.registers) *
                    getValueFromExpression(inst.operand2, cpu.registers)
            1L
        },
        "mod" to { cpu: CPU, inst: Instruction ->
            cpu.registers[inst.operand1] = getValueFromExpression(inst.operand1, cpu.registers) %
                    getValueFromExpression(inst.operand2, cpu.registers)
            1L
        },
        "snd" to { cpu: CPU, inst: Instruction ->
            cpu.lastSound = getValueFromExpression(inst.operand1, cpu.registers)
            1L
        },
        "rcv" to { cpu: CPU, inst: Instruction ->
            if (getValueFromExpression(inst.operand1, cpu.registers) != 0L) {
                cpu.registers[inst.operand1] = cpu.lastSound
                cpu.end = true
            }
            1L
        },
        "jgz" to { cpu: CPU, inst: Instruction ->
            if (getValueFromExpression(inst.operand1, cpu.registers) > 0L) {
                getValueFromExpression(inst.operand2, cpu.registers)
            } else {
                1L
            }
        }
)

fun getValueFromExpression(expression: String, registers: MutableMap<String, Long>): Long {
    return expression.toLongOrNull() ?: registers.getOrDefault(expression, 0L)
}

fun execute(program: List<Instruction>): Long {
    val cpu = CPU()
    var eip = 0

    while (!cpu.end && eip in program.indices) {
        val instruction = program[eip]
        val increment = action[instruction.operation]!!(cpu, instruction)
        eip += increment.toInt()
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
