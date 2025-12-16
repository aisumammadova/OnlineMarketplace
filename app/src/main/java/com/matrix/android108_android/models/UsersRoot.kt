package com.matrix.android108_android.models

data class UsersRoot(
    val limit: Int,
    val skip: Int,
    val total: Int,
    val users: List<User>
)