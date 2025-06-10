# Tuition Management App

A comprehensive Android application for managing tuition classes, built with modern Android development technologies.

## Features

### 🔐 Authentication
- User registration and login with email/password
- Firebase Authentication integration
- Session management with automatic login

### 👥 Student Management
- Add, view, and delete students
- Store student information (name, grade, contact info)
- Real-time updates with Firestore

### 📚 Assignment Management
- Create and manage assignments
- Track due dates and subjects
- Assignment details and descriptions

### 🏫 Class Management
- Organize tuition classes
- Teacher information and schedules
- Subject-wise class organization

### 📝 Test Management
- Schedule and track tests
- Set maximum marks for tests
- Test date management

### 👤 User Profile
- View and edit user profile
- Account information display
- Name update functionality

### ⚙️ Settings
- Dark/Light theme toggle
- Theme preference persistence
- App information and about section

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Component
- **Async Programming**: Kotlin Coroutines and Flow
- **Dependency Injection**: Hilt
- **Authentication**: Firebase Authentication
- **Database**: Firebase Firestore
- **Local Storage**: DataStore Preferences

## Project Structure

```
app/src/main/java/com/tutionapp/
├── data/
│   ├── model/              # Data models (User, Student, Assignment, etc.)
│   └── repository/         # Firestore repository
├── di/                     # Hilt dependency injection modules
├── features/               # Feature-based packages
│   ├── auth/              # Authentication (Login, Register)
│   ├── students/          # Student management
│   ├── assignments/       # Assignment management
│   ├── classes/           # Class management
│   ├── tests/             # Test management
│   ├── profile/           # User profile
│   └── settings/          # App settings
├── navigation/            # Navigation setup
├── ui/                    # UI components and theme
│   ├── theme/            # Material Design 3 theme
│   └── MainScreen.kt     # Main app screen with drawer
└── utils/                # Utility classes
```

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Firebase project setup

### Firebase Setup
1. Create a new Firebase project at [Firebase Console](https://console.firebase.google.com/)
2. Add an Android app to your Firebase project with package name: `com.tutionapp`
3. Download the `google-services.json` file and place it in the `app/` directory
4. Enable Authentication and Firestore Database in Firebase Console

### Installation Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/tuition-app.git
   cd tuition-app
   ```

2. Open the project in Android Studio

3. Replace the placeholder `google-services.json` with your actual Firebase configuration file

4. Build and run the project:
   ```bash
   ./gradlew build
   ```

### Firebase Configuration

1. **Authentication**: 
   - Go to Firebase Console → Authentication → Sign-in method
   - Enable Email/Password sign-in method
   - Optional: Enable Google sign-in for additional authentication options

2. **Firestore Database**:
   - Go to Firebase Console → Firestore Database
   - Create database in test mode initially
   - For production, use the provided `firestore.rules` file in the project root
   - Deploy security rules: `firebase deploy --only firestore:rules`

3. **Project Settings**:
   - Ensure your package name matches: `com.tutionapp`
   - Download the latest `google-services.json` and replace the placeholder file

### Firestore Security Rules (Optional)
For production, consider implementing proper security rules:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can only access their own data
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Authenticated users can access all other collections
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## Building the App

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## Key Components

### Architecture
- **MVVM Pattern**: Separation of concerns with ViewModels managing UI state
- **Clean Architecture**: Clear separation between data, domain, and presentation layers
- **Repository Pattern**: Centralized data access through FirestoreRepository

### State Management
- **StateFlow**: Reactive state management in ViewModels
- **Compose State**: Local UI state management in Composables

### Navigation
- **Navigation Component**: Type-safe navigation between screens
- **Deep Linking**: Support for navigation arguments and routes

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Commit your changes: `git commit -am 'Add new feature'`
4. Push to the branch: `git push origin feature/new-feature`
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@tutionapp.com or open an issue in the repository.

## Acknowledgments

- Firebase for backend services
- Material Design 3 for UI components
- Jetpack Compose team for the modern UI toolkit
