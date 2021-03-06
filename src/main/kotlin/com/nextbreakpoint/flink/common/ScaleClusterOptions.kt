package com.nextbreakpoint.flink.common

import com.fasterxml.jackson.annotation.JsonAutoDetect

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class ScaleClusterOptions(
    val taskManagers: Int
)