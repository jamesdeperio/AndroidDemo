package com.example.design.itemodel.loader

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.facebook.shimmer.ShimmerFrameLayout

@EpoxyModelClass
abstract class ItemLoadingCardModel : EpoxyModelWithHolder<ItemLoadingCardModel.Holder>() {

    @EpoxyAttribute
    var type: Type = Type.LIST_LOADER_1

    override fun getDefaultLayout(): Int = type.layoutID

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


    enum class Type(val layoutID: Int) {
        LIST_LOADER_1(R.layout.loading_list_type1),
        LIST_LOADER_2(R.layout.loading_list_type2)
    }
}
