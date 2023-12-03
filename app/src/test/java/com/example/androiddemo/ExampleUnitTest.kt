package com.example.androiddemo

import org.apache.maven.artifact.versioning.DefaultArtifactVersion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `test force update`() {
        val fromAPI1 = DefaultArtifactVersion("1.2.3".substringBefore("-"))
        val fromAPI2 = DefaultArtifactVersion("1.0.0-dev".substringBefore("-"))
        val fromAPI3 = DefaultArtifactVersion("0.2.9".substringBefore("-"))
        val fromBuild = DefaultArtifactVersion(BuildConfig.VERSION_NAME)


        assertTrue(fromAPI1>fromBuild)
        assertTrue(fromAPI2<=fromBuild)
        assertTrue(fromAPI3<=fromBuild)
    }
}
