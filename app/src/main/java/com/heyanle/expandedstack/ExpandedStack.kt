package com.heyanle.expandedstack

import android.media.effect.Effect
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Created by HeYanLe on 2022/2/13 16:57.
 * https://github.com/heyanLE
 */

val LocalPageStackEntity = compositionLocalOf<PageStackEntity> {
    error("LocalPageStackEntity Not Provide")
}

@Composable
fun ExpandedStack(
    modifier: Modifier = Modifier,
    isHasExpanded: Boolean = false,
    deliver: @Composable ()-> Unit = {
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(3.dp)
            .background(Color.Black))
    },
    content: @Composable ()->Unit,
) {
    val viewModel = viewModel<EStackViewModel>()

    LaunchedEffect(key1 = Unit){
        viewModel.pushMain (content = content)
    }

    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedDispatcher = onBackPressedDispatcherOwner?.onBackPressedDispatcher

    val main by viewModel.mainEntity
    val expanded by viewModel.expandedEntity
    val isBackEnable by viewModel.isBackEnable

    val onCallBack = remember {
        object: OnBackPressedCallback(!isBackEnable){
            override fun handleOnBackPressed() {
                viewModel.popBack()
            }
        }
    }

    DisposableEffect(key1 = Unit){
        onBackPressedDispatcher?.addCallback(onCallBack)
        onDispose {
            onCallBack.remove()
        }
    }

    LaunchedEffect(key1 = isBackEnable){
        onCallBack.isEnabled = isBackEnable
    }



    Row(
        modifier = modifier
    ) {

        if(!isHasExpanded){
            Crossfade(modifier = Modifier.weight(1f), targetState = if(expanded == null) main else expanded) {
                it?.let {
                    CompositionLocalProvider(
                        LocalPageStackEntity provides it
                    ){
                        it.content()
                    }
                }


            }
        }else{
            Crossfade(modifier = Modifier.weight(1f), targetState = main) {
                it?.let {
                    CompositionLocalProvider(
                        LocalPageStackEntity provides it
                    ){
                        it.content()
                    }
                }
            }
            if(viewModel.isExpendedShow(isExpended = isHasExpanded)){
                deliver()
                Crossfade(modifier = Modifier.weight(1f), targetState = expanded) {
                    it?.let {
                        CompositionLocalProvider(
                            LocalPageStackEntity provides it
                        ){
                            it.content()
                        }
                    }
                }
            }
        }
        
    }






}
