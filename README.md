# HNews
A text-only browser for *headlines and comments* on hackernews. Also an excuse to try out
some kotlin.

## Capabilities
Prints out each of the headlines on the home page, along with the corresponding
URL. You have the option of selecting a headline by its index and seeing
a printout of the associated comments.

## Build
Project is gradle-based, so you can accomplish a build using the included gradle
build scripts. You'll need a recent kotlin development environment.

After cloning the repo, run `./gradlew assemble` to produce an executable
JAR file, located at `./build/libs/hnews-<version>.jar`. Run the JAR
using `java -jar hnews-<version>.jar` and enjoy!

### Dependencies
- Java 8
- JSoup, a fantastic HTML parser for Java
- Kotlin, a cool language that targets the JVM (among other things)
