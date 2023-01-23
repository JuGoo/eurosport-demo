# eurosport-demo
This is a small demo application based on modern Android application tech-stacks and Clean architecture.

Fetching data from the network<br>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- JetPack
    - Compose - to make ui elements
    - LiveData - notify presentation layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - ViewModel - UI related data holder, lifecycle aware.
- Architecture
    - Clean Architecture
    - Repository pattern
- [Retrofit](https://github.com/square/retrofit)
- [Glide](https://github.com/bumptech/glide)
- [Material-Components](https://github.com/material-components/material-components-android)
- [Mockk](https://mockk.io/ANDROID.html) : Mocking in testing


## Project Structure

This project is built using Clean Architecture and is structured in the following way:

**app** - contains Activities/Fragments/Screen

**presentation** - contains ViewModels for the presentation layer

**domain** - contains entities and use cases for the presentation layer to access data from the **data** layer

**data** -  contains data models and repositories for getting data

## Improvements
- UI Test
- Unit test on presentation module
- Branch with ConstraintLayout
- Support Light/Dark Mode
- Fix bugs
- Add Github Actions
