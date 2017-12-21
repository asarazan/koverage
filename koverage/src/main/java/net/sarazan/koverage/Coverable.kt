package net.sarazan.koverage

import kotlin.reflect.KClass

/**
 * Created by Aaron Sarazan on 12/21/17
 */
interface Coverable {
    fun <T : Any> cover(klass: KClass<T>)
}