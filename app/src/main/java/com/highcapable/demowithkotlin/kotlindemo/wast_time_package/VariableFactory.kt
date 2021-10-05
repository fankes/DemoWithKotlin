@file:Suppress(
    "LocalVariableName", "unused", "SENSELESS_COMPARISON", "SameParameterValue", "HasPlatformType",
    "SpellCheckingInspection", "HttpUrlsUsage"
)

package com.highcapable.demowithkotlin.kotlindemo.wast_time_package

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import java.io.Serializable
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.round

const val nothing = ""
const val NAN = 0
const val NOP = -1
const val NOPF = -1f
const val NANL = 0L
const val NANF = 0f
const val NAND = 0.0
const val NOPL = -1L
const val undefInt = -9999
const val undefColor = -999
const val undefined = "_0"
const val emptySpace = " "
const val tabSpace = "　"
const val wrap = "\n"
const val part = "\r"
val NULL get() = null

val Int.asNothing get() = this == NAN

val String.isBlank get() = safeOf(true) { trim() == nothing || trim() == undefined }

val String.isNothing get() = this == nothing

val String.isNotNothing get() = this != nothing

val String.isNotSpace get() = this != emptySpace && this != tabSpace

val Char.isNotSpace get() = string != emptySpace && string != tabSpace

val Char.isTabSpace get() = string == tabSpace

val Int.isZero get() = this == NAN

val Long.isZero get() = this == NANL

val Float.isZero get() = this == NANF

val Int.isUndefInt get() = this == undefInt

val Int.isNop get() = this == NOP

val Long.isNop get() = this == NOPL

inline val Long.isNotZero get() = !isZero

inline val Int.isNotZero get() = !isZero

inline val Int.isUpperZero get() = this > NAN

inline val Int.isUpperOfZero get() = this >= NAN

inline val Int.isLowerZero get() = this < NAN

inline val Int.isLowerOfZero get() = this <= NAN

inline val Float.isNotZero get() = !isZero

inline val Long.isNotNop get() = !isNop

inline val Int.isNotNop get() = !isNop

inline val String.isNotBlank get() = !isBlank

inline val String.isSpace get() = !isNotSpace

inline val Char.isSpace get() = !isNotSpace

inline val Char.isNotTabSpace get() = !isTabSpace

val String.float get() = safeOf(NANF) { toFloat() }

val Number.float get() = safeOf(NANF) { toFloat() }

val Number.double get() = safeOf(NAND) { toDouble() }

val String.int get() = safeOf(NAN) { toInt() }

val Number.int get() = safeOf(NAN) { toInt() }

val Number.round10
    get() = safeOf(int) {
        when {
            int.string.length < 2 -> string
            int.string.endsWith("1") || int.string.endsWith("2") || int.string.endsWith("3") || int.string.endsWith("4") -> int.string.removeLastChar() + "0"
            int.string.endsWith("6") || int.string.endsWith("7") -> int.string.removeLastChar() + "5"
            int.string.endsWith("8") || int.string.endsWith("9") -> int.string.removeLastChar() + "0"
            else -> int.string
        }.int
    }

val Number.intNoFull
    get() = safeOf("1") {
        when (int) {
            0 -> "1"
            100 -> "99"
            else -> int
        }
    }

val Number.intNoMinFull
    get() = safeOf("1") {
        when (int) {
            0 -> "1"
            else -> int
        }
    }

val Number.intNoMaxFull
    get() = safeOf("1") {
        when (int) {
            100 -> "99"
            else -> int
        }
    }

val Any.string get() = safeOf(nothing) { toString() }

val Float.round get() = round(this)

val Float.cast2Point: Float
    get() = safeOf(NANF) {
        DecimalFormat("0.0").apply {
            roundingMode = RoundingMode.DOWN
        }.format(this)
            .replace(",00", nothing)
            .replace(",", ".").float
    }

val Float.cast1Point: Float
    get() = safeOf(NANF) {
        DecimalFormat("0.0").apply {
            roundingMode = RoundingMode.DOWN
        }.format(this)
            .replace(",0", nothing)
            .replace(",", ".").float
    }

val IntRange.random: Int
    get() {
        val max = last
        val min = first
        return Random().nextInt(max - min + 1) + min
    }

val String.noAlphaColor get() = if (length == 9) replace("#FF", "#") else this

