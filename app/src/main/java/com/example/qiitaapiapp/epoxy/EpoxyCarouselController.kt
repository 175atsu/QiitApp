package com.example.qiitaapiapp.epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.qiitaapiapp.ViewEpoxyCarouselBindingModel_


class EpoxyCarouselController: EpoxyController() {
    //多分、リストの設定
    var list: List<EpoxyCarouselModel> = emptyList()
        set(vlaue) {
            field = vlaue
            requestModelBuild()
        }

    override fun buildModels() {
        list.forEach { list ->
            ViewEpoxyCarouselBindingModel_()
                .id("carousel")
                .addTo(this)
        }
    }
}