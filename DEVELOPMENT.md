# Development Guide

## Quick Start

### 1. Firebase Setup (Required)
Before running the app, you must configure Firebase:

1. **Create Firebase Project**:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project: "Tuition Management App"
   - Add Android app with package name: `com.tutionapp`

2. **Download Configuration**:
   - Download `google-services.json`
   - Replace the placeholder file in `app/google-services.json`

3. **Enable Services**:
   - Authentication → Sign-in method → Email/Password (Enable)
   - Firestore Database → Create database (Start in test mode)

### 2. Build the App

```bash
# Clean build
gradlew.bat clean

# Build debug APK
gradlew.bat assembleDebug

# Build and install to device
gradlew.bat installDebug
```

### 3. Run Tests

```bash
# Unit tests
gradlew.bat test

# Instrumentation tests
gradlew.bat connectedAndroidTest
```

## Development Workflow

### Adding New Features

1. **Create Feature Package**:
   ```
   features/
   ├── newfeature/
   │   ├── NewFeatureScreen.kt
   │   ├── NewFeatureViewModel.kt
   │   └── NewFeatureState.kt
   ```

2. **Update Navigation**:
   - Add route to `Screen.kt`
   - Update `TutionNavGraph.kt`
   - Add to drawer in `MainScreen.kt`

3. **Add Data Model** (if needed):
   - Create model in `data/model/`
   - Add repository methods in `FirestoreRepository.kt`
   - Update Firestore rules if needed

### Code Style Guidelines

- **Naming**: Use descriptive names for variables and functions
- **Composition**: Keep Composables small and focused
- **State**: Use StateFlow for ViewModels, remember for local state
- **Error Handling**: Always handle loading and error states
- **Validation**: Use ValidationUtils for form validation

## Architecture Overview

### MVVM Pattern
```
UI Layer (Compose) ← ViewModel ← Repository ← Firebase
```

### Dependency Injection (Hilt)
- `@HiltAndroidApp` on Application class
- `@AndroidEntryPoint` on Activity and ViewModels
- Modules in `di/` package

### Data Flow
1. UI triggers action
2. ViewModel processes action
3. Repository makes Firebase call
4. StateFlow updates UI reactively

## Common Issues

### Build Issues
- **Gradle sync fails**: Ensure `google-services.json` is valid
- **Compilation errors**: Check Kotlin version compatibility
- **Missing dependencies**: Run `gradlew.bat --refresh-dependencies`

### Firebase Issues
- **Authentication fails**: Check Firebase config and internet connection
- **Firestore permission denied**: Update security rules
- **Network errors**: Ensure device has internet access

### UI Issues
- **Theme not applying**: Check if theme preferences are loaded correctly
- **Navigation issues**: Verify route names in Screen.kt
- **State not updating**: Ensure StateFlow.collect is used correctly

## Debugging

### Enable Firebase Debug Logging
```kotlin
FirebaseFirestore.setLoggingEnabled(true)
```

### View Logs
```bash
adb logcat | grep "TutionApp"
```

### Common Log Tags
- `AuthViewModel`: Authentication-related logs
- `FirestoreRepository`: Database operations
- `PreferencesManager`: Settings and preferences

## Testing

### Unit Tests
Located in `src/test/java/`:
- ViewModel logic tests
- Utility function tests
- Validation tests

### UI Tests
Located in `src/androidTest/java/`:
- Screen composition tests
- Navigation tests
- Integration tests

### Manual Testing Checklist
- [ ] User registration and login
- [ ] Add/delete students, assignments, classes, tests
- [ ] Theme switching
- [ ] Navigation drawer functionality
- [ ] Data persistence across app restarts

## Performance Tips

### Optimize Firestore Usage
- Use pagination for large lists
- Implement offline persistence
- Cache frequently accessed data

### Compose Performance
- Use `remember` for expensive calculations
- Avoid recomposition with stable parameters
- Use `LaunchedEffect` properly

### Memory Management
- Properly dispose of Flow collections
- Use appropriate lifecycle-aware components
- Monitor memory usage during development
