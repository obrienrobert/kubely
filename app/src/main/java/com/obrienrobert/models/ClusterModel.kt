package com.obrienrobert.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClusterModel(
    var uid: String? = "",
    var masterURL: String? = "",
    var clusterName: String? = "",
    var userName: String? = "",
    var password: String? = "",
    var isActiveCluster: Boolean = false,
    var activeNamespace: String = ""

) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "masterURL" to masterURL,
            "clusterName" to clusterName,
            "userName" to userName,
            "password" to password,
            "isActiveCluster" to isActiveCluster,
            "activeNamespace" to activeNamespace
        )
    }
}