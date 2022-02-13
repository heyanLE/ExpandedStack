package com.heyanle.expandedstack

import androidx.compose.runtime.Composable

/**
 * Created by HeYanLe on 2022/2/13 16:56.
 * https://github.com/heyanLE
 */
class PageStackEntity (
    val isExtended: Boolean = false,
    val content: @Composable ()->Unit,
    val viewModel: EStackViewModel
){
    fun pushCurrent(content: @Composable ()->Unit){
        if(isExtended){
            viewModel.pushExpanded(content)
        }else{
            viewModel.pushMain(content)
        }
    }

    fun pushExpanded(content: @Composable ()->Unit){
        viewModel.pushExpanded(content)
    }

    fun pushMain(content: @Composable () -> Unit){
        viewModel.pushMain(content)
    }

    fun popBack(){
        viewModel.popBack()
    }

    fun popCurrent(){
        if(isExtended){
            popExpanded()
        }else{
            popMain()
        }
    }

    fun popMain(){
        viewModel.popMain()
    }

    fun popExpanded(){
        viewModel.popExpanded()
    }


}