package com.alexvoz.cvirus.presentation.country_list

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Country
import kotlinx.android.synthetic.main.item_country.view.*
import java.util.*


class CountryAdapter(private val onClick: (Country) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Country>() {

        override fun areItemsTheSame(
            oldItem: Country,
            newItem: Country
        ): Boolean {
            //always update
            return false
        }

        override fun areContentsTheSame(
            oldItem: Country,
            newItem: Country
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CountryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_country,
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CountryHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Country>) {
        differ.submitList(list)
    }

    class CountryHolder
    constructor(
        itemView: View,
        private val onClick: (Country) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Country) = with(itemView) {

            itemView.setOnClickListener {
                onClick(item)
            }

            var drawable: Drawable? = null
            try {
                drawable = AppCompatResources.getDrawable(
                    context, context.resources.getIdentifier(
                        if (item.countryCode == "do") "d_o" else item.countryCode.toLowerCase(Locale.ROOT),
                        "drawable",
                        context.packageName
                    )
                ) ?: AppCompatResources.getDrawable(
                    context, R.drawable.lv
                )!!

            } catch (throwable: Throwable) {
                val a = 1
                drawable = AppCompatResources.getDrawable(
                    context, R.drawable.lv
                )
            }


            itemView.country_img.setImageDrawable(drawable)


            itemView.country_name.text = item.country
        }
    }
}