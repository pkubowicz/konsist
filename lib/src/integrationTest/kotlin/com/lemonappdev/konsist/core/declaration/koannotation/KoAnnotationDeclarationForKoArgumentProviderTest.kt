package com.lemonappdev.konsist.core.declaration.koannotation

import com.lemonappdev.konsist.TestSnippetProvider.getSnippetKoScope
import com.lemonappdev.konsist.api.ext.list.annotations
import org.amshove.kluent.assertSoftly
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class KoAnnotationDeclarationForKoArgumentProviderTest {
    @Test
    fun `annotation-without-arguments`() {
        // given
        val sut = getSnippetFile("annotation-without-arguments")
            .functions()
            .annotations
            .first()

        // then
        assertSoftly(sut) {
            arguments shouldBeEqualTo emptyList()
            numArguments shouldBeEqualTo 0
            hasArguments() shouldBeEqualTo false
        }
    }

    @Test
    fun `annotation-with-constructor-invocation-without-arguments`() {
        // given
        val sut = getSnippetFile("annotation-with-constructor-invocation-without-arguments")
            .functions()
            .annotations
            .first()

        // then
        assertSoftly(sut) {
            arguments shouldBeEqualTo emptyList()
            numArguments shouldBeEqualTo 0
            hasArguments() shouldBeEqualTo false
        }
    }

    @Test
    fun `annotation-with-one-argument`() {
        // given
        val sut = getSnippetFile("annotation-with-one-argument")
            .functions()
            .annotations
            .first()

        // then
        assertSoftly(sut) {
            arguments.map { it.value } shouldBeEqualTo listOf("text")
            numArguments shouldBeEqualTo 1
            hasArguments() shouldBeEqualTo true
        }
    }

    @Test
    fun `annotation-with-two-arguments`() {
        // given
        val sut = getSnippetFile("annotation-with-two-arguments")
            .functions()
            .annotations
            .first()

        // then
        assertSoftly(sut) {
            arguments.map { it.value } shouldBeEqualTo listOf("text", "true")
            numArguments shouldBeEqualTo 2
            hasArguments() shouldBeEqualTo true
        }
    }

    @Test
    fun `annotation-with-multiline-string-argument`() {
        // given
        val sut = getSnippetFile("annotation-with-multiline-string-argument")
            .functions()
            .annotations
            .first()

        // then
        assertSoftly(sut) {
            arguments.map { it.value } shouldBeEqualTo listOf(
                "first line\n    second line",
            )
            numArguments shouldBeEqualTo 1
            hasArguments() shouldBeEqualTo true
        }
    }

    private fun getSnippetFile(fileName: String) =
        getSnippetKoScope("core/declaration/koannotation/snippet/forkoargument/", fileName)
}
