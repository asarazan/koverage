package net.sarazan.koverage.util

import org.mockito.Mockito
import sun.misc.Unsafe
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*

/**
 * Created by Aaron Sarazan on 12/15/17
 */

@Suppress("UNCHECKED_CAST")
fun <T : Any> KClass<T>.coverableInstances(): List<T> {
    return when {
        isCompanion -> listOf(getInstanceOfCompanionClass())
        objectInstance != null -> listOf(objectInstance!!)
        java.isEnum -> java.enumConstants.toList()
        else -> listOf(allocateUnsafe())
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> KClass<T>.allocateUnsafe(): T {
    val unsafeField = Unsafe::class.java.getDeclaredField("theUnsafe")
    unsafeField.isAccessible = true
    val unsafe = unsafeField.get(null) as Unsafe
    return unsafe.allocateInstance(this.java) as T
}

fun <T : Any> KFunction<T>.invokeDefault(): T {
    val params = parameters.map { it.kclass().default() }
    return call(*params.toTypedArray())
}

fun <T : Any> KClass<T>.constructDefault(): T {
    return primaryConstructor!!.invokeDefault()
}

@Suppress("UNCHECKED_CAST")
fun KParameter.kclass(): KClass<*> {
    return type.classifier as KClass<*>
}

@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
fun <T : Any> KClass<T>.default(): T {
    val cls = java
    return when (this) {
        String::class -> ""
        Boolean::class -> false
        Int::class -> 0
        Byte::class -> 0.toByte()
        Char::class -> 0.toChar()
        Double::class -> 0.toDouble()
        Float::class -> 0.toFloat()
        Long::class -> 0.toLong()
        Short::class -> 0.toShort()
        Class::class -> Any::class.java

        // This is my best-effort attempt to support arrays. Can only do primitives and strings
        DoubleArray::class -> doubleArrayOf()
        FloatArray::class -> floatArrayOf()
        LongArray::class -> longArrayOf()
        IntArray::class -> intArrayOf()
        CharArray::class -> charArrayOf()
        ShortArray::class -> shortArrayOf()
        ByteArray::class -> byteArrayOf()
        BooleanArray::class -> booleanArrayOf()
        Array<String>::class -> arrayOf<String>()

        // Everything else gets the Mockito treatment
        else -> Mockito.mock(cls)
    } as T
}

@Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
fun <T : Any> T.different(): T {
    val self = this
    return when (this) {
        is String -> "${this}_diff"
        is Boolean -> !this
        is Int -> this + 1
        is Byte -> this + 1
        is Char -> this + 1
        is Double -> this + 1
        is Float -> this + 1
        is Long -> this + 1
        is Short -> this + 1
        is DoubleArray -> this + 1.0
        is FloatArray -> this + 1f
        is LongArray -> this + 1L
        is IntArray -> this + 1
        is CharArray -> this + 'a'
        is ShortArray -> this + 1
        is ByteArray -> this + 1
        is BooleanArray -> this + true

        // is Class<*> -> I have no idea how to handle this.
        else -> Mockito.mock(self.javaClass)
    } as T
}

val KProperty1<*,*>.simpleName: String
    get() = name.split('.').last()

val KFunction<*>.simpleName: String
    get() = name.split('.').last()

@Suppress("UNCHECKED_CAST")
val <T : Any> KClass<T>.copyFunction: KFunction<T>
    get() = (memberFunctions.find { it.simpleName == "copy" } as KFunction<T>)

fun <T : Any> T.dataClassCopy(): T {
    val copy = javaClass.kotlin.copyFunction
    return copy.callBy(mapOf(copy.parameters.first() to this))
}

val <T : Any> KClass<T>.dataProperties: List<KProperty1<T, *>>
    get() {
        return primaryConstructor!!.parameters.map {
            param ->
            declaredMemberProperties.find { it.name == param.name }!!
        }
    }

val <T : Any> KClass<T>.coverableProperties: List<KProperty1<T, *>>
    get() {
        return if (java.isEnum) {
            declaredMemberProperties
        } else {
            memberProperties
        }.toList()
    }

@Suppress("UNCHECKED_CAST")
fun <T : Any> KClass<T>.getInstanceOfCompanionClass(): T {
    assert(isCompanion)
    val baseClass = Class.forName(qualifiedName!!.split('.').dropLast(1).joinToString(".")).kotlin
    return baseClass.companionObjectInstance as T
}