package com.project.libgen.presentation.book_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.libgen.presentation.book_list.BookListViewModel

@Composable
fun FilterSection(
    viewModel: BookListViewModel
) {
    var filterShow by remember { mutableStateOf(false) }

    Box {
        IconButton(modifier = Modifier
            .size(50.dp)
            .padding(start = 5.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            onClick = { filterShow = true }) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }

        DropdownMenu(expanded = filterShow, onDismissRequest = { filterShow = !filterShow }) {
            viewModel.filterText.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick = {
                        filterShow = false
                        viewModel.filterIndex.value = index
                    },
                    modifier = Modifier.background(
                        if (index == viewModel.filterIndex.value) Color.LightGray else Color.Transparent
                    )
                ) {
                    Text(text)
                }
            }
        }
    }
}