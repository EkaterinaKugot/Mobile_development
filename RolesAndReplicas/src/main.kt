import java.io.File

fun main() {
    val file = File("src/roles.txt")
    val lines = file.readLines()

    val roles = lines.dropWhile { it != "roles:" }
        .takeWhile { it != "textLines:" }
        .filter { it.isNotBlank() }
        .map { it.trim() }

    val textLines = lines.dropWhile { it != "textLines:" }
        .filter { it.isNotBlank() }
        .map { it.trim() }

    val roleReplies = mutableMapOf<String, MutableList<Pair<Int, String>>>()
    var currentRole = ""

    for ((index, line) in textLines.withIndex()) {
        val role = roles.firstOrNull { line.startsWith(it) }
        if (role != null) {
            currentRole = role
            roleReplies.getOrPut(currentRole) { mutableListOf() }.add(index to line.substringAfter(":").trim())
        } else {
            roleReplies.getOrPut(currentRole) { mutableListOf() }.add(index to line.trim())
        }
    }

    val result = buildString {
        for (role in roles) {
            val replies = roleReplies[role] ?: continue
            append("$role:\n")
            replies.forEach { (index, reply) ->
                append("${index}) $reply\n")
            }
            append("\n")
        }
    }

    println(result)
}