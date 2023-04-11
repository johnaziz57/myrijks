# MyRijks

## Description

This app uses Rijks museum API to display its collection of art objects.
Clicking on one of the art objects displayed will open the details of such object.



https://user-images.githubusercontent.com/14923445/231248197-ecadc152-bae7-4605-aa1e-3ef93ad2ec6c.mp4



https://user-images.githubusercontent.com/14923445/231248215-06af1e66-a9a1-4dec-9ae7-6e88598c3028.mp4



## Project structure

This project follows Google's [recommendation](https://developer.android.com/topic/architecture)
regarding the structure.
![Diagram of Typical app architecture](https://developer.android.com/static/topic/libraries/architecture/images/mad-arch-overview.png)
It has three main components:

1. Data
2. Domain
3. UI

### Details

This project is using MVVM architecture.

### To run

Please create `local.gradle` and add the API key to it as follows

```groovy
android {
    defaultConfig {
        buildConfigField "String", "API_KEY", "\"key\""
    }
}

```

## Unit Testing

This project also has UnitTests for the following

1. ViewModels
2. Mappers
3. Interactors
4. Repository

## Technologies used

1. Retrofit 2
2. Hilt
3. Navigation component
4. RxJava 3
5. Coil
6. ViewModel
7. LiveData
8. Mockito
