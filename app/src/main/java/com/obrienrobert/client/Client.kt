package com.obrienrobert.client

import io.fabric8.kubernetes.client.Config
import io.fabric8.kubernetes.client.ConfigBuilder
import io.fabric8.openshift.client.DefaultOpenShiftClient
import io.fabric8.openshift.client.OpenShiftClient
import org.jetbrains.anko.AnkoLogger

class Client(masterURL: String?, userName: String?, password: String?) : AnkoLogger {

    private val config: Config = ConfigBuilder().withMasterUrl(masterURL)
        .withUsername(userName)
        .withPassword(password)
        .withTrustCerts(true)
        .build()

    private val osClient: OpenShiftClient = DefaultOpenShiftClient(config)

    fun getClient(): OpenShiftClient {
        return osClient
    }
}