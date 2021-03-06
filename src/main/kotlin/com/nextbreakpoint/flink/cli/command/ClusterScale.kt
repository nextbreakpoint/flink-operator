package com.nextbreakpoint.flink.cli.command

import com.nextbreakpoint.flink.cli.core.ClusterCommand
import com.nextbreakpoint.flink.cli.core.HttpUtils
import com.nextbreakpoint.flink.cli.factory.WebClientDefaultFactory
import com.nextbreakpoint.flink.cli.factory.WebClientFactory
import com.nextbreakpoint.flink.common.ConnectionConfig
import com.nextbreakpoint.flink.common.ScaleClusterOptions

class ClusterScale(private val factory: WebClientFactory = WebClientDefaultFactory) : ClusterCommand<ScaleClusterOptions> {
    override fun run(connectionConfig: ConnectionConfig, clusterName: String, args: ScaleClusterOptions) {
        HttpUtils.putJson(factory, connectionConfig, "/api/v1/clusters/$clusterName/scale", args)
    }
}

