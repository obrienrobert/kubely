package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.*


interface Get {

    // Pods
    fun getAllPods(): PodList?

    fun getAllPodsInNamespace(namespace: String): PodList?

    fun getPod(namespace: String, pod: String): PodSpec?


    // Services
    fun getAllServices(): ServiceList?

    fun getAllServicesInNamespace(namespace: String): ServiceList?

    fun getService(namespace: String, service: String): ServiceSpec?


    // Namespaces
    fun getAllNamespaces(): NamespaceList?

    fun getSpecificNamespace(namespace: String): Namespace?


}