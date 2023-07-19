package com.lemonappdev.konsist.core.declaration

import com.lemonappdev.konsist.api.container.KoFile
import com.lemonappdev.konsist.api.declaration.KoPrimaryConstructorDeclaration
import com.lemonappdev.konsist.api.provider.KoParentDeclarationProvider
import com.lemonappdev.konsist.core.cache.KoDeclarationCache
import com.lemonappdev.konsist.core.provider.KoAnnotationProviderCore
import com.lemonappdev.konsist.core.provider.KoConstructorProviderCore
import com.lemonappdev.konsist.core.provider.KoDeclarationFullyQualifiedNameProviderCore
import com.lemonappdev.konsist.core.provider.KoModifierProviderCore
import com.lemonappdev.konsist.core.provider.KoPackageProviderCore
import com.lemonappdev.konsist.core.provider.KoParametersProviderCore
import com.lemonappdev.konsist.core.provider.KoResideInOrOutsidePackageProviderCore
import com.lemonappdev.konsist.core.provider.KoTopLevelProviderCore
import com.lemonappdev.konsist.core.util.TagUtil
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPrimaryConstructor
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner

internal class KoPrimaryConstructorDeclarationImpl private constructor(
    private val ktPrimaryConstructor: KtPrimaryConstructor,
    override val parentDeclaration: KoParentDeclarationProvider?,
) :
    KoBaseDeclarationImpl(ktPrimaryConstructor),
    KoAnnotationProviderCore,
    KoPackageProviderCore,
    KoResideInOrOutsidePackageProviderCore,
    KoDeclarationFullyQualifiedNameProviderCore,
    KoModifierProviderCore,
    KoTopLevelProviderCore,
    KoParametersProviderCore,
    KoConstructorProviderCore,
    KoPrimaryConstructorDeclaration {
    override val ktAnnotated: KtAnnotated by lazy { ktPrimaryConstructor }

    override val ktFile: KtFile? by lazy { null }

    override val ktTypeParameterListOwner: KtTypeParameterListOwner by lazy { ktPrimaryConstructor }

    override val ktCallableDeclaration: KtCallableDeclaration by lazy { ktPrimaryConstructor }

    override val koFiles: Sequence<KoFile>? by lazy { null }

    override fun hasValidParamTag(enabled: Boolean): Boolean = TagUtil.hasValidParamTag(enabled, parameters, kDoc)
    internal companion object {
        private val cache: KoDeclarationCache<KoPrimaryConstructorDeclaration> = KoDeclarationCache()

        internal fun getInstance(
            ktPrimaryConstructor: KtPrimaryConstructor,
            parentDeclaration: KoParentDeclarationProvider?,
        ): KoPrimaryConstructorDeclaration =
            cache.getOrCreateInstance(ktPrimaryConstructor, parentDeclaration) {
                KoPrimaryConstructorDeclarationImpl(
                    ktPrimaryConstructor,
                    parentDeclaration,
                )
            }
    }
}
