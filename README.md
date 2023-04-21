# 👴👦 Rick and Morty

A Rick and Morty application where you can filter characters by location and view their details
easily.

## Demo

<img width="33%" src="https://user-images.githubusercontent.com/46245749/233591260-7eb0f654-bb9f-4be4-b33b-4af4624ee2d3.mp4">

## 🛠 Tech Stack & Open-Source Libraries

- Minimum SDK level 21.
- 100% [Kotlin](https://kotlinlang.org/) based + Designed
  with [Jetpack Compose](https://developer.android.com/jetpack/compose) UI Toolkit.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - A coroutine is a
  concurrency design pattern that can use on Android to simplify code that executes asynchronously.
- [Flow](https://kotlinlang.org/docs/flow.html) - An asynchronous data stream that sequentially
  emits values and completes normally or with an exception.
- [MVVM Architecture](https://developer.android.com/topic/architecture#recommended-app-arch) -
  Modern, maintainable, and Google-suggested app architecture.
- Android Architecture Components - Collection of libraries that help you design robust, testable,
  and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
    UI-related data that isn't destroyed on UI changes.
  - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in the
    data layer that contains application data and business logic.
- 💉Dependency Injection
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Easy
    implementation and less boilerplate code than Dagger2.
- 🌐 Networking
  - [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
  - [OkHttp](https://square.github.io/okhttp/) - An HTTP client that efficiently make network
    requests.
- 🖼️ Image Loading
  - [Coil](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- 🗐 Pagination
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The Paging library helps you load and display pages of data from a larger dataset from local storage or over network.

## 🏗️ Package Structure

```
rickandmorty/      # Root package
|
├─ data/           # Data layer
│  ├─ database/    # Local storage
│  ├─ dto/         # Data transfer objects for remote responses
│  ├─ network/     # Api service
│  ├─ repository/  # Repository
|
├─ di/             # Dependency-injection modules
|
├─ domain/         # Domain layer
│  ├─ mapper/      # Mappers for network responses
│  ├─ model/       # UI models
|
├─ navigation/     # Compose navigation host
|
├─ ui/             # Presentation layer
│  ├─ detail/      # Character details
│  ├─ home/        # Main screen (Characters & Locations)
|  ├─ splash/      # Starting screen for SDK level < 31
|  ├─ theme/       # App theming
|  ├─ util/        # UI utility objects
|
├─ util/           # Common utility objects
```

## Splash Screen Changes in Android 12

![image](https://user-images.githubusercontent.com/46245749/233596683-4a7fabda-8662-4113-b483-002b50d29d88.png)

Starting from Android 12, a default Splash screen is shown for all apps, which cannot be customized or disabled. This means that any custom Splash screens that were previously used in your app will no longer be visible on devices running Android 12 and later versions.

However, this creates a challenge for apps that support both Android 11 and lower versions, as well as Android 12 and higher versions. In my Rick and Morty project, I have implemented the desired splash screen for Android 11 and lower versions, while also using the new Splash API for Android 12 and higher versions.

As you can see, the Welcome and Hello texts appear on Android 11 and below, while only the logo appears on Android 12 and above.

For more information on the subject, you can visit [here](https://developer.android.com/develop/ui/views/launch/splash-screen).
