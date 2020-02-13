package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.NamespaceList
import io.fabric8.kubernetes.api.model.PodList
import io.fabric8.kubernetes.api.model.ServiceList
import io.fabric8.kubernetes.client.Config
import io.fabric8.kubernetes.client.ConfigBuilder
import io.fabric8.kubernetes.client.DefaultKubernetesClient
import io.fabric8.kubernetes.client.KubernetesClient

class Client(masterURL: String, OauthToken: String) : RetrieveAPI {

    private val config: Config =
        ConfigBuilder().withMasterUrl(masterURL)
            .withOauthToken(OauthToken).withTrustCerts(true)
            .build()

    private val client: KubernetesClient = DefaultKubernetesClient(config)

    override fun getPods(): PodList? {
        return client.pods().list()
    }

    override fun getNamespaces(): NamespaceList? {
        return client.namespaces().list()
    }

    override fun getServices(): ServiceList? {
        return client.services().list()
    }
}