import kotlinx.coroutines.*

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line: String in input) {
            var firstDigit = -1
            var lastDigit = -1
            for (c: Char in line) {
                if (c.isDigit()) {
                    if (firstDigit == -1) {
                        firstDigit = c.digitToInt()
                        lastDigit = c.digitToInt()
                    } else {
                        lastDigit = c.digitToInt()
                    }
                }
            }

            total += if (firstDigit != -1 && lastDigit != -1) {
                "$firstDigit$lastDigit".toInt()
            } else if (firstDigit != -1) {
                firstDigit
            } else if (lastDigit != -1) {
                lastDigit
            } else {
                0
            }
        }
        return total
    }

    val numValues = mapOf<String, Int>(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    val numLetters = numValues.keys.toList()

    fun numberInString(str: String, reversed: Boolean): Int {
        var cache = ""
        for (c: Char in str.lowercase()) {

            if (c.isDigit()) {
                return c.digitToInt()
            }

            if (c.isLetter()) {
                cache = if (reversed) {
                    "$c$cache"
                } else {
                    "$cache$c"
                }
                for (numLetter in numLetters) {
                    if (cache.contains(numLetter)) {
                        return numValues[numLetter] ?: -1
                    }
                }
            }

        }
        return -1
    }

    fun getFirstAndLast(str: String): Pair<Int, Int> = runBlocking(Dispatchers.IO) {
        val firstNumber = async(Dispatchers.IO) { numberInString(str, false) }
        val secondNumber = async(Dispatchers.IO) { numberInString(str.reversed(), true) }
        return@runBlocking Pair(firstNumber.await(), secondNumber.await())
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val numbers: Pair<Int, Int> = getFirstAndLast(line)
            total += if (numbers.first != -1 && numbers.second != -1) {
                "${numbers.first}${numbers.second}".toInt()
            } else if (numbers.first != -1) {
                numbers.first
            } else if (numbers.second != -1) {
                numbers.second
            } else {
                0
            }
        }
        return total
    }

    /*
    Part 1
     */
    val testInput = readInput("Day01_part1_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01_part1")
    val result = part1(input)
    println("result: $result")

    /*
    Part 2
     */
    val testInput2 = readInput("Day01_part2_test")
    check(part2(testInput2) == 281)

    val input2 = readInput("Day01_part2")
    val result2 = part2(input2)
    println("result 2: $result2")

}
