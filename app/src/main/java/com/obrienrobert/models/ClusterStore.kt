package com.obrienrobert.models

object ClusterStore {
    var listOfClusters = ArrayList<ClusterModel>()

    fun getActiveCluster(): ClusterModel? {
        return listOfClusters.find { it.isActiveCluster }
    }

    fun setActiveNamespace(namespace: String) {
        listOfClusters.find { it.isActiveCluster }?.activeNamespace = namespace
    }

    fun updateCluster(clusterToEdit: String?, updatedCluster: ClusterModel) {
        listOfClusters[listOfClusters.indexOf(listOfClusters.find { it.clusterName == clusterToEdit })] =
            updatedCluster
    }
}