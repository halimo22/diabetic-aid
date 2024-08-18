# Diabetic Aid

**Diabetic Aid** is a Java-based Android application designed to assist diabetics in managing their daily glucose readings, calculating averages, setting reminders, and calculating carbohydrate intake using a food calculator. The app integrates Firebase Authentication for secure login and registration and utilizes SQLite for local storage of data, including glucose readings and a food database.

## Features

- **Glucose Readings:**
  - Store daily glucose readings.
  - View historical data.
  - Calculate and display averages over a specified period.

- **Carb Calculator:**
  - Integrated food database for retrieving nutritional information.
  - Calculate carbohydrate amounts based on food intake.
  - Easily add foods from the database to calculate total carbs.

- **Reminders:**
  - Set and manage reminders for glucose testing, medication, meals, and other health-related activities.

- **User Authentication:**
  - Secure login and registration using Firebase Authentication.
  - Personalized data for each user.

## Technology Stack

- **Java**: The primary programming language for Android development.
- **Firebase Authentication**: For user login and registration.
- **SQLite**: For local storage of glucose readings, averages, and the food database.

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/diabetic-aid.git
   cd diabetic-aid
2. **Open in Android Studio:**
   - Open Android Studio.
   - Select "Open an existing Android Studio project."
   - Navigate to the cloned repository folder and open it.
3. **Set Up Firebase Authentication:**
   - Create a Firebase project [here](https://console.firebase.google.com/).
   - Add your Android app to the Firebase project.
   - Download the `google-services.json` file and place it in the `app/` directory.

4. **Build the Project:**
   - Build the project in Android Studio.
   - Run the app on an Android device or emulator.

## Usage

1. **Sign Up / Login:**
   - Users must first register or log in using Firebase Authentication.

2. **Enter Glucose Readings:**
   - Navigate to the "Glucose Readings" section to enter and save daily readings.
   - View historical data and calculate averages.

3. **Calculate Carbs:**
   - Use the food calculator to select foods from the database.
   - Enter quantities to calculate the total carbohydrate intake.

4. **Set Reminders:**
   - Navigate to the "Reminders" section to set up custom reminders for various activities.

## Contact

For any inquiries or issues, please contact seifeldin.ibrahim@gmail.com or open an issue in this repository.

