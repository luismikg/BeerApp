package com.example.beersapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.beersapp.R
import com.example.beersapp.base.BaseViewHolder
import com.example.beersapp.data.model.Bar
import com.example.beersapp.data.model.BarItem
import kotlinx.android.synthetic.main.beer_row.view.*
import java.util.*
import javax.inject.Inject

class MainAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<BaseViewHolder<*>>(), Filterable {

    private var showingFavorites = false
    private var barOriginal: Bar? = null
    private lateinit var barFilter: List<BarItem>
    private lateinit var onMainAdapterClickListener: OnMainAdapterClickListener

    interface OnMainAdapterClickListener {
        fun onItemClickListener(barItem: BarItem)
    }

    fun setData(data: Bar) {
        if (this.barOriginal == null) {
            barOriginal = data
            barFilter = data
        }
    }

    fun setOnMainAdapterClickListener(onMainAdapterClickListener: OnMainAdapterClickListener) {
        this.onMainAdapterClickListener = onMainAdapterClickListener
    }

    override fun getItemCount(): Int {
        return barFilter.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.beer_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(barFilter[position], position)
        }
    }

    //setup view of each row
    inner class MainViewHolder(itemView: View) : BaseViewHolder<BarItem>(itemView) {

        private val userAgent =
            "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"

        private val requestOptions = RequestOptions()
            .placeholder(R.drawable.beer_dummy)
            .error(R.drawable.beer_dummy)

        @SuppressLint("SetTextI18n")
        override fun bind(item: BarItem, position: Int) {

            val glideUrl = GlideUrl(
                item.photo ?: "Fail",
                LazyHeaders.Builder().addHeader("User-Agent", userAgent).build()
            )

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(glideUrl)
                .fitCenter()
                .listener(object : com.bumptech.glide.request.RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .timeout(6000)
                .into(itemView.photo)

            itemView.name.text = item.name
            itemView.tagline.text = item.tagline
            itemView.firstBrewed.text = item.first_brewed
            itemView.avb.text = "${item.abv.toString()} %"
            itemView.attenuationLevel.text = "${item.attenuation_level.toString()} %"
            itemView.ph.text = item.ph.toString()

            itemView.isFavorite.visibility =
                if (item.isFavorite) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }

            itemView.setOnClickListener {
                onMainAdapterClickListener.onItemClickListener(item)
            }
        }
    }

    fun setFavorites(barFavorites: List<BarItem>) {

        barOriginal?.forEach { it.isFavorite = false }

        //setup mark as favorite some elements from bar list
        for (favorite in barFavorites) {
            val barItem: BarItem? = barOriginal?.find { it.id == favorite.id }
            barItem?.isFavorite = true
        }
    }

    fun showFavorites(constraint: CharSequence?) {
        showingFavorites = true
        barOriginal?.let {
            val resultList: MutableList<BarItem> = mutableListOf()
            for (barItem in it) {
                if (barItem.isFavorite) {
                    resultList.add(barItem)
                }
            }

            barFilter = resultList
            this.filter.filter(constraint)
        }
    }

    fun showAll(constraint: CharSequence?) {
        showingFavorites = false
        barOriginal?.let {
            barFilter = it
            this.filter.filter(constraint)
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var resultList: MutableList<BarItem> = mutableListOf()

                barOriginal?.let {
                    val charSearch = constraint.toString().trim()
                    resultList = mutableListOf()
                    for (barItem in it) {
                        if ((barItem.name?.toLowerCase(Locale.ROOT)
                                ?.contains(charSearch.toLowerCase(Locale.ROOT)) == true) &&
                            (barItem.isFavorite || !showingFavorites)
                        ) {
                            resultList.add(barItem)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    barFilter = it.values as List<BarItem>
                    notifyDataSetChanged()
                }
            }
        }
    }
}