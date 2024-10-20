package hs.kr.equus.application.domain.application.service

import hs.kr.equus.application.global.annotation.DomainService
import kotlin.math.pow

@DomainService
class LocationService {
    companion object {
        private const val DSM_LONGITUDE: Double = 127.36304191185
        private const val DSM_LATITUDE: Double = 36.3920263282383
    }

    fun calculateDistanceFromDSM(latitude: Double, longitude: Double): Double {
        return calculateDistance(latitude, longitude, DSM_LATITUDE, DSM_LONGITUDE)
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        return kotlin.math.sqrt((lat2 - lat1).pow(2) + (lon2 - lon1).pow(2))
    }
}