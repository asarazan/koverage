package net.sarazan.koverage.testers

import net.sarazan.koverage.Coverable
import net.sarazan.koverage.util.invokeDefault
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

/**
 * Created by Aaron Sarazan on 12/21/17
 */
@Suppress("UNCHECKED_CAST")
object ConstructorTester : Coverable {

    override fun <T : Any> cover(klass: KClass<T>) {
        if (klass.java.isEnum) return
        if (klass.isCompanion) return
        if (klass.objectInstance != null) return
        klass.primaryConstructor?.let {
            it.isAccessible = true
            it.invokeDefault()
        }
    }
}