package com.heyanle.expandedstack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by HeYanLe on 2022/2/13 17:18.
 * https://github.com/heyanLE
 */
class EStackViewModel: ViewModel() {

    val mainStack = mutableListOf<PageStackEntity>()
    val expandedStack = mutableListOf<PageStackEntity>()
    val actionStack = mutableListOf<PageStackEntity>()

    val mainEntity = mutableStateOf<PageStackEntity?>(null)
    val expandedEntity = mutableStateOf<PageStackEntity?>(null)

    val isBackEnable = mutableStateOf(true)

    fun isExpendedShow(isExpended: Boolean):Boolean{

        if(!isExpended){
            return false
        }

        if(expandedStack.isEmpty()){
            return false
        }

        return true
    }



    fun pushExpanded(content: @Composable ()->Unit){
        viewModelScope.launch {
            val pageStackEntity = PageStackEntity(true, content, this@EStackViewModel)
            expandedStack.add(pageStackEntity)
            actionStack.add(pageStackEntity)
            refresh()
        }

    }

    fun pushMain(content: @Composable () -> Unit){
        viewModelScope.launch {
            val pageStackEntity = PageStackEntity(false, content, this@EStackViewModel)
            mainStack.add(pageStackEntity)
            actionStack.add(pageStackEntity)
            refresh()
        }
    }

    fun popExpanded(){
        viewModelScope.launch {
            runCatching {
                if(actionStack.last() == expandedStack.last()){
                    actionStack.removeLast()
                }
                if(expandedStack.isNotEmpty()){
                    expandedStack.removeLast()
                }
                refresh()
            }

        }
    }

    fun popMain(){
        viewModelScope.launch {
            runCatching {
                if(actionStack.last() == mainStack.last()){
                    actionStack.removeLast()
                }
                if(mainStack.size > 1){
                    mainStack.removeLast()
                }
                refresh()
            }

        }
    }

    fun popBack(){
        viewModelScope.launch {
            while(actionStack.isNotEmpty()){
                if(mainStack.isEmpty() && expandedStack.isEmpty()){
                    actionStack.clear()
                    break
                }
                if(actionStack.isEmpty()){
                    mainStack.clear()
                    expandedStack.clear()
                    break
                }
                if (actionStack.isNotEmpty() && actionStack.last() == mainStack.last()){
                    mainStack.removeLast()
                    actionStack.removeLast()
                    break
                }
                if(expandedStack.isNotEmpty() && expandedStack.last() == actionStack.last()){
                    expandedStack.removeLast()
                    actionStack.removeLast()
                    break
                }
                actionStack.removeLast()
            }
            refresh()

        }
    }

    private fun refresh(){
        mainEntity.value = if (mainStack.isEmpty())  null else {mainStack.last()}
        expandedEntity.value = if(expandedStack.isEmpty()) null else {expandedStack.last()}
        isBackEnable.value = !(mainStack.size == 1 && expandedStack.isEmpty())
    }

}