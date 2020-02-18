package com.obrienrobert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.kubely.R
import io.fabric8.kubernetes.api.model.Pod
import kotlinx.android.synthetic.main.card_view.view.*


class ClusterAdapter(private val myDataset: List<Pod>) :
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

        myDataset.forEachIndexed { index, element ->
            holder.cardView.info_text.text = element.spec.containers[index].name
        }

    }

    override fun getItemCount() = myDataset.size
}