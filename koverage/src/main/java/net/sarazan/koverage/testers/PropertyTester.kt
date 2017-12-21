package net.sarazan.koverage.testers

import net.sarazan.koverage.Coverable
import net.sarazan.koverage.util.coverableInstances
import net.sarazan.koverage.util.coverableProperties
import net.sarazan.koverage.util.default
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField

/**
 * Created by Aaron Sarazan on 12/21/17
 */
@Suppress("UNCHECKED_CAST")
object PropertyTester : Coverable {

    override fun <T : Any> cover(klass: KClass<T>) {
        if (klass.coverableProperties.isEmpty()) return
        klass.coverableInstances().forEach {
            obj ->
            klass.coverableProperties.forEach {
                prop ->
                (prop as KProperty1<T, Any>).javaField?.let {
                    it.isAccessible = true
                    if (prop is KMutableProperty1) {
                        (prop as KMutableProperty1<T, Any>).set(obj, it.type.kotlin.default())
                    } else {
                        try {
                            it.set(obj, it.type.kotlin.default())
                        } catch (_: IllegalAccessException) {}
                    }
                    prop.get(obj)
                }
            }
        }
    }
}