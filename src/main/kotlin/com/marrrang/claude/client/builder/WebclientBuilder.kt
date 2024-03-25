package com.marrrang.claude.client.builder

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.marrrang.claude.configuration.ClaudeConfiguration
import com.marrrang.claude.configuration.exchangeStrategies
import io.netty.channel.ChannelOption
import org.springframework.http.HttpHeaders
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import kotlin.time.toJavaDuration

public fun webClientBuilder(configuration: ClaudeConfiguration): WebClient.Builder {
    return WebClient.builder()
        .baseUrl(configuration.host.hostUrl)
        .exchangeStrategies(exchangeStrategies())
        .defaultHeader("x-api-key", configuration.token)
        .defaultHeader("anthropic-version", configuration.anthropicVersion)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create(
                    ConnectionProvider.builder(configuration.connectionProviderConfiguration.name)
                        .maxConnections(configuration.connectionProviderConfiguration.maxConnections)
                        .maxIdleTime(configuration.connectionProviderConfiguration.maxIdleTimeout.toJavaDuration())
                        .pendingAcquireTimeout(configuration.connectionProviderConfiguration.pendingAcquireTimeout.toJavaDuration())
                        .lifo()
                        .build()
                )
                    .responseTimeout(Duration.ofMillis(99000))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
            )
        )
}

internal fun exchangeStrategies(): ExchangeStrategies {
    return ExchangeStrategies.builder()
        .codecs {
            val ignoreCaseMapper = JsonMapper.builder()
                .addModule(KotlinModule.Builder().build())
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .build()
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(ignoreCaseMapper))
            it.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
        }
        .build()
}