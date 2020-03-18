package com.obrienrobert.models

import com.soywiz.klock.DateTime

data class ClusterModel(
    var masterURL: String? = "",
    var clusterName: String? = "",
    var userName: String? = "",
    var password: String? = "",
    var isActiveCluster: Boolean = false,
    var activeNamespace: String = "",
    var dataAdded: DateTime? = DateTime.now()
)