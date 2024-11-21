package com.example.demo.healthInfo

import jakarta.persistence.*

@Entity
@Table(name = "trackers")
data class Tracker(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val userId: Int,
    val walkHours: Double,
    val calories: Double,
    val drinking: Double,

)