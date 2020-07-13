package com.example.qiitaapiapp.epoxy

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.qiitaapiapp.ViewEpoxyCarouselBindingModel_
import com.example.qiitaapiapp.ViewEpoxyRowBindingModel_

class EpoxyListController : EpoxyController() {

    //多分、リストの設定
    var list: List<EpoxyModel> = emptyList()
        set(vlaue) {
            field = vlaue
            requestModelBuild()
        }

    var carouselList: List<EpoxyCarouselModel> = emptyList()
        set(vlaue) {
            field = vlaue
            requestModelBuild()
        }

    override fun buildModels() {

        // carousel header
        if (list.isNotEmpty()) {
            //val spacing = context.dpToPx(8)
            CarouselModel_()
                .padding(Carousel.Padding(8, 0, 0, 0, 8))
                .id("carousel")
                .spanSizeOverride { _, _, _ -> 2 }
                .models(
                    carouselList.map {
                        ViewEpoxyCarouselBindingModel_()
                            .id(it.id)
                    }
                )
                .addTo(this)
        }

        list.forEach { list ->
            ViewEpoxyRowBindingModel_()
                .id(list.id)
                .epoxymodel(list)
                .addTo(this)
        }
    }
}