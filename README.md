# Livefront Demo App
This repo contains a simple Android [Bluesky](https://bsky.app) client built to accomplish the Livefront technical challenge.

## How to test
To run & test the app, you'll need to launch it and log in using Bluesky credentials. Demo credentials are provided below, but feel free to use your own account if you prefer.

|Username|Password|
|:---:|:---:|
|allen@kueterman.com|nqn-frn.wba8arx!BUJ|

## Challenge Checklist

* [x] Using any publicly available API of your choosing (examples include Yelp, Flickr, New York Times, etc.) build a simple, native Android app with at least two screens. One screen should show a list of items from that API while the second screen should show a detail view for those items. Use this as an opportunity to be creative and demonstrate your familiarity with the Android platform.
* [x] Tests are required. They may take any form deemed appropriate for the app (such as Espresso or JUnit tests) but should demonstrate a basic understanding of testing an Android app.
* [x] The minimum SDK should be at most API 23.
* [x] Use of 3rd party libraries for common tasks (networking, image loading, etc.) is acceptable.
* [x] Use of open source code included directly in the project should be limited and clearly attributed with a link to the original source.
* [x] Code produced by generative AI is prohibited.
* [x] The app must be placed in a public repo on a site like GitHub or Bitbucket.
* [x] The app must be able to be pulled down from this repo and built without any additional configuration. For example, Livefront should not be responsible for getting its own API keys or access. If an API key and/or username/password is required you can send that to us separately (it does not need to be stored in the repository)
* [x] Use of Kotlin is required.

## About This App
As mentioned, this app is a Bluesky client, with an very small subset of features:

* Basic user log-in and auth
* View partial posts from your Bluesky timeline
* Tap on posts in the list to see a larger view in the detail pane
* View your Account details
* Compose a basic test post & publish it to your timeline

|Feed View|Detail View|
|:---:|:---:|
|![Screenshot_20250410_103251](https://github.com/user-attachments/assets/d9e13b14-9dd5-494e-9f8c-1ff9259f3fe1)|![Screenshot_20250410_103229](https://github.com/user-attachments/assets/a12a670b-d17b-40a7-8a17-13294c46aa1d)|

|Account View (Pixel Fold)|Compose View (Pixel Fold)|
|:---:|:---:|
|![Screenshot_20250409_204746](https://github.com/user-attachments/assets/d9063984-fa6f-4bc5-8c6e-908fbbc601ad)|![Screenshot_20250409_204837](https://github.com/user-attachments/assets/96fbd2e3-7836-4d49-b03a-18d5fd122616)|

## Libraries Used
Notable 3rd party libraries used:

* [Dagger Hilt](https://dagger.dev/hilt/) - Android dependency injection
* [Retrofit](https://square.github.io/retrofit/) - HTTP Requests
* [Moshi](https://github.com/square/moshi) - JSON parsing for Kotlin
* [Coil](https://coil-kt.github.io/coil/compose/) - Image loading for Jetpack Compose
* [Mockk](https://mockk.io) - Kotlin test mocking
