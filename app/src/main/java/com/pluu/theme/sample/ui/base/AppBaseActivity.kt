package com.pluu.theme.sample.ui.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class AppBaseActivity : AppCompatActivity {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)
}