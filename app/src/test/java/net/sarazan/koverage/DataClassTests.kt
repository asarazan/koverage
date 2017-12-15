package net.sarazan.koverage

import org.junit.Test

/**
 * Confirm 100% test coverage for kotlin data classes
 */
class DataClassTests {

    @Test
    fun testDataClassCoverage() {
        DataClasses.cover<SampleData>()
    }
}
