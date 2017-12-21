package net.sarazan.koverage

import kotlin.reflect.KClass

/**
 * Created by Aaron Sarazan on 12/21/17
 */
@Suppress("UNCHECKED_CAST")
object ConstructorTester : Coverable {

    override fun <T : Any> cover(klass: KClass<T>) {
        if (klass.constructors.isEmpty()) return
        klass.constructors.forEach {
            it.invokeDefault()
        }
    }
}