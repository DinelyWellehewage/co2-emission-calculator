# CO2 Emission Calculator

The **Co2 Emission Calculator** is a Java-based application designed to estimate the CO2-equivalent emissions produced when travelling a specified distance using a chosen mode of transportation.

---

## Features

- **Calculate CO2 Emissions**: Computes emissions based on distance and transportation mode.
- **Flexible Transport Modes**: Supports multiple modes of transport with customizable emission values.

## Technologies Used

- **Programming Language**: Java(11 or later)
- **API**: OpenRouteService API
- **Build Tool**: Maven
- **Testing Framework**: JUnit
- **Version Control**: Git

---

## Getting Started

### Prerequisites

- Java Development Kit(JDK) 11 or later
- Maven 3.x
- OpenRouteService API key (obstainable from [OpenRouteService](https://openrouteservice.org/))

### 🚀 Build
Follow the steps outlined below to get started.

1. CLone the repository
```bash
git clone https://github.com/DinelyWellehewage/co2-emission-calculator.git
```
2. Navigate to the Project Directory
```bash
cd co2-emission-calculator
```
3. Build the Application
```bash
mvn clean install
```
4. Configure API key

- Register for an API key on [OpenRouteService](https://openrouteservice.org/)
- Add API key as an environment variable


5.Run the Application
```bash
java -jar co2-calculator.jar --start <start city> --end <end city> --transportation-method <transportation method>
```
### Usage
1. Input the source and destination cities
2. Select a mode of transportation from the list
3. Select the coordinates for cities from displayed coordinates
4. The application displays the CO2-equivalent emission

### Command-line Arguments
- `--start=START_CITY`
- `--end=END_CITY`
- `--transportation-method=TRANSPORT_METHOD`

Command-line arguments can be put in any order and either use an equal sign(=) or a space ( ) between key and value.


#### Valid Options for Transportation Method

| Vehicle Type     | Transportation Method                                                                       |
|------------------|---------------------------------------------------------------------------------------------|
| Small Cars       | `diesel-car-small`, `petrol-car-small`, `plugin-hybrid-car-small`, `electric-car-small`     |
| Medium Cars      | `diesel-car-medium`, `petrol-car-medium`, `plugin-hybrid-car-medium`, `electric-car-medium` |
| Large Cars       | `diesel-car-large`, `petrol-car-large`, `plugin-hybrid-car-large`, `electric-car-large`     |
| Public Transport | `bus-default`, `train-default`                                                              |


![CO2-Calculator](https://github.com/user-attachments/assets/851b0f8c-65b0-4cb2-86d2-204a7b08a1f0)

---
### Future Enhancements
- Interactive Web Interface:Transition to a web-based UI for broader accessibility
- Multi-Language Support: Enable inputs and outputs in multiple languages
- Historical Data Storage: Allows users to save and analyze past calculations
- Multiple Destination Support: Add support for multiple stops and complex routes

### Contact

**Author:** Dinely Shanuka

**Email:** [dinelywh78@gmail.com](dinelywh78@gmail.com)
