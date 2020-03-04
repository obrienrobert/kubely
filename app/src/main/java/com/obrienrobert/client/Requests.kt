package com.obrienrobert.client

import io.fabric8.kubernetes.api.model.*
import io.fabric8.openshift.api.model.Project
import io.fabric8.openshift.client.OpenShiftClient

class Requests(private var client: OpenShiftClient) : Get {

    // Pods
    override fun getAllPods(): List<Pod> {
        return client.pods().list().items
    }

    override fun getAllPodsInNamespace(namespace: String): List<Pod> {
        return client.pods().inNamespace(namespace).list().items
    }

    override fun getPod(namespace: String, pod: String): PodSpec {
        return client.pods().inNamespace(namespace).withName(pod).get().spec
    }


    // Namespaces
    override fun getAllNamespaces(): List<Project> {
        return client.projects().list().items
    }

    override fun getSpecificNamespace(namespace: String): Namespace? {
        return client.namespaces().withName(namespace).get()
    }


    // Services
    override fun getAllServices(): List<Service> {
        return client.services().list().items
    }

    override fun getAllServicesInNamespace(namespace: String): ServiceList? {
        return client.services().inNamespace(namespace).list()
    }

    override fun getService(namespace: String, service: String): ServiceSpec? {
        return client.services().inNamespace(namespace).withName(service).get().spec
    }


    // Nodes
    override fun getAllNodes(): List<Node> {
        return client.nodes().list().items
    }
}
