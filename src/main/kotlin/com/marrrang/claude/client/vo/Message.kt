package com.marrrang.claude.client.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.marrrang.claude.common.Role
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val content: List<Content>,
    val id: String,
    val model: String,
    val role: String,
    @SerialName("stop_reason")
    val stopReason: String?,
    @SerialName("stop_sequence")
    val stopSequence: String?,
    val type: String,
    val usage: Usage
)

@Serializable
data class Content(
    val text: String,
    val type: String
)

@Serializable
@JsonIgnoreProperties(ignoreUnknown = true)
data class Usage(
    @SerialName("input_tokens")
    val inputTokens: Int? = 0,
    @SerialName("output_tokens")
    val outputTokens: Int? = 0
)

@Serializable
data class MessageRequest(
    val model: String,
    @SerialName("max_tokens")
    val maxTokens: Int,
    val messages: List<MessageRequestContent>,
    val stream: Boolean = false
)

@Serializable
data class MessageRequestContent(
    val role: Role,
    val content: String
)