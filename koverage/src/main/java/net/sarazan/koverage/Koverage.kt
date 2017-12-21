package net.sarazan.koverage

import net.sarazan.koverage.testers.ConstructorTester
import net.sarazan.koverage.testers.DataTester
import net.sarazan.koverage.testers.EnumTester
import net.sarazan.koverage.testers.PropertyTester
import kotlin.reflect.KClass

/**
 * Created by Aaron Sarazan on 12/21/17
 */
object Koverage {

    val testers = listOf(
            ConstructorTester,
            PropertyTester,
            DataTester,
            EnumTester
    )

    fun <T : Any> cover(klass: KClass<T>) {
        testers.forEach { it.cover(klass) }
    }

    inline fun <reified T : Any> cover() {
        cover(T::class)
    }
}