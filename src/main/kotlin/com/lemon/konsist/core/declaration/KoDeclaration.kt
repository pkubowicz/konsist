package com.lemon.konsist.core.declaration

import com.lemon.konsist.core.const.KoModifier
import com.lemon.konsist.core.const.toKtToken
import com.lemon.konsist.util.PackageHelper
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner
import org.jetbrains.kotlin.psi.psiUtil.isTopLevelKtOrJavaMember
import kotlin.reflect.KClass

abstract class KoDeclaration(private val ktTypeParameterListOwner: KtTypeParameterListOwner) :
    KoNamedDeclaration(ktTypeParameterListOwner) {

    open val fullyQualifiedName by lazy {
        if (ktTypeParameterListOwner.fqName != null) {
            ktTypeParameterListOwner.fqName.toString()
        } else {
            ""
        }
    }

    val packageName by lazy {
        fullyQualifiedName
            .split(".")
            .toMutableList()
            .apply { removeLast() }
            .joinToString(separator = ".")
    }

    val annotations = ktTypeParameterListOwner
        .annotationEntries
        .map { KoAnnotation.getInstance(it) }

    fun hasPublicModifier() = ktTypeParameterListOwner.hasModifier(KtTokens.PUBLIC_KEYWORD)

    fun isPublicOrDefault() = ktTypeParameterListOwner.run {
        if (hasModifier(KtTokens.PUBLIC_KEYWORD)) {
            return@run true
        }

        val hasOtherVisibilityModifier =
            hasModifier(KtTokens.PRIVATE_KEYWORD) ||
                hasModifier(KtTokens.PROTECTED_KEYWORD) ||
                hasModifier(
                    KtTokens.INTERNAL_KEYWORD,
                )

        hasOtherVisibilityModifier.not()
    }

    fun hasPrivateModifier() = ktTypeParameterListOwner
        .modifierList
        ?.hasModifier(KtTokens.PRIVATE_KEYWORD)
        ?: false

    fun hasProtectedModifier() = ktTypeParameterListOwner
        .modifierList
        ?.hasModifier(KtTokens.PROTECTED_KEYWORD)
        ?: false

    fun hasInternalModifier() = ktTypeParameterListOwner
        .modifierList
        ?.hasModifier(KtTokens.INTERNAL_KEYWORD)
        ?: false

    fun isTopLevel() = ktTypeParameterListOwner.isTopLevelKtOrJavaMember()

    fun hasAnnotation(kClass: KClass<*>): Boolean {
        val qualifiedName = kClass.qualifiedName ?: return false

        return annotations
            .map { it.getFullyQualifiedClassName(it.type) }
            .contains(qualifiedName)
    }

    fun hasModifiers(vararg koModifiers: KoModifier) = koModifiers.all {
        ktTypeParameterListOwner
            .modifierList
            ?.hasModifier(it.toKtToken())
            ?: false
    }

    fun resideInPackages(vararg packages: String) = packages.toList().any { PackageHelper.resideInPackage(it, packageName) }

    fun resideInPath(path: String, ignoreCase: Boolean = true) = filePath.contains(path, ignoreCase)

    fun resideOutsidePath(path: String, ignoreCase: Boolean = true) = !resideInPath(path, ignoreCase)
}
