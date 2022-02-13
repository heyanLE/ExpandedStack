package com.heyanle.expandedstack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * Created by HeYanLe on 2022/2/13 18:02.
 * https://github.com/heyanLE
 */
class TestActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            ExpandedStack(isHasExpanded = true) {
                MainA()
            }
        }
    }
}

@Composable
fun MainA(){
    val entity = LocalPageStackEntity.current
    Text(text = "MainA")
    Button(onClick = {
        entity.pushMain {
            MainB()
        }
    }) {
        Text(text = "to Main B")
    }
    Button(onClick = {
        entity.pushExpanded {
            ExpandedA()
        }
    }) {
        Text(text = "to Expanded A")
    }
}

@Composable
fun MainB(){
    val entity = LocalPageStackEntity.current
    Text(text = "MainB")
    Button(onClick = {
        entity.popCurrent()
    }) {
        Text(text = "Back")
    }
}

@Composable
fun ExpandedA(){
    val entity = LocalPageStackEntity.current
    Text(text = "ExpandedA")
    Button(onClick = {
        entity.pushExpanded {
            ExpandedB()
        }
    }) {
        Text(text = "to Expanded B")
    }
    Button(onClick = {
        entity.popCurrent()
    }) {
        Text(text = "Back")
    }
}

@Composable
fun ExpandedB(){
    val entity = LocalPageStackEntity.current
    Text(text = "ExpandedB")
    Button(onClick = {
        entity.popCurrent()
    }) {
        Text(text = "Back")
    }

}