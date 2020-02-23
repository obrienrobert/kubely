package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.PodList
import kotlinx.android.synthetic.main.card_view.view.*


class ClusterAdapter(private val clusters: List<Pod>) :
    RecyclerView.Adapter<ClusterAdapter.MyViewHolder>() {

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false) as CardView
        return MyViewHolder(cardView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        clusters.forEachIndexed { index, element ->
            holder.cardView.info_text.text = clusters[index].spec.serviceAccountName
        }

    }

    override fun getItemCount() = clusters.size
}