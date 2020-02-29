package com.obrienrobert.models

import com.soywiz.klock.DateTime

data class ClusterModel(
    var uid: String? = "",
    var masterURL: String? = "",
    var userName: String? = "",
    var password: String? = "",
    var dataAdded: DateTime? = DateTime.now()
)