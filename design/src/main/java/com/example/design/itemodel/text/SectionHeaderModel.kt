package com.example.design.itemodel.text

import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.design.R
import com.example.design.helper.BaseEpoxyHolder

@EpoxyModelClass
abstract class SectionHeaderModel : EpoxyModelWithHolder<SectionHeaderModel.Holder>() {

    @EpoxyAttribute
    lateinit var headerText: String

    @EpoxyAttribute
    var actionText: String? = null

    @EpoxyAttribute
    var headerActionListener: (() -> Unit)? = null

    @EpoxyAttribute
    var headerTextAppearance: Int? = null

    @EpoxyAttribute
    var withContentPadding: Boolean = false

    override fun getDefaultLayout(): Int = R.layout.section_header

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder) {
            tvHeader.let {
                it.text = headerText
                headerTextAppearance?.let { appearance->
                    it.setTextAppearance(appearance)
                }
            }
            container.let {
                if (withContentPadding) {
                    val layoutParams = ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    layoutParams.marginStart = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        it.resources.getDimension(R.dimen.margin_8), it.resources.displayMetrics
                    ).toInt()
                    layoutParams.topMargin = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        it.resources.getDimension(R.dimen.margin_2), it.resources.displayMetrics
                    ).toInt()
                    layoutParams.bottomMargin = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        it.resources.getDimension(R.dimen.margin_2), it.resources.displayMetrics
                    ).toInt()
                    it.layoutParams = layoutParams
                }
            }
            buttonSection.let {
                it.text = actionText
                it.setOnClickListener { headerActionListener?.invoke() }
                it.visibility = if (actionText == null) View.GONE else View.VISIBLE
            }
        }
    }

    class Holder : BaseEpoxyHolder() {
        val container by bind<ConstraintLayout>(R.id.container)
        val buttonSection by bind<Button>(R.id.button_section)
        val tvHeader by bind<TextView>(R.id.tv_header)
    }
}
