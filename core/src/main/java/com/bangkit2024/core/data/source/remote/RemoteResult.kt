package com.bangkit2024.core.data.source.remote

sealed class RemoteResult<out R> private constructor() {
    data class Success<out T>(val data: T) : RemoteResult<T>()
    data class Error(val error: String) : RemoteResult<Nothing>()
    data object Empty : RemoteResult<Nothing>()
}