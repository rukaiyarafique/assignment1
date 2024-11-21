package com.example.demo.healthInfo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TrackerContoller(@Autowired private val trackerRepository: TrackerRepository){

    //get all trackers
    fun getAllTrackers(): List<Tracker> =
        trackerRepository.findAll().toList()

    //create tracker
    fun createTracker(tracker: Tracker): ResponseEntity<Tracker> {
        val savedTracker = trackerRepository.save(tracker)
        return ResponseEntity(savedTracker, HttpStatus.CREATED)
    }

    //get tracker by id
    fun getTrackerById(trackerId: Int): ResponseEntity<Tracker> {
        val user = trackerRepository.findById(trackerId).orElse(null)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //update trackers
    fun updateTrackerById(trackerId: Int, tracker: Tracker): ResponseEntity<Tracker> {
        val existingTracker = trackerRepository.findById(trackerId).orElse(null)

        if (existingTracker == null){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedTracker = existingTracker.copy(calories = tracker.calories, walkHours = tracker.walkHours, drinking = tracker.drinking)
        trackerRepository.save(updatedTracker)
        return ResponseEntity(updatedTracker, HttpStatus.OK)
    }

    //delete tracker
    fun deletedTrackerById(trackerId: Int): ResponseEntity<Tracker> {
        if (!trackerRepository.existsById(trackerId)){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        trackerRepository.deleteById(trackerId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    //delete tracker by userId
    fun deletedTrackerByUserId(trackersToDelete: List<Tracker>?): Boolean {
        if (trackersToDelete != null) {
            for (tracker in trackersToDelete){
                if (!trackerRepository.existsById(tracker.id)){
                    return false
                }
                trackerRepository.deleteById(tracker.id)
            }
        }
        return true
    }
}