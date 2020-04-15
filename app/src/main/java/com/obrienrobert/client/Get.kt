package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.*
import io.fabric8.openshift.api.model.Project

interface Get {

    // Pods
    fun getAllPods(): List<Pod>

    fun getAllPodsInNamespace(namespace: String?): List<Pod>

    fun getPod(namespace: String, pod: String): PodSpec


    // Services
    fun getAllServices(): List<Service>

    fun getAllServicesInNamespace(namespace: String): List<Service>

    fun getService(namespace: String, service: String): ServiceSpec?


    // Namespaces
    fun getAllNamespaces(): List<Project>

    fun getSpecificNamespace(namespace: String): Namespace?

    fun createNamespace(namespace: String?)

    // Nodes
    fun getAllNodes(): List<Node>

    // Events
    fun getAllEventsInNamespace(namespace: String): List<Event>
}