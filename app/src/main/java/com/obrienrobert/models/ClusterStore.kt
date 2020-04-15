package com.obrienrobert.models

object ClusterStore {

    lateinit var apiURL: String
    lateinit var username: String
    lateinit var password: String

    private var listOfClusters = ArrayList<ClusterModel>()

    fun getActiveCluster(): ClusterModel? {
        return listOfClusters.find { it.isActiveCluster }
    }

    fun setActiveNamespace(namespace: String) {
        listOfClusters.find { it.isActiveCluster }?.activeNamespace = namespace
    }
}