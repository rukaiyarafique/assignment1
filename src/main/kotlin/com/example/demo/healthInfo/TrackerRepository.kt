package com.example.demo.healthInfo

import org.springframework.data.repository.CrudRepository

interface TrackerRepository : CrudRepository<Tracker, Int>

