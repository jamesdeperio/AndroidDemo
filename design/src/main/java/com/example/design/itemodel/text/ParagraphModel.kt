package com.example.design.itemodel.text

import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.TypedValue
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder

@EpoxyModelClass
abstract class ParagraphModel : EpoxyModelWithHolder<ParagraphModel.Holder>() {
    override fun getDefaultLayout(): Int = R.layout.paragraph

    @EpoxyAttribute
    lateinit var displayText: String

    @EpoxyAttribute
    var textSize: Float? = null


    override fun bind(holder: Holder) {
        with(holder) {
            textViewBody.text = Html.fromHtml(displayText,FROM_HTML_MODE_COMPACT)
            textSize?.let {
                textViewBody.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
            }
        }
    }

    class Holder : BaseEpoxyHolder() {
        val textViewBody by bind<TextView>(R.id.text_view_body)
    }
}
