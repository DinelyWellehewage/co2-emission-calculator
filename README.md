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
Valid options for --transportation-method are:

Small cars:
- `diesel-car-small`
- `petrol-car-small`
- `plugin-hybrid-car-small`
- `electric-car-small`

Medium cars:
- `diesel-car-medium`
- `petrol-car-medium`
- `plugin-hybrid-car-medium`
- `electric-car-medium`

Large cars:
- `diesel-car-large`
- `petrol-car-large`
- `plugin-hybrid-car-large`
- `electric-car-large`

Bus
- `bus-default`

Train
- `train-default`


Example:

```text
CO2 Emission Calculator
-------------------------
Entered Start City: Hamburg
Entered End City: Berlin
Entered Transporation method: diesel-car-medium

Start City Location Information
Hamburg Coordinates 
Available Coordinates with Country and Region
0 - [10.007046, 53.576158] Country: Germany ; Region: Hamburg
1 - [-87.953926, 43.316682] Country: United States ; Region: Wisconsin
2 - [-78.832812, 42.718672] Country: United States ; Region: New York
Select a coordinate (0 to 10): (enter index):
0

End City Location Information
Berlin Coordinates 
Available Coordinates with Country and Region
0 - [13.407032, 52.524932] Country: Germany ; Region: Berlin
1 - [-88.53333, 13.5] Country: El Salvador ; Region: Usulután
2 - [-71.259903, 44.487158] Country: United States ; Region: New Hampshire
Select a coordinate (0 to 10): (enter index):
0
Your trip caused 49.2kg of CO2-equivalent.
```
![Uploading CO2-calculator.gif…]()


---
### Future Enhancements
- Interactive Web Interface:Transition to a web-based UI for broader accessibility
- Multi-Language Support: Enable inputs and outputs in multiple languages
- Historical Data Storage: Allows users to save and analyze past calculations
- Multiple Destination Support: Add support for multiple stops and complex routes

### Contact

**Author:** Dinely Shanuka

**Email:** [dinelywh78@gmail.com](dinelywh78@gmail.com)
