@echo off
echo ================================================
echo    Tuition Management App - Setup Guide
echo ================================================
echo.

echo 1. ANDROID STUDIO SETUP
echo ------------------------------------------------
echo Please ensure Android Studio is installed from:
echo https://developer.android.com/studio
echo.
echo After installation, the Android SDK should be at:
echo %LOCALAPPDATA%\Android\Sdk
echo.

echo 2. FIREBASE SETUP (REQUIRED)
echo ------------------------------------------------
echo Your app currently has placeholder Firebase config.
echo To run the app, you MUST:
echo.
echo a) Go to https://console.firebase.google.com/
echo b) Create a new project named "Tuition Management App"
echo c) Add an Android app with package name: com.tutionapp
echo d) Download google-services.json
echo e) Replace the file at: app\google-services.json
echo f) Enable Authentication ^(Email/Password^)
echo g) Enable Firestore Database
echo.

echo 3. BUILD AND RUN
echo ------------------------------------------------
echo Once setup is complete, run:
echo gradlew.bat assembleDebug
echo.
echo Or open the project in Android Studio and click Run
echo.

echo 4. TESTING WITHOUT DEVICE
echo ------------------------------------------------
echo Create an Android Virtual Device ^(AVD^) in Android Studio:
echo Tools ^> AVD Manager ^> Create Virtual Device
echo.

pause
