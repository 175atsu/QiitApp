package com.example.qiitaapiapp.epoxy

import com.airbnb.epoxy.EpoxyController
import com.example.qiitaapiapp.ViewEpoxyRowBindingModel_

class EpoxyListController : EpoxyController() {

    //多分、リストの設定
    var list: List<EpoxyModel> = emptyList()
        set(vlaue) {
            field = vlaue
            requestModelBuild()
        }

    override fun buildModels() {
        list.forEach { list ->
            ViewEpoxyRowBindingModel_()
                .id(modelCountBuiltSoFar)
                .epoxymodel(list)
                .addTo(this)
        }
    }
}