val String.color get() = safeOf(Color.TRANSPARENT) { Color.parseColor(this) }

val Int.color
    get() = safeOf("#00000000") {
        val sb = StringBuffer()
        var R = Integer.toHexString(Color.red(this))
        var G = Integer.toHexString(Color.green(this))
        var B = Integer.toHexString(Color.blue(this))
        R = if (R.length == 1) "0$R" else R
        G = if (G.length == 1) "0$G" else G
        B = if (B.length == 1) "0$B" else B
        sb.append("#")
        sb.append(R.upperCase)
        sb.append(G.upperCase)
        sb.append(B.upperCase)
        sb.string
    }

val String.upperCase get() = safeOf(this) { uppercase(Locale.ROOT) }

val String.lowerCase get() = safeOf(this) { lowercase(Locale.ROOT) }

val String.hexToInt get() = safeOf(NAN) { toInt(16) }

val String.oneCountName
    get() = safeOf("N") {
        removeAllExtraAndFullWidthExtra.removeAllNumbers.trim()
            .substring(0, 1).upperCase.let { if (it.isBlank) "N" else it }
    }

val Int.toHex get() = safeOf("0") { Integer.toHexString(this) ?: "0" }

val Int.isLightColor: Boolean
    get() = safeOf(false) {
        val color = this
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        darkness < 0.5
    }

fun Int.isMultiPle(other: Int) = safeOf(false) { this % other == NAN }

fun Int.colorWithAlpha(alpha: Float): Int {
    val a = 255.coerceAtMost(0.coerceAtLeast((alpha * 255).toInt())) shl 24
    val rgb = 0x00ffffff and this
    return a + rgb
}

fun <T> ArrayList<T>.removeDuplicateWithOrder(): ArrayList<T> {
    if (isEmpty()) return ArrayList()
    val set = HashSet<T>()
    val newList = ArrayList<T>()
    val iter = iterator()
    while (iter.hasNext()) {
        val element = iter.next()
        if (set.add(element)) newList.add(element)
    }
    return newList
}

fun String?.whenNoNull(it: (String) -> Unit) {
    if (this != NULL && isNotBlank) it(this)
}

val Number.long get() = safeOf(NANL) { toLong() }

val String.long get() = safeOf(NANL) { toLong() }

val String.autoZero: String get() = if (toInt() < 10) "0$this" else this

val Int.autoZero: String get() = if (this < 10) "0$this" else string

val Int.isUndefColor get() = this == undefColor

inline val Int.isNotUndefColor get() = !isUndefColor

val String.isUndefined get() = this == undefined

inline val String.isNotUndefined get() = !isUndefined

val String.base64: String get() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)

val String.unbase64 get() = safeOf(this) { String(Base64.decode(this, Base64.NO_WRAP)) }

fun String.unBase64(count: Int) =
    when (count) {
        1 -> unbase64
        2 -> unbase64.unbase64
        3 -> unbase64.unbase64.unbase64
        4 -> unbase64.unbase64.unbase64.unbase64
        5 -> unbase64.unbase64.unbase64.unbase64.unbase64
        else -> this
    }

val String.isNumber get() = safeOf(false) { Pattern.compile("[0-9]*").matcher(this).matches() }

val String.onlyHasUpperAndNumber get() = Pattern.compile("^[a-z0-9A-Z]+\$").matcher(this).matches()

val String.url: String get() = safeOf(this) { URLEncoder.encode(this, "utf-8") }

val String.deUrl: String get() = safeOf(this) { URLDecoder.decode(this, "utf-8") }

val String.toUtf8 get() = String(byteInputStream(Charset.forName("UTF-8")).readBytes(), Charset.forName("UTF-8"))

val String.isNotValidJson get() = isNotJson || this == "[]" || this == "{}" || this == "{[]}" || this == "[{}]"

val String.isValidUrl get() = (this != "http://" && this != "https://") && (startsWith("http://") || startsWith("https://"))

inline val String.isNotJson get() = !isJson

inline val Bundle.isNotEmpty get() = !isEmpty

val String.isJson: Boolean
    get() {
        val str = trim()
        return when {
            str.startsWith("{") && str.endsWith("}") -> true
            str.startsWith("[") && str.endsWith("]") -> true
            else -> false
        }
    }

