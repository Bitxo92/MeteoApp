# MeteoApp

MeteoApp is a simple Java-based weather application that retrieves and displays real-time weather data for a user-provided location. Built using **Java Swing** for the user interface and **FlatLaf** for a modern look and feel, it provides weather details such as temperature, wind speed, and humidity, using data retrieved from a weather API.

## Features

- **Search for Weather**: Users can search for the current weather by entering the name of a city or location.
- **Real-time Weather Data**: The app retrieves live weather conditions such as temperature, wind speed, and humidity.
- **Dynamic Icons**: The weather condition is represented visually with icons for clear skies, clouds, rain, and snow.
- **FlatLaf Look & Feel**: The app uses the FlatLaf library to give the interface a modern, flat aesthetic.
- **Responsive UI**: The UI updates dynamically as the weather information is retrieved from the API.

## Screenshots

![image](https://github.com/user-attachments/assets/5c56198d-064c-4387-8d9c-292e18c01d52)


## Technologies Used

- **Java**: The core language used to build the application.
- **Java Swing**: Used to create the graphical user interface (GUI).
- **FlatLaf**: A modern look and feel for Swing applications.
- **Weather API**: The app makes HTTP requests to a weather API to retrieve real-time weather data. The specific API used can be configured in the code.

## Requirements

- **Java 8 or higher**
- **Maven** or any other build tool (Optional, if you need to manage dependencies)
- **Internet Connection**: The app retrieves weather data via an API, so an active internet connection is required.

## How to Run the App

### Prerequisites

- **JDK (Java Development Kit)**: Ensure you have JDK 8 or above installed. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html).
- **Eclipse IDE** (or any other Java IDE): If you don't have Eclipse, download and install it from [here](https://www.eclipse.org/downloads/).

### Steps to Run

1. **Clone or Download the Project:**

   - If using Git:
     ```bash
     git clone https://github.com/Bitxo92/MeteoApp.git
     ```
   - Alternatively, download the ZIP of the project and extract it.

2. **Open the Project in Eclipse:**
   - Open Eclipse and go to `File` > `Import`.
   - Select `Existing Projects into Workspace`, then browse to the project directory and click `Finish`.

3. **Install Dependencies:**
   - Ensure you have all required external libraries (e.g., `json-simple` for JSON parsing, `FlatLaf` for GUI themes).
   - You can download `json-simple` from [here](https://code.google.com/archive/p/json-simple/downloads) if it’s not already included.

4. **Run the Project:**
   - In Eclipse, right-click on the `Main.java` file  and select `Run As` > `Java Application`.

5. **Enter a Location:**
   - When the app runs, you will see a GUI where you can enter the name of a city or location. Type a location and press the "Get Weather" button to retrieve the current weather data.

### Note

- Ensure you have an active internet connection, as the app fetches live weather data from the Open Meteo API.
- The time zone displayed will be based on the location you entered.

### Troubleshooting

- If you encounter issues with dependencies, make sure the `json-simple` and any other libraries are properly included in your build path.

