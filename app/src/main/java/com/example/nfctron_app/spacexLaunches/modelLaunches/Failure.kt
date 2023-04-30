package com.example.nfctron_app.spacexLaunches.modelLaunches

data class Failure(
    val altitude: Int,
    val reason: String,
    val time: Int
)