val List<String>.hasDuplicate: Boolean
    get() {
        if (isEmpty()) return false
        val datas = ArrayList<String>()
        forEach { datas.add(it) }
        forEach { e -> datas.forEach { c -> if (c == e) return true } }
        return false
    }

fun emptyArrayList(size: Int) = ArrayList<String>().apply { if (size > NAN) for (i in 1..size) add(nothing) }

fun Context.string(resId: Int) = getString(resId)

fun space(count: Int): String {
    if (count == NAN) return nothing
    var space = nothing
    for (i in 1..count) space += emptySpace
    return space
}

fun randomString(length: Int): String {
    val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val random = Random()
    val sb = StringBuffer()
    for (i in 0 until length) {
        val number = random.nextInt(62)
        sb.append(str[number])
    }
    return sb.toString()
}

fun tabs(count: Int): String {
    if (count == NAN) return nothing
    var space = nothing
    for (i in 1..count) space += tabSpace
    return space
}

fun Bundle.saveStates() = putBoolean("saveStates", true)

fun Bundle.putAny(key: String, value: Any?) {
    if (key.isBlank) return
    when (value) {
        is String -> putString(key, value)
        is Boolean -> putBoolean(key, value)
        is Int -> putInt(key, value)
        is Long -> putLong(key, value)
        is Serializable -> putSerializable(key, value)
        is Parcelable -> putParcelable(key, value)
        is Float -> putFloat(key, value)
        is Char -> putChar(key, value)
        is Byte -> putByte(key, value)
        is Bundle -> putBundle(key, value)
        else -> {
        }
    }
}

fun Intent.putExtraAny(key: String, value: Any?) {
    if (key.isBlank) return
    when (value) {
        is Boolean -> putExtra(key, value)
        is String -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is ByteArray -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Serializable -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
        is Bundle -> putExtra(key, value)
        else -> {
        }
    }
}

private fun emojiConvert(str: String): String {
    val patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])"
    val pattern = Pattern.compile(patternString)
    val matcher = pattern.matcher(str)
    val sb = StringBuffer()
    while (matcher.find()) {
        try {
            matcher.appendReplacement(
                sb,
                "[["
                        + URLEncoder.encode(
                    matcher.group(1),
                    "UTF-8"
                ) + "]]"
            )
        } catch (_: Exception) {
        }
    }
    matcher.appendTail(sb)
    return sb.string
}

private fun emojiRecovery(str: String): String {
    return try {
        val patternString = "\\[\\[(.*?)]]"
        val pattern = Pattern.compile(patternString)
        val matcher = pattern.matcher(str)
        val sb = StringBuffer()
        while (matcher.find()) {
            try {
                matcher.appendReplacement(
                    sb,
                    URLDecoder.decode(matcher.group(1), "UTF-8")
                )
            } catch (_: Exception) {
            }
        }
        matcher.appendTail(sb)
        sb.string
    } catch (e: Exception) {
        var vstr = str.replace("\\[\\[".toRegex(), nothing)
        vstr = vstr.replace("]]".toRegex(), nothing)
        return try {
            URLDecoder.decode(vstr, "UTF-8")
        } catch (e: Exception) {
            str
        }
    }
}

fun String.stringCount(subStr: String): Int {
    val minLength = length
    val subLength = subStr.length
    var count = 0
    var index = 0
    if (minLength >= subLength) {
        while (indexOf(subStr, index).also { index = it } != NOP) {
            count++
            index += subLength
        }
        return count
    }
    return NAN
}

fun List<String>.addContent(spt: String = nothing, pos: Int = NOP, remove: String = nothing): String {
    if (isEmpty()) return nothing
    var contents = nothing
    forEachIndexed { p, e -> if (p != pos) contents += (e + spt).replace(remove, nothing) }
    return contents.removeLastChar(spt)
}

fun <T> List<T>.sizeBy(it: (T) -> Boolean): Int {
    if (isEmpty()) return NAN
    var sizes = NAN
    forEach { if (it(it)) sizes++ }
    return sizes
}

fun <T> List<T>.sizeOf(it: (T) -> Boolean): ArrayList<T> {
    if (isEmpty()) return ArrayList()
    val lists = ArrayList<T>()
    forEach { if (it(it)) lists.add(it) }
    return lists
}

