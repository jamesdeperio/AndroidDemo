package com.example.design.itemodel.pager

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder
import com.example.design.itemodel.card.CarouselCardModel
import com.example.design.itemodel.card.carouselCard
import com.example.design.itemodel.loader.CarouselLoadingCardModel
import com.example.design.itemodel.loader.carouselLoadingCard
import com.example.design.view.CarouselView

@EpoxyModelClass
abstract class CarouselModel : EpoxyModelWithHolder<CarouselModel.Holder>() {

    @EpoxyAttribute
    var carouselItems:List<CarouselItem>? = ArrayList()

    @EpoxyAttribute
    var type:Type = Type.CONTENT

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var listener: Listener? = null

    override fun getDefaultLayout(): Int = R.layout.carousel

    override fun bind(holder: Holder) {
        with(holder.carouselView) {
            withModels {
                when(type) {
                    Type.CONTENT -> {
                        carouselItems?.forEachIndexed { index, carouselItem ->
                            carouselCard {
                                id(CarouselCardModel::class.java.simpleName+index)
                                item(carouselItem)
                                listener(this@CarouselModel.listener)
                            }
                        }
                    }
                    Type.LOADING -> {
                        (1..5).forEach {
                            carouselLoadingCard {
                                id(CarouselLoadingCardModel::class.java.simpleName+it)
                            }
                        }
                    }
                }
            }
        }
    }


    class Holder : BaseEpoxyHolder() {
        val carouselView by bind<CarouselView>(R.id.carousel_view)
    }


    data class CarouselItem(
        val imageURL:String?,
        val rating:String?,
        val data:Any
    )

    enum class Type {
        LOADING,
        CONTENT
    }

    interface Listener {
        fun onItemClicked(item:CarouselItem)
    }
}
