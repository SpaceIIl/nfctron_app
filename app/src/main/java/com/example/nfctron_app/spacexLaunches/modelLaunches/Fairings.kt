package com.example.nfctron_app.spacexLaunches.modelLaunches

data class Fairings(
    val recovered: Boolean,
    val recovery_attempt: Boolean,
    val reused: Boolean,
    val ships: List<String>
)