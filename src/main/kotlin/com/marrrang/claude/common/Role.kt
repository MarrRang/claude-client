package com.marrrang.claude.common

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
public value class Role (
    val role: String
) {
    companion object {
        val User: Role = Role("user")
        val Assistant: Role = Role("assistant")
    }
}