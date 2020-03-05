package com.obrienrobert.models

interface ClusterStore {

    fun findAll(): List<ClusterModel>
    fun create(cluster: ClusterModel)
    fun update(cluster: ClusterModel)
    fun findByName(name: String): ClusterModel?
    fun delete(cluster: ClusterModel)
}