package com.obrienrobert.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.obrienrobert.helpers.exists
import com.obrienrobert.helpers.read
import com.obrienrobert.helpers.write
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.reflect.Type

const val JSON_FILE = "clusters.json"
val GSONBuilder: Gson = GsonBuilder().setPrettyPrinting().create()!!
val listType: Type = object : TypeToken<java.util.ArrayList<ClusterModel>>() {}.type

object ClusterMemStore : ClusterStore, AnkoLogger {

    var listOfClusters = mutableListOf<ClusterModel>()

    override fun findAll(): List<ClusterModel> {
        return listOfClusters
    }

    override fun create(cluster: ClusterModel) {
        listOfClusters.add(cluster)
    }

    override fun delete(cluster: ClusterModel) {
        listOfClusters.remove(cluster)
    }

    override fun findByName(name: String): ClusterModel? {
        return listOfClusters.find { it.clusterName == name }
    }


    override fun update(cluster: ClusterModel) {
        val foundCluster: ClusterModel? =
            listOfClusters.find { p -> p.clusterName == cluster.clusterName }
        if (foundCluster != null) {
            foundCluster.masterURL = cluster.masterURL
            foundCluster.clusterName = cluster.clusterName
            foundCluster.userName = cluster.userName
            foundCluster.password = cluster.password
            foundCluster.isActiveCluster = cluster.isActiveCluster
            logAll()
        }
    }

    fun serialize(context: Context) {
        val jsonString = GSONBuilder.toJson(listOfClusters, listType)
        write(context, JSON_FILE, jsonString)
    }

    fun deserialize(context: Context) {
        val jsonString = read(context, JSON_FILE)

        if (exists(context, JSON_FILE)) {
            listOfClusters = Gson().fromJson(jsonString, listType)
        }
    }

    private fun logAll() {
        listOfClusters.forEach { info("$it") }
    }
}