fun <T> List<T>.sizeBySize(poss: IntRange): ArrayList<T> {
    if (isEmpty()) return ArrayList()
    val lists = ArrayList<T>()
    forEachIndexed { p, e -> if (p in poss) lists.add(e) }
    return lists
}

fun <T> List<T>.findPos(it: (T) -> Boolean): Int {
    if (isEmpty()) return NOP
    var pos = NOP
    forEachIndexed { p, e ->
        if (it(e)) {
            pos = p
            return@forEachIndexed
        }
    }
    return pos
}

fun <T> ArrayList<T>.removeBy(it: (T) -> Boolean) {
    if (isEmpty()) return
    val posx = ArrayList<T>()
    forEach { e -> if (it(e)) posx.add(e) }
    if (posx.isNotEmpty()) posx.forEach { remove(it) }
}

fun <T> List<T>.existBy(it: (T) -> Boolean): Boolean {
    if (isEmpty()) return false
    var isExist = false
    forEach {
        if (it(it)) {
            isExist = true
            return@forEach
        }
    }
    return isExist
}

fun <T> ArrayList<T>.sizeNeed(size: Int): ArrayList<T> {
    if (isEmpty() || size <= NAN) return ArrayList()
    val newList = ArrayList<T>()
    forEachIndexed { p, it -> if (p < size) newList.add(it) }
    return newList
}

fun <T> onelistOf(t: T) = ArrayList<T>().apply { add(t) }

fun Number.point(count: Int): Float = try {
    val bg = BigDecimal(toDouble())
    bg.setScale(count, BigDecimal.ROUND_HALF_UP).toDouble().float
} catch (e: Exception) {
    0f
}

val String.emoji: String get() = emojiConvert(this)

val String.deEmoji: String get() = emojiRecovery(this)

val Long.formatFrkw
    get() = when (this) {
        in 0..999 -> string
        in 1000..9999 -> (this / 1000f).cast1Point.string + "k"
        else -> formatFriendlyEng
    }

val Number.formatFriendlyEng get() = safeOf(nothing) { formatNum(string, zzChine = false, kBool = false) }

val Number.formatFriendly get() = safeOf(nothing) { formatNum(string, zzChine = true, kBool = false) }

val Int.format999 get() = if (this > 999) "999+" else string

fun String.splitNotBlank(vararg delimiter: String): Array<String> =
    run { split(*delimiter).map { it.trim() }.filterNot { it.isBlank() }.toTypedArray() }

fun String.splitNotBlank(regex: Regex, limit: Int = NAN): Array<String> =
    run { split(regex, limit).map { it.trim() }.filterNot { it.isBlank() }.toTypedArray() }

private fun formatNum(num: String, zzChine: Boolean, kBool: Boolean): String {
    val sb = StringBuffer()
    val b0 = BigDecimal("1000")
    val b1 = BigDecimal("10000")
    val b2 = BigDecimal("100000000")
    val b3 = BigDecimal(num)
    var formatNumStr = ""
    var nuit = ""

    if (kBool) {
        return if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
            "999+"
        } else num
    }

    if (b3.compareTo(b1) == -1) {
        sb.append(b3.toString())
    } else if (b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1
        || b3.compareTo(b2) == -1
    ) {
        formatNumStr = b3.divide(b1).toString()
        nuit = if (zzChine) "z" else "w"
    } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
        formatNumStr = b3.divide(b2).toString()
        nuit = "x"
    }
    if ("" != formatNumStr) {
        var i = formatNumStr.indexOf(".")
        if (i == -1) {
            sb.append(formatNumStr).append(nuit)
        } else {
            i += 1
            val v = formatNumStr.substring(i, i + 1)
            if (v != "0") {
                sb.append(formatNumStr.substring(0, i + 1)).append(nuit)
            } else {
                sb.append(formatNumStr.substring(0, i - 1)).append(nuit)
            }
        }
    }
    return if (sb.isEmpty()) "0" else sb.toString()
}

fun String.containAll(vararg string: String): Boolean {
    var isTrue = false
    if (string.isEmpty()) return false
    string.forEach { isTrue = isTrue && contains(it) }
    return isTrue
}

fun String.containLike(vararg string: String): Boolean {
    var isTrue = false
    if (string.isEmpty()) return false
    string.forEach {
        if (contains(it)) {
            isTrue = true
            return@forEach
        }
    }
    return isTrue
}

