package com.project.libgen.presentation.book_details

sealed class BookDetailsEvent {
    object starBook : BookDetailsEvent()
    object unstarBook : BookDetailsEvent()
    object downloadBook : BookDetailsEvent()
    object getTorrent : BookDetailsEvent()
}
