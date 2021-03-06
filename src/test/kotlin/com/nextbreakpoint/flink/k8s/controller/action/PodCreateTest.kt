package com.nextbreakpoint.flink.k8s.controller.action

import com.nextbreakpoint.flink.common.FlinkOptions
import com.nextbreakpoint.flink.k8s.common.FlinkClient
import com.nextbreakpoint.flink.k8s.common.KubeClient
import com.nextbreakpoint.flink.k8s.controller.core.ResultStatus
import com.nextbreakpoint.flink.testing.KotlinMockito.any
import com.nextbreakpoint.flink.testing.KotlinMockito.eq
import com.nextbreakpoint.flink.testing.KotlinMockito.given
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1PodBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class PodCreateTest {
    private val bootstrapJob = mock(V1Pod::class.java)
    private val flinkOptions = FlinkOptions(hostname = "localhost", portForward = null, useNodePort = false)
    private val flinkClient = mock(FlinkClient::class.java)
    private val kubeClient = mock(KubeClient::class.java)
    private val command = PodCreate(flinkOptions, flinkClient, kubeClient)

    @BeforeEach
    fun configure() {
        val job = V1PodBuilder().withNewMetadata().withName("xxx").endMetadata().build()
        given(kubeClient.createPod(eq("flink"), any())).thenReturn(job)
    }

    @Test
    fun `should fail when kubeClient throws exception`() {
        given(kubeClient.createPod(eq("flink"), any())).thenThrow(RuntimeException::class.java)
        val result = command.execute("flink", "test", bootstrapJob)
        verify(kubeClient, times(1)).createPod(eq("flink"), any())
        verifyNoMoreInteractions(kubeClient)
        verifyNoMoreInteractions(flinkClient)
        assertThat(result).isNotNull()
        assertThat(result.status).isEqualTo(ResultStatus.ERROR)
        assertThat(result.output).isNull()
    }

    @Test
    fun `should create pod`() {
        val result = command.execute("flink", "test", bootstrapJob)
        verify(kubeClient, times(1)).createPod(eq("flink"), any())
        verifyNoMoreInteractions(kubeClient)
        verifyNoMoreInteractions(flinkClient)
        assertThat(result).isNotNull()
        assertThat(result.status).isEqualTo(ResultStatus.OK)
        assertThat(result.output).isEqualTo("xxx")
    }
}