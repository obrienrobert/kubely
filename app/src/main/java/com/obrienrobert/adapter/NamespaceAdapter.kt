package com.obrienrobert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.kubely.R
import kotlinx.android.synthetic.main.card_view.view.*


class NamespaceAdapter(private val namespaces: List<String>) :
    RecyclerView.Adapter<NamespaceAdapter.MyViewHolder>() {

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

        namespaces.forEachIndexed { index, element ->
            holder.cardView.info_text.text = namespaces[index]
        }

    }

    override fun getItemCount() = namespaces.size
}