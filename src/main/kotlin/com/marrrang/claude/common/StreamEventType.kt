package com.marrrang.claude.common

public enum class StreamEventType (
    val type: String
) {
    MessageStart("message_start"),
    ContentBlockStart("content_block_start"),
    Ping("ping"),
    ContentBlockDelta("content_block_delta"),
    ContentBlockStop("content_block_stop"),
    MessageDelta("message_delta"),
    MessageStop("message_stop")
}