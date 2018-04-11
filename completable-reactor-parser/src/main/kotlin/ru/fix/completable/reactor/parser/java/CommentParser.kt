package ru.fix.completable.reactor.parser.java

class CommentParser {

    fun parseComment(commentText: String): Comment {
        val lines = commentText.split('\n')
                .asSequence()
                .map { it.trim() }
                .map {
                    when {
                        it.startsWith("//") -> it.substring("//".length).trim()
                        it.startsWith("/**") -> it.substring("/**".length).trim()
                        it.startsWith("/*") -> it.substring("/*".length).trim()
                        it.endsWith("*/") -> it.substring(0, it.length - "*/".length).trim()
                        it.startsWith("*") -> it.substring("*".length).trim()
                        else -> it
                    }
                }
                .dropWhile { it.isBlank() }
                .toList()
                .dropLastWhile { it.isBlank() }

        if (lines.isEmpty()) {
            return Comment(null, null)
        }

        val title = lines
                .takeWhile { it.startsWith('#') }
                .map { it.substring(1).trim() }
                .takeIf { it.isNotEmpty() }
                ?.joinToString(separator = "\n")

        val doc = lines.dropWhile { it.startsWith('#') }

                .takeIf { it.isNotEmpty() }
                ?.joinToString(separator = "\n")

        return Comment(title, doc)
    }
}