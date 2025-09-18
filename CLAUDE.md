NEVER MODIFY THIS FILE YOURSELF. READ ONLY.
Always check the latest version of this file.

Name: Estato
Project purpose: android app for real estates
Description: android app with 2 main screens:
	- List page : a real estate listing page.
	- Details page : a details page allowing you to explore each item of the list previously created.


################   Techstack    ################

Language
	Kotlin
	Architecture
	MVI + Clean Architecture

UI
	Jetpack Compose
	Build System
	Gradle (Kotlin DSL)

Asynchronous / Reactive
	Kotlin Coroutines + Flow

Dependency Injection
	Hilt (Dagger)

Navigation
	Jetpack Navigation Component Compose

Networking
	Retrofit
	OkHttp
	Kotlinx.serialization
	Charles
	Certificate Pinning

Data Persistence
	Room
	Jetpack DataStore
	In-memory caching

Media
	Coil 

Firebase & Remote Config
	Firebase Remote Config
	Firebase A/B Testing

Testing
	JUnit5
	MockK
	Turbine
	Espresso + Compose Testing
	Paparazzi

Code Quality & CI
	Detekt + KtLint
	SonarQube
	Android Lint

Security
	Keystore-backed AES encryption
	Secure wrappers for Room and DataStore
	Logging / Debugging
	Timber for logging


################   MVI + Clean Architecture rules    ################ 

1. Layering
	* Data → API, DB, cache, repositories implementations only.
	* Domain → UseCases, pure models, repository interfaces.
	* Presentation → Intents, States, Result, Reducers, UI Mappers, Interactors, Contract, ViewModels only.
	* Core → Generic MVI classes, utilities, security, Firebase wrappers.
	* Data, Domain, Presentation layers contains a mapper that will map data for the current layer
2. MVI
	* ViewModels extend MviViewModel<Interactor, Mapper, Reducer>; only wires Reducer, Interactor, Mapper and initializes the stateflow.
	* Contract: contains Intent, Result, Viewstate interfaces immutable + initial viewState that will be held by ViewModel
	* Interactor: Given Intent When calls Usecase and Mapper THEN map to Result
	* Mapper: Given Domain Data  THEN maps to Current Screen Data
	* Reducer: Given current state and Result from Interactor Then updates state
	* the only allowed path: Compose -> ViewModel -> Interactor -> UseCase -> Mapper -> Reducer
3. Dependency Injection
	* Feature-specific DI modules.
	* Singletons (Retrofit, Room, DataStore, Firebase) in AppModule.
	* No cross-feature dependency leakage.
4. UI & Navigation
	* Compose screens: stateless, observe state StateFlow 
	* Dispatch intents only.
	* Each feature owns NavGraph; root aggregates all.
5. Data & Security
	* Repository decides network vs cache.
	* Room for entities, DataStore for preferences.
	* Encrypt sensitive data; keystore-backed AES.
	* Retrofit with certificate pinning.
6. Testing
	* Unit tests: Interactors, Mappers, Reducers, UseCases
	* UI tests: Compose/Espresso.
	* Snapshot tests: Paparazzi.
	* Use mocks for isolation.
7. Firebase
	* Core wrapper only.
	* No direct Firebase calls in features.
8. Code Quality
	* KtLint + Detekt.
	* Consistent naming. Ex: MyScreenInteractor, MyScreenViewModel, MyScreenReducer, MyFeatureSomething
	* Core = abstractions/utilities only.



################   Code Rules    ################ 

MagicNumber
MaxLineLength 120
LongMethod 30
LargeClass 500
TooManyFunctions 15
TooManyParameters 6
ComplexCondition 4
DuplicateCaseInWhenExpression
UnnecessarySafeCall
UnsafeCallOnNullableType
EmptyCatchBlock
UnusedImports
LateinitUsage
ExplicitItLambdaParameter
EqualsOnSignatureLine


After finalizing the task, run "./gradlew fixAll" max 3 times. Make sure to fix as much as possible of the error logs
