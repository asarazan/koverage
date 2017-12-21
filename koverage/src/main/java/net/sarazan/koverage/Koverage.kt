package net.sarazan.koverage

import kotlin.reflect.KClass

/**
 * Created by Aaron Sarazan on 12/21/17
 */
object Koverage {

    val testers = listOf(
            ConstructorTester,
            PropertyTester,
            DataTester
    )

    fun <T : Any> cover(klass: KClass<T>) {
        testers.forEach { it.cover(klass) }
    }

    inline fun <reified T : Any> cover() {
        cover(T::class)
    }
}