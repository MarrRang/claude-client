package com.marrrang.claude.configuration

import com.marrrang.claude.common.Timeout
import io.ktor.client.*
import io.ktor.client.engine.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ClaudeConfiguration(
    val token: String,
    val anthropicVersion: String?,
    val timeout: Timeout = Timeout(socket = 30.seconds),
    val headers: Map<String, String> = emptyMap(),
    val host: ClaudeHost = ClaudeHost.Claude,
    val engine: HttpClientEngine? = null,
    val connectionProviderConfiguration: ConnectionProviderConfiguration
)

class ClaudeHost(
    val hostUrl: String,

    val queryParams: Map<String, String> = emptyMap()
) {
    companion object {
        val Claude: ClaudeHost = ClaudeHost(hostUrl = "https://api.anthropic.com/v1")
    }
}

class ConnectionProviderConfiguration(
    val name: String,
    val maxConnections: Int = 500,
    val maxIdleTimeout: Duration = 5.seconds,
    val pendingAcquireTimeout: Duration = 300.milliseconds,
    val lifo: Boolean = true
)
