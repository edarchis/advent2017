package day07

fun main(args: Array<String>) {
    val sampleNodes = loadNodes("day07/sample.txt")
    println("sample unbalanced node should be ugml: " + findUnbalancedNode(sampleNodes))

    val input = loadNodes("day07/input.txt")
    println("input has unbalanced node: " + findUnbalancedNode(input))
}

class AnswerFoundException(override var message:String): Exception()

fun findUnbalancedNode(nodes: List<Node>): String {
    val root = findRoot(nodes)
    return try {
        getNodeWeight(root, nodes)
        "No answer found, this looks balanced"
    } catch (answerFound : AnswerFoundException) {
        answerFound.message
    }
}

fun getNodeWeight(node: Node, nodes: List<Node>): Int {
    if (node.subNodes.isEmpty()) {
        return node.weight
    }
    val subNodesWeights = node.subNodes.associateBy({ it }, { subNode ->
        getNodeWeight(nodes.find { subNode == it.name }!!, nodes)
    })

    val counts = subNodesWeights.values.groupingBy { it }.eachCount()
    if (counts.size > 1) {
        val unbalanced = counts.minBy { it.value }!!.key
        val balanced = counts.maxBy { it.value }!!.key
        val unbalancedNode = nodes.find {
            it.name == subNodesWeights.filter { it.value == unbalanced }.keys.first()
        }!!

        throw AnswerFoundException("OFF-BALANCE FOUND: $unbalanced instead of $balanced on node ${unbalancedNode.name}" +
            "Its weight should be " + (unbalancedNode.weight + balanced - unbalanced))
    }
    return node.weight + subNodesWeights.values.sum()
}