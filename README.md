Calorie App
========================

Project to demonstrate the android architecture along with the server integration.

DEMO
--------

App demo can be seen [here](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/blob/develop/android-app/app-demo.mp4).


FEATURES
--------
* [Login](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/tree/develop/android-app/feature/login)
* [Food](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/tree/develop/android-app/feature/food)
* [Admin](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/tree/develop/android-app/feature/admin)

STRUCTURE
---------
The App is separated into modules with the following structure.

    +----------------------------------------------------+
    |                                                    |
    | Calorie-App                                        |
    |                                                    |
    +----------------------------------------------------+
    +---------------+ +---------------+ +----------------+
    |               | |               | |                |
    |               | |               | |                |
    |  Login        | |  Admin        | | Food           |
    |               | |               | |                |
    +---------------+ +---------------+ +----------------+
    +----------------------------------------------------+
    |                                                    |
    |                      Core                          |
    |                                                    |
    +----------------------------------------------------+


INSTALLATION
------------
* [Android App](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/blob/develop/android-app/README.md)
* [GraphQL Backend](https://git.toptal.com/screening/Navjot-Singh-Bedi/-/blob/develop/graphql-api/README.md)