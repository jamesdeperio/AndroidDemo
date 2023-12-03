package com.example.design.view

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.*
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.design.R
import com.example.design.databinding.ViewTextboxBinding
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE


class TextBox @JvmOverloads constructor(
    ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(ctx, attrs, defStyleAttr) {

    private val binding: ViewTextboxBinding =
        ViewTextboxBinding.inflate(LayoutInflater.from(context), this)


    private val textWatcherSet: HashSet<TextWatcher> = HashSet()
    var isFocus: Boolean = false

    var text: String? = null
        set(value) {
            field = value ?: ""
            with(binding) {
                if (field.isNullOrEmpty()) {
                    tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                    viewTextbox.background =
                        ContextCompat.getDrawable(context, R.drawable.state_textbox_empty)
                } else {
                    tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                    viewTextbox.background =
                        ContextCompat.getDrawable(context, R.drawable.state_textbox_with_content)
                }
                tvLabel.requestLayout()
                viewTextbox.requestLayout()
                editTextbox.setText(value)
            }
        }
        get() = binding.editTextbox.text?.toString()

    var label: String? = null
        set(value) {
            field = value
            binding.tvLabel.setText(field, TextView.BufferType.SPANNABLE)
        }
        get() = binding.tvLabel.text.toString()

    var isOptional: Boolean = false

    var note: String? = null
        set(value) {
            field = value
            binding.tvNote.text = value
        }
        get() = binding.tvNote.text.toString()

    var error: String? = null
        set(value) {
            field = value
            if (value == null) {
                hideError()
            } else {
                showError()
            }
            binding.tvError.text = value
        }

    var widthWeight = -1f
        set(value) {
            field = value
            binding.viewTextbox.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, field)
            binding.space.layoutParams =
                LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }


    init {
        if (!isInEditMode) initialize(attrs)
    }


    @SuppressLint("CustomViewStyleable")
    private fun initialize(attrs: AttributeSet?) {
        val common = context.obtainStyledAttributes(attrs, R.styleable.CommonAttr, 0, 0)
        val attribText = common.getString(R.styleable.CommonAttr_text)
        val attribError = common.getString(R.styleable.CommonAttr_error)
        val attribNote = common.getString(R.styleable.CommonAttr_note)
        val attribLabel = common.getString(R.styleable.CommonAttr_label)
        val attribPassword = common.getBoolean(R.styleable.CommonAttr_password,false)
        isOptional = common.getBoolean(R.styleable.CommonAttr_optional, false)
        val attribCopyPaste = common.getBoolean(R.styleable.CommonAttr_copyPaste, true)
        val attribWidthWeight: Float = common.getFloat(R.styleable.CommonAttr_widthWeight, -1f)
        common.recycle()


        with(binding) {
            editTextbox.addTextChangedListener(getOnTextChangedListener())
            editTextbox.onFocusChangeListener = getFocusChangeListener()

            if (!attribCopyPaste) {
               disableCopyPasting()
            }
            if (attribPassword) {
              setInputTypeToPassword()
            }

            attribLabel?.let {
                tvLabel.text = it
            }

            attribNote?.let {
                tvNote.text = it
                tvNote.visibility = VISIBLE
            }

            attribError?.let {
                tvError.text = it
            }

            if (attribWidthWeight != -1f) {
                viewTextbox.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, attribWidthWeight
                )
                space.layoutParams = LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f
                )
            }

            if (attribText.isNullOrEmpty()) {
                tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                viewTextbox.background =
                    ContextCompat.getDrawable(context, R.drawable.state_textbox_empty)
            } else {
                tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                viewTextbox.background =
                    ContextCompat.getDrawable(context, R.drawable.state_textbox_with_content)
            }
            tvLabel.requestLayout()
            viewTextbox.requestLayout()
        }
    }

    private fun disableCopyPasting() {
        binding.editTextbox.customSelectionActionModeCallback = NoCopyPasteActionMode()
    }

    fun setInputTypeToPassword() {
         binding.textInputTextbox.endIconMode =  END_ICON_PASSWORD_TOGGLE
         binding.textInputTextbox.endIconDrawable = ContextCompat.getDrawable(context,R.drawable.drawable_password_state)
         binding.editTextbox.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
         binding.editTextbox.transformationMethod = PasswordTransformationMethod.getInstance()
    }


    fun showError() {
        with(binding) {
            tvError.text = error
            viewTextbox.background =
                ContextCompat.getDrawable(context, R.drawable.state_textbox_error)
            if (!error.isNullOrEmpty()) {
                tvError.visibility = VISIBLE
            }
            tvLabel.setTextColor(ContextCompat.getColor(context, R.color.error))
            tvLabel.requestLayout()
        }
    }

    fun hideError() {
        with(binding) {
            tvError.visibility = GONE
            if (isFocus) {
                tvLabel.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.orange
                    )
                )
                viewTextbox.background =
                    ContextCompat.getDrawable(context, R.drawable.state_textbox_typing)
            } else if (text.isNullOrEmpty()) {
                tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                viewTextbox.background =
                    ContextCompat.getDrawable(context, R.drawable.state_textbox_empty)
            } else {
                tvLabel.setTextColor(ContextCompat.getColor(context, R.color.black))
                viewTextbox.background =
                    ContextCompat.getDrawable(context, R.drawable.state_textbox_with_content)
            }
            tvLabel.requestLayout()
            viewTextbox.requestLayout()
        }
    }


    fun addAfterTextChanged(callback: (value: String?) -> Unit) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //do nothing
            }

            override fun afterTextChanged(p0: Editable?) {
                callback(text)
            }

        }
        textWatcherSet.add(textWatcher)
        binding.editTextbox.addTextChangedListener(textWatcher)
    }

    fun removeAllTextWatcher() {
        textWatcherSet.forEach {
            binding.editTextbox.removeTextChangedListener(it)
        }
    }

    fun setInputType(inputType: Int) {
        binding.editTextbox.inputType = inputType
    }

    fun setMaxLength(length: Int) {
        binding.editTextbox.filters = arrayOf<InputFilter>(LengthFilter(length))
    }

    fun setFilter(filter: Array<InputFilter?>?) {
        binding.editTextbox.filters = filter
    }

    fun getEditText(): EditText {
        return binding.editTextbox
    }

    fun getTextAmount(): EditText = binding.editTextbox

    private fun getOnTextChangedListener(): TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            when {
                binding.editTextbox.text?.toString().isNullOrEmpty() && !isOptional -> showError()
                else -> hideError()
            }
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun isErrorVisible(): Boolean {
        return binding.tvError.visibility == VISIBLE
    }

    private fun getFocusChangeListener(): OnFocusChangeListener =
        OnFocusChangeListener { _, focus ->
            isFocus = focus
            when {
                !isFocus && binding.editTextbox.text?.toString().isNullOrEmpty() && !isOptional -> showError()
                else -> hideError()
            }
        }

    fun showCustomError(failedItemMessage: String?) {
        with(binding) {
            failedItemMessage?.let {
                tvError.text = failedItemMessage
            }
            viewTextbox.background =
                ContextCompat.getDrawable(context, R.drawable.state_textbox_error)
            if (!failedItemMessage.isNullOrEmpty()) {
                tvError.visibility = VISIBLE
                tvLabel.setTextColor(ContextCompat.getColor(context, R.color.error))
                tvError.setTextColor(ContextCompat.getColor(context, R.color.error))
            } else {
                hideError()
            }

            tvLabel.requestLayout()
        }
    }

    private class NoCopyPasteActionMode : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {}
    }
}