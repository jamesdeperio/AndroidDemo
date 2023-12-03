package com.example.design.itemodel.card

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.example.design.itemodel.pager.CarouselModel
import com.example.design.listener.setOnThrottleClickListener
import com.facebook.shimmer.ShimmerFrameLayout

@EpoxyModelClass
abstract class CarouselCardModel : EpoxyModelWithHolder<CarouselCardModel.Holder>() {

    @EpoxyAttribute
    lateinit var item:CarouselModel.CarouselItem

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var listener: CarouselModel.Listener? = null

    override fun getDefaultLayout(): Int = R.layout.item_list_carousel

    override fun bind(holder: Holder) {
        with(holder) {
            shimmerLayout.startShimmerAnimation()
            holder.itemView?.setOnThrottleClickListener {
                listener?.onItemClicked(item)
            }
            tvRating.text = item.rating ?:"N/A"
            item.imageURL?.let { url ->
                holder.context?.let { context ->
                    Glide.with(context)
                        .asDrawable()
                        .centerCrop()
                        .load(url)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                shimmerLayout.stopShimmerAnimation()
                                shimmerLayout.visibility= View.GONE
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                shimmerLayout.stopShimmerAnimation()
                                shimmerLayout.visibility= View.GONE
                                return false
                            }
                        })
                        .into(ivImage)
                }
            }
        }
    }

    override fun unbind(holder: Holder) {
        holder.shimmerLayout.stopShimmerAnimation()
        super.unbind(holder)
    }

    class Holder : BaseEpoxyHolder() {
        val shimmerLayout by bind<ShimmerFrameLayout>(R.id.shimmer_layout)
        val ivImage by bind<AppCompatImageView>(R.id.iv_image)
        val tvRating by bind<TextView>(R.id.tv_rating)
    }



}
