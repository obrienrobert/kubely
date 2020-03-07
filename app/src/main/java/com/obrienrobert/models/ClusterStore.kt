package com.obrienrobert.models

object ClusterStore {
    val listOfClusters = ArrayList<ClusterModel>()

    fun getActiveCluster(): ClusterModel? {
        return listOfClusters.find { it.isActiveCluster }
    }
}