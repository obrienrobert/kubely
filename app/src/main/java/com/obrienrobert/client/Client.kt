package com.obrienrobert.client

import io.fabric8.kubernetes.client.Config
import io.fabric8.kubernetes.client.ConfigBuilder
import io.fabric8.openshift.client.DefaultOpenShiftClient
import io.fabric8.openshift.client.OpenShiftClient

class Client(masterURL: String?, userName: String?, password: String?) {

    private val config: Config = ConfigBuilder().withMasterUrl(masterURL)
        .withUsername(userName)
        .withPassword(password)
        .withTrustCerts(true)
        .build()

    private var osClient: OpenShiftClient = DefaultOpenShiftClient(config)

    fun getClient(): OpenShiftClient {
        return osClient
    }
}