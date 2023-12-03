package com.example.notification.ui.main

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.ext.viewBinding
import com.example.design.base.BaseFragment
import com.example.design.itemodel.pager.CarouselModel
import com.example.design.itemodel.pager.carousel
import com.example.notification.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment() {

    private val binding by viewBinding<FragmentNotificationsBinding>()

    override fun onViewDidLoad() {
        binding.recyclerView.requestModelBuild()
    }

    override fun onViewConfiguration(): View {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.withModels {
            carousel {
                id(CarouselModel::class.java.simpleName)
                type(CarouselModel.Type.LOADING)
                listener(object:CarouselModel.Listener {
                    override fun onItemClicked(item: CarouselModel.CarouselItem) {

                    }

                })

            }
        }
        return binding.root
    }

    override fun onInitializeIntentData() {

    }

    override fun onObserveState() {

    }

}