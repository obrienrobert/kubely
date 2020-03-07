package com.obrienrobert.client

import com.obrienrobert.models.ClusterStore

object ActiveClient {

    private val activeCluster = ClusterStore.getActiveCluster()

    val client  = Client(
        activeCluster?.masterURL,
        activeCluster?.userName,
        activeCluster?.password
    ).getClient()
}