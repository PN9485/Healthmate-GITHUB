# Healthmate Android App

A comprehensive health and fitness Android application that provides personalized exercise and nutrition guidance using modern mobile development techniques.

## Features

- **User Profile & BMI Calculation**: Collect user information and calculate BMI
- **Food Suggestion System**: Provides nutritional information and portion recommendations based on BMI
- **Exercise Suggestion System**: Offers targeted workouts for specific body parts
- **Integration with External APIs**: Uses Open Food Facts API for food data and ExerciseDB from RapidAPI for exercise information
- **Local Database Fallback**: Stores essential information locally for offline access

## Technology Stack

- Java-based Android native application
- Material Design UI components
- RapidAPI integration
- SQLite database for local storage
- Retrofit for API communications
- RecyclerView and CardView for dynamic content display

## Building the App

This project is configured to build automatically using GitHub Actions. Follow these steps to build the APK:

1. **Push the code to GitHub**:
   ```
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin YOUR_GITHUB_REPOSITORY_URL
   git push -u origin main
   ```

2. **GitHub Actions will automatically build the APK** when you push to the main branch.

3. **Download the APK** from the GitHub Actions artifacts section after the workflow completes.

## API Keys

This application requires an API key from RapidAPI (ExerciseDB) to access the exercise database. The key should be added as a secret in the GitHub repository settings with the name `RAPID_API_KEY`.

## Project Structure

- `app/src/main/java/com/healthmate/app/`: Main application code
  - `adapter/`: RecyclerView adapters
  - `api/`: API service interfaces
  - `database/`: SQLite database implementation
  - `model/`: Data models
  - `util/`: Utility classes
- `app/src/main/res/`: Resources
  - `layout/`: UI layouts
  - `drawable/`: Images and drawable resources
  - `values/`: Strings, colors, and styles
- `app/src/main/AndroidManifest.xml`: Application manifest

## Development Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run on an emulator or physical device

## License

This project is licensed under the MIT License - see the LICENSE file for details.