# Aviator Tracker
![demo_main](https://github.com/rohanny1114/AviatorTracker/assets/101334680/ce061abf-1ea9-4eb6-b2aa-b19bc8adea13)<br>
This android application helps users track aviators, save the selected flight information, and delete saved flight information.

## Description
The user can enter the airport code to search for flights that depart from the airport, save and delete the selected flight information.

## Tech used
- Java
- Room Database
- Aviationstack API


## function
- Search flights: The user can enter the IATA airport code, for instance, YOW is for Ottawa, and EWR is for Newark. The results will show up to 100 flights on the day including schedule status, flight code, airline, and name of the arrival airport. <br>
![func_search](https://github.com/rohanny1114/AviatorTracker/assets/101334680/8a94db35-43e2-4e34-a7cd-ae5d490c1b19)<br>
- Save flight: The user can click one flight on the list and save it to the database on the device. The detailed information about the selected flight is shown on the fragment including the information of airport name, terminal, gate, original and updated schedule on the departure and arrival airports.<br>
![func_save](https://github.com/rohanny1114/AviatorTracker/assets/101334680/f8d3d3b7-a8c9-4565-bb07-ab134644bc20)<br>
- View saved flights: The user can list the flights that have been saved to the database on the device. The detailed information is shown in the fragment.<br>
![func_saved](https://github.com/rohanny1114/AviatorTracker/assets/101334680/23f870d8-5cfe-418e-b080-ddaf5e1c32e8)<br>
- Delete flight: The user can delete the saved flight from the database.<br>
![func_delete](https://github.com/rohanny1114/AviatorTracker/assets/101334680/537f9ce3-d78a-4219-823e-6093b5c72595)<br>
- Help icon: The help icon on the main activity displays an AlertDialog with instructions for how to use the interface.<br>
![func_help](https://github.com/rohanny1114/AviatorTracker/assets/101334680/dd89bd92-6e4a-4e88-8450-39b8b3fa714b)<br>


## Resource
- [Aviationstack] https://aviationstack.com/
