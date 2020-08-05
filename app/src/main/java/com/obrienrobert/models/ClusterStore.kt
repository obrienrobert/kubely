package com.obrienrobert.models

object ClusterStore {

    var listOfClusters = ArrayList<ClusterModel>()

    lateinit var currentActiveNamespace: String

    lateinit var apiURL: String
    lateinit var username: String
    lateinit var password: String
}