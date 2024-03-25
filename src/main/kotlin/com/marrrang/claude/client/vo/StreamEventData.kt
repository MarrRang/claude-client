package com.marrrang.claude.client.vo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.marrrang.claude.common.StreamEventType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
open class StreamData(
    val type: String
)

@Serializable
class MessageStart(
    val message: Message
): StreamData(type = StreamEventType.MessageStart.type)

@Serializable
class ContentBlockStart(
    val index: Int,
    @SerialName("content_block")
    val contentBlock: ContentBlock
): StreamData(type = StreamEventType.ContentBlockStart.type)

@Serializable
class Ping: StreamData(StreamEventType.Ping.type)

@Serializable
class ContentBlockDelta(
    val index: Int,
    val delta: Delta
): StreamData(type = StreamEventType.ContentBlockDelta.type)

@Serializable
class ContentBlockStop(
    val index: Int
): StreamData(type = StreamEventType.ContentBlockStop.type)

@Serializable
@JsonIgnoreProperties(ignoreUnknown = true)
class MessageDelta(
    @SerialName("delta")
    val delta: StopDelta?,
    @SerialName("usage")
    val usage: Usage?
): StreamData(type = StreamEventType.MessageDelta.type)

@Serializable
class MessageStop(): StreamData(type = StreamEventType.MessageStop.type)

@Serializable
class ContentBlock(
    val type: String,
    val text: String?
)

@Serializable
class Delta(
    val type: String,
    val text: String?
)

@Serializable
@JsonIgnoreProperties(ignoreUnknown = true)
class StopDelta(
    @SerialName("stop_reason")
    val stopReason: String? = "",
    @SerialName("stop_sequence")
    val stopSequence: String? = "",
)