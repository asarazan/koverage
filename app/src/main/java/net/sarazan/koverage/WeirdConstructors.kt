package net.sarazan.koverage

/**
 * Created by Aaron Sarazan on 1/5/18
 */
class WeirdConstructors(
        val clazz: Class<out CharSequence>,
        val bytes: ByteArray,
        val strings: Array<String>
)

class DataWeirdConstructors(
        val clazz: Class<out CharSequence>,
        val bytes: ByteArray,
        val strings: Array<String>
)