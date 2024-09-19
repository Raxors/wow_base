package com.raxors.wowbase.core.base

interface BaseScreenState {
    // TODO надо как-то реализовать copy для базового состояния (для функций обработки ошибок и загрузки в BaseViewModel)
    val isLoading: Boolean
    val error: Exception?
}