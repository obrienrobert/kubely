package com.obrienrobert.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize


@IgnoreExtraProperties
@Parcelize
data class ClusterModel(
    var uid: String? = "",
    var masterURL: String? = "",
    var clusterName: String? = "",
    var userName: String? = "",
    var password: String? = "",
    @field:JvmField // https://stackoverflow.com/questions/46406376/kotlin-class-does-not-get-its-boolean-value-from-firebase
    var isActiveCluster: Boolean = false,
    var activeNamespace: String = ""

) : Parcelable {
    @Exclude
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