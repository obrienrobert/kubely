package com.obrienrobert.client

import io.fabric8.openshift.api.model.ProjectRequest

interface Put {

    // New namespace
    fun createNamespace(namespace: String?): ProjectRequest
}