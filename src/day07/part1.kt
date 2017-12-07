package day07

import java.io.File

fun main(args: Array<String>) {
    val sampleNodes = loadNodes("day07/sample.txt")
    println("sample root should be tknk: " + findRoot(sampleNodes))

    val input = loadNodes("day07/input.txt")
    println("input has root: " + findRoot(input))
}

data class Node(val name: String, val weight: Int, val subNodes: List<String>)

/* We build two lists: root candidates and nodes that can't be root. When a node is found in subnodes, it is
   transferred to the notRoots. Given that all its subnodes are already in the notRoots, this should in the end
   leave us with only one element in the rootCandidates.
 */
fun findRoot(nodes: List<Node>): Node {
    val rootCandidates: MutableMap<String, Node> = mutableMapOf()
    val notRoots: MutableMap<String, Int> = mutableMapOf()
    nodes.forEach { node ->
        if (notRoots.containsKey(node.name)) {
            node.subNodes.forEach {
                rootCandidates.remove(it)
                notRoots.put(it, 1)
            }
        } else {
            rootCandidates.put(node.name, node)
            node.subNodes.forEach {
                rootCandidates.remove(it)
                notRoots.put(it, 1)
            }
        }
    }
    if (rootCandidates.size != 1) {
        throw RuntimeException("couldn't find the root")
    }
    return rootCandidates.entries.first().value
}

fun loadNodes(fileName: String): List<Node> {
    val reader = File(fileName).bufferedReader()
    return reader.readLines().map {
        val values = "([a-z]*) [(](.*)[)]( -> (.*))?".toRegex().matchEntire(it)!!.groupValues
        // "".split(", ") will give [""] instead of []
        Node(values[1], values[2].toInt(), if (values[4].isNotEmpty()) values[4].split(", ") else listOf())
    }
}