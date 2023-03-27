Calorie App
========================

Project to demonstrate the android architecture along with the server integration.


STRUCTURE
---------
The App is separated into modules with the following structure. Clean architecture is being followed.

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
