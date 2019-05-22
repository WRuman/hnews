import org.jsoup.Jsoup

const val HNEWS_BASE_URL = "https://news.ycombinator.com"

fun main() {
    println("All the news that's un-fit to print")
    fetch()
}

fun fetch() {
    var count = 0
    Jsoup.connect(HNEWS_BASE_URL)
        .get()
        .body()
        .getElementsByAttributeValue("class", "storylink")
        .map { "${++count}. ${it.text()}\n${it.attr("href")}" }
        .forEach(::println)
}

