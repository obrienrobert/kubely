package com.obrienrobert.client

import com.obrienrobert.models.ClusterStore
import io.fabric8.openshift.client.OpenShiftClient

class ActiveClient {

    companion object {
        fun newInstance(): OpenShiftClient {
            return Client(
                ClusterStore.apiURL,
                ClusterStore.username,
                ClusterStore.password
            ).getClient()
        }
    }
}