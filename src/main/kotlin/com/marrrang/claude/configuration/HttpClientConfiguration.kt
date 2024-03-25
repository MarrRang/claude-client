package com.marrrang.claude.configuration

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.marrrang.claude.client.ClaudeClient
import com.marrrang.claude.client.builder.webClientBuilder
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

internal fun createHttpClient(claudeConfiguration: ClaudeConfiguration): ClaudeClient {
    val clientBuilder = webClientBuilder(claudeConfiguration)

    return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(clientBuilder.build()))
        .build()
        .createClient(ClaudeClient::class.java)
}

internal fun exchangeStrategies(): ExchangeStrategies {
    return ExchangeStrategies.builder()
        .codecs {
            val ignoreCaseMapper = JsonMapper.builder()
                .addModule(KotlinModule.Builder().build())
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)      // 대소문자 구분 없음
                .build()
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(ignoreCaseMapper))
            it.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
        } // 버퍼 메모리 최대 10MB
        .build()
}