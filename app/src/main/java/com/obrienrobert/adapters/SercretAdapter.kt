package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.card_view.view.*


class SercretAdapter(private val secrets: List<String>) :
    RecyclerView.Adapter<SercretAdapter.MyViewHolder>() {

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

        secrets.forEachIndexed { index, element ->
            holder.cardView.info_text.text = secrets[index]
        }

    }

    override fun getItemCount() = secrets.size
}