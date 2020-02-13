package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.NamespaceList
import io.fabric8.kubernetes.api.model.PodList
import io.fabric8.kubernetes.api.model.ServiceList

interface RetrieveAPI {

    fun getPods(): PodList?

    fun getServices(): ServiceList?

    fun getNamespaces(): NamespaceList?

}