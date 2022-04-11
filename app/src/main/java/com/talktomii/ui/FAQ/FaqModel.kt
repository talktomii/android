package com.talktomii.ui.FAQ

import com.talktomii.recycleradapter.AbstractModel

data class FaqModel(
    val faq_que : String,
    val faq_ans: String
) : AbstractModel()