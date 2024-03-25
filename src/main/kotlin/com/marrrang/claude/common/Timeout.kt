package com.marrrang.claude.common

import kotlin.time.Duration

class Timeout (
    public val request: Duration? = null,
    public val connect: Duration? = null,
    public val socket: Duration? = null,
)