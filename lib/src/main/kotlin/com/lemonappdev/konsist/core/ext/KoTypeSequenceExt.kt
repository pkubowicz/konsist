package com.lemonappdev.konsist.core.ext

import com.lemonappdev.konsist.core.declaration.KoType

inline fun <reified T> Sequence<KoType>.withSourceType() = filter { it.sourceType == T::class.simpleName }

inline fun <reified T> Sequence<KoType>.withoutSourceType() = filterNot { it.sourceType == T::class.simpleName }

fun Sequence<KoType>.withSourceType(vararg types: String) = filter {
    types.any { type -> it.sourceType == type }
}

fun Sequence<KoType>.withoutSourceType(vararg types: String) = filter {
    types.none { type -> it.sourceType == type }
}

inline fun <reified T> Sequence<KoType>.withImportAliasOf() =
    filter { it.isImportAlias() && it.sourceType == T::class.simpleName }

inline fun <reified T> Sequence<KoType>.withoutImportAliasOf() =
    filterNot { it.isImportAlias() && it.sourceType == T::class.simpleName }

fun Sequence<KoType>.withImportAlias(vararg names: String) = filter {
    when {
        names.isEmpty() -> it.isImportAlias()
        else -> names.any { name -> it.importAliasName == name }
    }
}

fun Sequence<KoType>.withoutImportAlias(vararg names: String) = filter {
    when {
        names.isEmpty() -> !it.isImportAlias()
        else -> names.none { name -> it.importAliasName == name }
    }
}

fun Sequence<KoType>.withFullyQualifiedName(vararg names: String) = filter {
    names.any { name -> it.fullyQualifiedName == name }
}

fun Sequence<KoType>.withoutFullyQualifiedName(vararg names: String) = filter {
    names.none { name -> it.fullyQualifiedName == name }
}
