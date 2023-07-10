package com.example.task.ui.navigator

import androidx.fragment.app.Fragment

interface Navigator {

    fun launch(fragment: Fragment)

    fun goBack()

}