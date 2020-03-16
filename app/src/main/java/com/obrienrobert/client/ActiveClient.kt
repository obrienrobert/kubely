package com.obrienrobert.client

import com.obrienrobert.models.ClusterStore

object ActiveClient {

    var client  = Client(
        ClusterStore.getActiveCluster()?.masterURL,
        ClusterStore.getActiveCluster()?.userName,
        ClusterStore.getActiveCluster()?.password
    ).getClient()
}