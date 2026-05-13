<div align="center">

# 🌤️ SkyCast

### A production-grade Android weather app built with Clean Architecture & modern Jetpack libraries

<br>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-API%2026+-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://android.com)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2025.04.01-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-F4A261?style=for-the-badge)](LICENSE)

<br>

> Search cities. Track weather. Stay informed — even when the app is closed.

<br>

---

</div>

## ✨ Features

| Feature | Description |
|---|---|
| 🔍 **City Search** | Search cities worldwide via OpenWeatherMap Geocoding API |
| 🌡️ **Live Weather** | Current temperature, description, humidity, high & low |
| ♻️ **Unit Toggle** | Switch between °C and °F — preference persisted across sessions |
| 🔄 **Pull to Refresh** | Manually refresh all cities with a swipe |
| ⏱️ **Background Sync** | Automatic weather refresh every 3 hours via WorkManager |
| 🪟 **Home Screen Widget** | Glance-powered widget with live weather at a glance |
| 📴 **Offline Support** | Cached weather data via Room — always shows last known state |
| ⚠️ **Sync Failure Banner** | Notifies the user when background sync fails after max retries |

---

## 🏗️ Architecture

SkyCast is built with **Clean Architecture** split across four Gradle modules, enforcing strict dependency boundaries at the compiler level.

```
┌──────────────────────────────────────────────┐
│                      :app                    │
│         Application · MainActivity · DI      │
└───────────┬──────────────┬───────────────────┘
            │              │
            ▼              ▼
┌───────────────┐  ┌────────────────────┐
│  :presentation│  │       :data        │
│               │  │                    │
│  Screens      │  │  Retrofit + OkHttp │
│  ViewModels   │  │  Room Database     │
│  Navigation   │  │  DataStore         │
│  Widget       │  │  WorkManager       │
│  UI State     │  │  Repository Impl   │
└───────┬───────┘  └────────┬───────────┘
        │                   │
        └─────────┬─────────┘
                  ▼
        ┌──────────────────┐
        │     :domain      │
        │                  │
        │  Models          │
        │  Repo Interfaces │
        │  Use Cases       │
        └──────────────────┘
```

### Dependency Rule

```
:presentation  →  :domain  ←  :data
                     ↑
              (zero dependencies)
```

`:domain` is pure Kotlin — no Android, no framework imports. `:data` and `:presentation` both depend on `:domain` but never on each other.

---

## 🛠️ Tech Stack

<div>

