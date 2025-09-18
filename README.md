# 🏠 Estato - Real Estate Android App

A modern Android real estate application built with **MVI + Clean Architecture**, showcasing best practices in mobile development with Jetpack Compose.

## 📱 Features

- **Real Estate Listings**: Browse through property listings with shimmer loading animations
- **Property Details**: View comprehensive property information including contact details
- **Offline Support**: Local caching with Room database for offline viewing
- **Responsive UI**: Material Design 3 with Jetpack Compose
- **Error Handling**: Robust error handling with user-friendly messages
- **Refresh Functionality**: Pull-to-refresh and automatic data synchronization

## 🏗️ Architecture

### MVI (Model-View-Intent) + Clean Architecture

The app follows a strict layered architecture with clear separation of concerns:

```
📦 Presentation Layer
├── 🔄 MVI Pattern
│   ├── Contract (Intent, Result, ViewState)
│   ├── Interactor (Business Logic Handler)
│   ├── Mapper (Domain → UI Data)
│   ├── Reducer (State Management)
│   └── ViewModel (State Holder)
│
📦 Domain Layer
├── 🎯 Use Cases (Business Rules)
├── 📋 Models (Pure Domain Objects)
└── 🔌 Repository Interfaces
│
📦 Data Layer
├── 🌐 Remote (API, DTOs)
├── 💾 Local (Database, Entities)
├── 🔄 Mappers (Data Transformation)
└── 📁 Repository Implementation
```

### Key Architectural Principles

1. **Unidirectional Data Flow**: `Intent → Interactor → UseCase → Mapper → Reducer → State`
2. **Layer Isolation**: Each layer only communicates with adjacent layers
3. **Dependency Inversion**: Higher layers depend on abstractions, not implementations
4. **Single Responsibility**: Each component has one clear purpose

## 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose + Material Design 3 |
| **Architecture** | MVI + Clean Architecture |
| **Async/Reactive** | Kotlin Coroutines + Flow |
| **Dependency Injection** | Hilt (Dagger) |
| **Navigation** | Jetpack Navigation Compose |
| **Networking** | Retrofit + OkHttp + Kotlinx Serialization |
| **Database** | Room |
| **Image Loading** | Coil |
| **Testing** | JUnit5 + Mockito + Coroutines Test |
| **Code Quality** | Detekt + KtLint |

## 📋 Project Structure

```
com.estato/
├── core/
│   ├── mvi/              # MVI interfaces and base classes
│   ├── constants/        # App-wide constants
│   └── exceptions/       # Custom exceptions
├── data/
│   ├── local/           # Room database, DAOs, entities
│   ├── remote/          # API services, DTOs
│   ├── repository/      # Repository implementations
│   └── mapper/          # Data layer mappers
├── domain/
│   ├── model/           # Domain models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Business logic use cases
├── presentation/
│   ├── listing/         # Property listing screen
│   │   ├── components/  # Reusable UI components
│   │   └── MVI components (Contract, Interactor, etc.)
│   ├── details/         # Property details screen
│   │   ├── components/  # Screen-specific components
│   │   └── MVI components
│   └── commons/         # Shared UI components (ShimmerEffect)
├── di/                  # Dependency injection modules
├── navigation/          # Navigation configuration
└── ui/theme/           # App theming and styling
```

## ✨ Key Features Implementation

### 🔄 Shimmer Loading Animation
- **Custom ShimmerEffect**: Reusable composable with gradient animation
- **Shimmer Cards**: Property card placeholders during loading
- **Smooth Transitions**: From shimmer to actual content

### 🏠 Property Management
- **Comprehensive Data Model**: Property details with contact information
- **Type Mapping**: Support for apartments, villas, houses, and commercial properties
- **Currency Formatting**: Multi-currency support (EUR, USD, GBP)
- **Nullable Field Handling**: Robust handling of optional API fields

### 💾 Offline-First Architecture
- **Room Database**: Local caching with automatic migrations
- **Repository Pattern**: Smart data source selection (network vs cache)
- **Error Recovery**: Graceful fallback to cached data on network failures

### 🧪 Comprehensive Testing
- **55 Unit Tests** across all MVI components
- **Given-When-Then** naming convention
- **Object-based assertions** with maximum 3 assertions per test
- **Mockito integration** for dependency isolation
- **100% test coverage** for Mappers, Reducers, and Interactors

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or newer
- JDK 17
- Android SDK API 34
- Gradle 8.13+

### Building the Project
```bash
# Clone the repository
git clone https://github.com/AChirvase/Estato
cd estato

# Build the project
./gradlew build

# Run tests
./gradlew test

# Run code quality checks
./gradlew fixAll
```

### Code Quality
The project enforces strict code quality standards:
```bash
# Format code and fix issues
./gradlew fixAll

# Individual tools
./gradlew ktlintFormat  # Kotlin formatting
./gradlew detekt       # Static code analysis
```

## 📊 Testing Strategy

### Test Coverage
- **Data Layer**: RealEstateMapper (13 tests)
- **Presentation Layer**:
  - ListingMapper (6 tests)
  - DetailsMapper (5 tests)
  - ListingReducer (9 tests)
  - DetailsReducer (7 tests)
  - ListingInteractor (7 tests)
  - DetailsInteractor (7 tests)
- **Domain Layer**: GetRealEstatesUseCase (1 test)

### Testing Principles
1. **Isolated Testing**: Each component tested in isolation with mocks
2. **Behavior Testing**: Focus on expected behavior, not implementation details
3. **Edge Case Coverage**: Null handling, error scenarios, and boundary conditions
4. **Readable Tests**: Clear Given-When-Then structure with descriptive names

## 🔧 Development Guidelines

### MVI Pattern Rules
1. **ViewModels** only wire components and hold state
2. **Interactors** handle intents and coordinate use cases
3. **Mappers** transform domain data to UI models
4. **Reducers** update state based on results
5. **One-way data flow** is strictly enforced

### Code Quality Standards
- Max line length: 120 characters
- Max method length: 30 lines
- Max class size: 500 lines
- Max parameters: 6
- Complex conditions: Max 4 conditions

## 🎯 Future Enhancements

- [ ] Property search and filtering
- [ ] Favorites functionality
- [ ] User authentication
- [ ] Property comparison
- [ ] Map integration
- [ ] Push notifications
- [ ] Dark theme support

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

*Built with ❤️ using Modern Android Development practices by Alex Chirvase*