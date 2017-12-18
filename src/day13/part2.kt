package day13

fun main(args: Array<String>) {
    val sampleFirewall = loadSpreadsheet("day13/sample.txt")
    println("Sample: " + findPassingDelay(sampleFirewall))

    val inputFirewall = loadSpreadsheet("day13/input.txt")
    println("Part2: " + findPassingDelay(inputFirewall))
}

fun findPassingDelay(firewall: MutableMap<Int, Int>) : Int {
    return generateSequence(0, {it+1}).first { canPass(firewall, it) }
}

fun canPass(firewall: MutableMap<Int, Int>, delay: Int) : Boolean {
    val max = firewall.keys.max()!!
    return (delay..(delay + max)).none { firewall.containsKey(it-delay) && positionAtPico(firewall[it-delay]!!, it) == 0 }
}
