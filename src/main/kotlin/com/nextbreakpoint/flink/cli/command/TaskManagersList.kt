package com.nextbreakpoint.flink.cli.command

import com.nextbreakpoint.flink.cli.core.ClusterCommand
import com.nextbreakpoint.flink.cli.factory.WebClientDefaultFactory
import com.nextbreakpoint.flink.cli.core.HttpUtils
import com.nextbreakpoint.flink.common.ConnectionConfig

class TaskManagersList : ClusterCommand<Void?>(WebClientDefaultFactory) {
    override fun run(
        connectionConfig: ConnectionConfig,
        clusterName: String,
        unused: Void?
    ) {
        HttpUtils.get(factory, connectionConfig, "/clusters/$clusterName/taskmanagers")
    }
}