val <K, V> LinkedHashMap<K, V>.tail: Map.Entry<K, V>?
    get() {
        val iterator: Iterator<Map.Entry<K, V>> = entries.iterator()
        var tail: Map.Entry<K, V>? = NULL
        while (iterator.hasNext()) tail = iterator.next()
        return tail
    }
val String.removeExtra get() = replace(", ", "，").replace("， ", "，").replace(". ", ".").replace("。 ", "。")

val String.replaceSourcePage
    get() = replace("{{page}}", "1").replace("<", nothing).replace(">", nothing).replace(",", nothing)

val String.parseConnStringToNone get() = replace("-", nothing).trim()

val String.removeTitleNumber get() = replace("《", nothing).replace("》", nothing)

val String.removeAllExtra
    get() = removeExtra
        .replace("|", nothing)
        .replace("丨", nothing)
        .replace("\\[", nothing)
        .replace("]", nothing)
        .replace("(", nothing)
        .replace(")", nothing)
        .replace(":", nothing)
        .replace("：", nothing)
        .replace("（", nothing)
        .replace("）", nothing)
        .replace("-", nothing)
        .replace("+", nothing)
        .replace("*", nothing)
        .replace("&", nothing)
        .replace("^", nothing)
        .replace("%", nothing)
        .replace("$", nothing)
        .replace("#", nothing)
        .replace("@", nothing)
        .replace("《", nothing)
        .replace("》", nothing)
        .replace("<", nothing)
        .replace(">", nothing)
        .replace("/", nothing)
        .replace("\\", nothing)
        .replace("=", nothing)
        .replace("~", nothing)
        .replace("`", nothing)

val String.removeAllExtraAndFullWidthExtra
    get():String {
        val pattern = Pattern.compile("([\\x{10000}-\\x{10ffff}\ud800-\udfff])")
        val matcher = pattern.matcher(this)
        val sb = StringBuffer()
        while (matcher.find()) matcher.appendReplacement(sb, nothing)
        matcher.appendTail(sb)
        return sb.string.removeAllExtra
            .replace(".", nothing)
            .replace("【", nothing)
            .replace("】", nothing)
            .replace("「", nothing)
            .replace("」", nothing)
            .replace("『", nothing)
            .replace("』", nothing)
            .replace("~", nothing)
            .replace("`", nothing)
    }

val String.removeAllNumbers
    get() = replace("0", nothing)
        .replace("1", nothing)
        .replace("2", nothing)
        .replace("3", nothing)
        .replace("4", nothing)
        .replace("5", nothing)
        .replace("6", nothing)
        .replace("7", nothing)
        .replace("8", nothing)
        .replace("9", nothing)

fun ArrayList<String>.forEachWith(let: String, empty: String): String {
    if (isEmpty()) return empty
    var whose = nothing
    forEach { whose += it + let }
    whose = whose.removeLastChar(let)
    return whose
}

fun String.removeAfter(char: String) = safeOf(this) { if (char.isNotNothing) split(char)[NAN] + char else this }

fun String.trimEnd(): String {
    val value = toCharArray()
    var len = value.size
    val st = 0
    while (st < len && value[len - 1] <= ' ') len--
    return if (st > 0 || len < value.size) String(value).substring(st, len) else String(value)
}

fun String.removeLastChar(char: String) = safeOf(this) { if (endsWith(char)) substring(NAN, lastIndex) else this }

fun String.removeLastChar() = safeOf(this) { substring(NAN, lastIndex) }

fun parseLineCount(measureHeight: Int, lineSpacing: Int, textSize: Float) =
    round(measureHeight / (lineSpacing + textSize)).toInt()

inline fun <T> safeOf(default: T, it: () -> T): T {
    return try {
        it()
    } catch (t: NullPointerException) {
        default
    } catch (t: UnsatisfiedLinkError) {
        default
    } catch (t: UnsupportedOperationException) {
        default
    } catch (t: ClassNotFoundException) {
        default
    } catch (t: IllegalStateException) {
        default
    } catch (t: NoSuchMethodError) {
        default
    } catch (t: NoSuchFieldError) {
        default
    } catch (t: Error) {
        default
    } catch (t: Exception) {
        default
    } catch (t: Throwable) {
        default
    }
}