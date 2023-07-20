package com.lemonappdev.konsist.api.provider

interface KoImplementationProvider : KoBaseProvider {
    /**
     * Whether this declaration has implementation.
     *
     * @return `true` if the declaration has the implementation, `false` otherwise.
     */
    fun hasImplementation(): Boolean
}
