package com.ruman.hnews
import org.jsoup.Jsoup
import java.util.*

const val HNEWS_BASE_URL = "https://news.ycombinator.com"

data class Headline (
    val id: Int = 0,
    val index: Int = 0,
    val title: String = "",
    val link: String = ""
)

data class Comment(
    val text: String = "",
    val level: Int = 0
)

fun main() {
    println("All the news that's un-fit to print")
    val headlines = fetch()
    headlines.forEach { println(formatLine(it.index, it.title, it.link)) }
    var selected: Int
    val rdr = Scanner(System.`in`)
    do {
        print("Select a story to see comments (number from 0 to ${headlines.size - 1}): ")
        if (!rdr.hasNextInt()) {
            rdr.next()
            selected = -1
            continue
        }
        selected = rdr.nextInt()
    } while (selected >= headlines.size || selected < 0)
    fetchComments(headlines[selected].id).forEach {
        println("${lineMarkerForCommentLevel(it.level)}\n${wordWrap(it.text, 80)}")
    }
}

fun lineMarkerForCommentLevel(level: Int): String {
    var out = ""
    for (i in 1 .. level) {
        out += "-"
    }
    return "$out|"
}

fun formatLine(index: Int, content: String, link: String) : String {
    return "$index. $content"
}

fun wordWrap(text: String, maxWidth: Int): String {
    val source = text.split(" ").listIterator()
    var budget = maxWidth
    var sink = ""
    while (source.hasNext()) {
        val wordToPlace = "${source.next()} " // Note the trailing space
        if (budget - wordToPlace.length >= 0) {
            sink += wordToPlace
            budget -= wordToPlace.length
        } else {
            sink += "\n$wordToPlace"
            budget = maxWidth - wordToPlace.length
        }
    }
    return sink
}

fun fetch(): List<Headline> {
    var count = 0
    val body = Jsoup.connect(HNEWS_BASE_URL).get().body()
    val stories = body.getElementsByAttributeValue("class", "athing")
    return stories.map {
        val id = it.attr("id").toInt()
        val storylink = it.getElementsByAttributeValue("class", "storylink").first()
        val title = storylink.text()
        val href = storylink.attr("href")
        Headline(id, count++, title, href)
    }
}

fun fetchComments(id: Int): List<Comment> {
    val comTableRows = Jsoup.connect("$HNEWS_BASE_URL/item?id=$id")
        .get()
        .body()
        .getElementsByClass("comtr")
    return comTableRows.map {
        val nesting = it.getElementsByTag("img").first().attr("width").toInt(10) / 10
        val content = it.getElementsByClass("commtext").text()
        Comment(content, nesting)
    }
}

