# CardInfoChecker
This is an Android app that allows users to enter a credit card BIN (Bank Identification Number) and receive all available information about the card. The app uses Retrofit for handling network requests, Room for storing user search history, and follows the MVVM architecture pattern. Additionally, the app uses coroutines and LiveData/Flow to provide a better user experience.

### App supports both day and night theme

Material day theme        |  Material dark theme
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/106911470/225698471-16e2eb61-d1e3-4c8e-b25a-385b45469418.PNG)  |  ![](https://user-images.githubusercontent.com/106911470/225698536-63def9fd-1f05-4819-a368-57bf4bba475b.PNG)



## Features

    Enter credit card BIN to get information about the card
    Display information such as card brand, card type, and issuer
    Save user search history using Room database
    Retrieve previous search history
    
## Usage

    Enter a valid credit card BIN number in the text field and click "Search by BIN"
    View the information about the card, such as card brand, card type, etc.
    To view previous search history, simply click on right end of input field.

## Credits

The app uses the Binlist API to retrieve credit card information.
