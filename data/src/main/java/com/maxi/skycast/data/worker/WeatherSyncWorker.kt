package com.maxi.skycast.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.maxi.skycast.data.local.datastore.DefaultAppPreferencesDataStore
import com.maxi.skycast.domain.repository.WeatherRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WeatherSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: WeatherRepository,
    private val appPreferencesDataStore: DefaultAppPreferencesDataStore
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val WORK_NAME = "weather_sync_work"
        const val MAX_RETRIES = 3
    }

    override suspend fun doWork(): Result {
        return repository.refreshWeatherForAllCities()
            .fold(
                onSuccess = {
                    appPreferencesDataStore.saveSyncFailed(false)
                    Result.success()
                },
                onFailure = {
                    if (runAttemptCount >= MAX_RETRIES) {
                        appPreferencesDataStore.saveSyncFailed(true)
                        Result.failure()
                    } else {
                        Result.retry()
                    }
                }
            )
    }
}