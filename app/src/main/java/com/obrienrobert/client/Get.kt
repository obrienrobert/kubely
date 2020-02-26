package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.*
import io.fabric8.openshift.api.model.Project


interface Get {

    // Pods
    fun getAllPods(): PodList?

    fun getAllPodsInNamespace(namespace: String): List<Pod>

    fun getPod(namespace: String, pod: String): PodSpec


    // Services
    fun getAllServices(): ServiceList?

    fun getAllServicesInNamespace(namespace: String): ServiceList?

    fun getService(namespace: String, service: String): ServiceSpec?


    // Namespaces
    fun getAllNamespaces(): List<Project>

    fun getSpecificNamespace(namespace: String): Namespace?


}