package com.marrrang.claude.client

import com.marrrang.claude.client.vo.Message
import com.marrrang.claude.client.vo.MessageRequest
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import reactor.core.publisher.Flux

@HttpExchange
interface ClaudeClient {
    @PostExchange("/messages")
    fun createMessage(@RequestBody messageRequest: MessageRequest): Message

    @PostExchange("/messages")
    fun createMessageStream(@RequestBody messageRequest: MessageRequest): Flux<ServerSentEvent<String>>
}