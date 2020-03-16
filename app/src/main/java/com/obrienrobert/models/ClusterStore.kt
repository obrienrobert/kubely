package com.obrienrobert.models

object ClusterStore {
    var listOfClusters = ArrayList<ClusterModel>()

    fun getActiveCluster(): ClusterModel? {
        return listOfClusters.find { it.isActiveCluster }
    }
}