### Core
![Kotlin](https://img.shields.io/badge/Kotlin-Coroutines%20%26%20Flow-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-Dependency%20Injection-34A853?style=flat-square&logo=google&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack-Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)

### Networking
![Retrofit](https://img.shields.io/badge/Retrofit-2.11.0-48B983?style=flat-square)
![OkHttp](https://img.shields.io/badge/OkHttp-5.3.2-48B983?style=flat-square)
![Serialization](https://img.shields.io/badge/Kotlinx-Serialization-7F52FF?style=flat-square&logo=kotlin&logoColor=white)

### Persistence
![Room](https://img.shields.io/badge/Room-Database-4285F4?style=flat-square&logo=google&logoColor=white)
![DataStore](https://img.shields.io/badge/DataStore-Preferences-4285F4?style=flat-square&logo=google&logoColor=white)

### Background & UI
![WorkManager](https://img.shields.io/badge/WorkManager-Background%20Sync-34A853?style=flat-square&logo=google&logoColor=white)
![Glance](https://img.shields.io/badge/Glance-App%20Widget-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Navigation](https://img.shields.io/badge/Navigation-Compose-4285F4?style=flat-square&logo=google&logoColor=white)

### Build
![KSP](https://img.shields.io/badge/KSP-2.3.7-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Convention Plugins](https://img.shields.io/badge/Convention-Plugins-FF6B35?style=flat-square)
![TOML](https://img.shields.io/badge/Version-Catalog%20TOML-FF6B35?style=flat-square)

</div>

---

## 📁 Project Structure

```
SkyCast/
├── build-logic/                    # Convention plugins
│   └── convention/
│       ├── AndroidLibraryConventionPlugin.kt
│       ├── AndroidLibraryComposeConventionPlugin.kt
│       └── AndroidHiltConventionPlugin.kt
│
├── app/                            # Application entry point
│   └── src/main/java/com/maxi/skycast/
│       ├── MainActivity.kt
│       ├── SkyCastApplication.kt
│       ├── di/AppModule.kt
│       └── widget/
│
├── domain/                         # Pure Kotlin — zero framework deps
│   └── src/main/java/com/maxi/skycast/domain/
│       ├── model/                  # City, Weather, TemperatureUnit...
│       ├── repository/             # WeatherRepository, AppPreferences
│       └── usecase/                # One class per use case
│
├── data/                           # Data sources & repository implementations
│   └── src/main/java/com/maxi/skycast/data/
│       ├── local/                  # Room, DataStore
│       ├── remote/                 # Retrofit, DTOs, Interceptors
│       ├── repository/             # WeatherRepositoryImpl
│       ├── worker/                 # WeatherSyncWorker, WeatherSyncScheduler
│       └── di/                     # NetworkModule, DatabaseModule...
│
└── presentation/                   # UI layer
    └── src/main/java/com/maxi/skycast/presentation/
        ├── citylist/               # CityListScreen + ViewModel
        ├── search/                 # SearchScreen + ViewModel
        ├── navigation/             # NavGraph, Screen
        └── ui/theme/               # Material3 theme
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Ladybug or newer
- JDK 11+
- OpenWeatherMap API key (free tier) → [Get one here](https://openweathermap.org/api)

### Setup

**1. Clone the repository**
```bash
git clone https://github.com/maxi/skycast.git
cd skycast
```

**2. Add your API key**

Create or open `local.properties` in the project root and add:
```properties
API_KEY=your_openweathermap_api_key_here
```

> ⚠️ `local.properties` is git-ignored by default. Never commit your API key.

**3. Build and run**

Open the project in Android Studio and run the `:app` configuration on a device or emulator running API 26+.

---

## 🔄 Data Flow

```
User searches city
      │
      ▼
SearchViewModel → SearchCitiesUseCase → WeatherRepository
                                              │
                          ┌───────────────────┤
                          │                   │
                    Geocoding API        Room (cache)
                    (city search)        (saved cities)
                          │
                          ▼
                  User selects city → AddCityUseCase
                                            │
                                    Insert to Room
                                    Fetch weather
                                            │
                                    Room Flow emits
                                            │
                                    CityListViewModel
                                    updates UI state
                                            │
                                       UI renders
```

### Background Sync

```
WorkManager (every 3h, network + battery constraints)
      │
      ▼
WeatherSyncWorker → refresh all cities → update Room
                                              │
                                              ▼
                                    Room Flow re-emits
                                    Widget updates
                                    App UI updates
```

---

## 🏛️ Key Architectural Decisions

| Decision | Choice | Reason |
|---|---|---|
| **API key injection** | OkHttp Interceptor | Centralised — never forgotten on new endpoints |
| **Temperature units** | Fetch metric, convert client-side | Single source of data, display concern only |
| **Weather storage** | Embedded in `CityEntity` | No JOIN needed for the list screen |
| **Uniqueness constraint** | `(latitude, longitude)` | Name+country unreliable across regions |
| **Error wrapping** | `Result<T>` + `runCatching` | Clean ViewModel handling, no checked exceptions |
| **Search debounce** | 500ms + `flatMapLatest` | Cancels stale requests, prevents API spam |
| **Widget data** | WorkManager → Room → Widget | No live observation possible across processes |
| **Module DI** | DI lives with what it provides | Prevents `:app` coupling to implementation details |

---

## 📐 Module Dependencies

```
        ┌──────────────────────────────────────┐
        │                 :app                 │
        │  (thin — wires everything together)  │
        └──────────┬─────────────────┬─────────┘
                   │                 │
                   ▼                 ▼
        ┌───────────────────┐  ┌───────────────────┐
        │   :presentation   │  │      :data        │
        └─────────┬─────────┘  └────────┬──────────┘
                  │                     │
                  └──────────┬──────────┘
                             ▼
                  ┌───────────────────┐
                  │      :domain      │
                  │  (no deps — pure) │
                  └───────────────────┘
```

---

## 🔧 Convention Plugins

SkyCast uses Gradle convention plugins in `build-logic/` to share build configuration across modules without repetition.

| Plugin | Used by | Provides |
|---|---|---|
| `skycast.android.library` | `:data` `:domain` `:presentation` | AGP library, Kotlin, JVM toolchain, compileSdk, minSdk |
| `skycast.android.library.compose` | `:presentation` | Compose compiler plugin, buildFeatures |
| `skycast.android.hilt` | `:data` `:presentation` `:app` | Hilt plugin, KSP, hilt-android, hilt-compiler |

---

<div align="center">

 · Powered by [OpenWeatherMap](https://openweathermap.org)

</div>