package com.example.design.itemodel.loader

import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.facebook.shimmer.ShimmerFrameLayout

@EpoxyModelClass
abstract class CarouselLoadingCardModel : EpoxyModelWithHolder<CarouselLoadingCardModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.loading_list_carousel

    override fun bind(holder: Holder) {
        with(holder) {
            shimmerLayout.startShimmerAnimation()
        }
    }

    override fun unbind(holder: Holder) {
        holder.shimmerLayout.stopShimmerAnimation()
        super.unbind(holder)
    }


    class Holder : BaseEpoxyHolder() {
        val shimmerLayout by bind<ShimmerFrameLayout>(R.id.shimmer_layout)
    }



}
