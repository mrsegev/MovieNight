# MovieNight

### Summary: ###
MovieNight is a sample Android application that uses the clean architecture approach and is written in Kotlin.

P.S: [I've written a blog post about this project.](https://goo.gl/KoVEh5)

![Screenshots](https://github.com/mrsegev/MovieNight/blob/master/screenshots/screens.jpg)

### The Motivation behind the app: ###
During the work on this app, my goal was to leave my comfort zone as much as I could, intentionally tackling subjects I'm less familiar with or, at times, entirely new to me.
Some of those subjects include:
- The Kotlin programming language
- Clean architecture
- Test-driven development
- Dagger2
- Android architecture components (ViewModels, LiveData, Room, etc.)

### How to run the app: ###
This app uses [The MovieDB public API](https://developers.themoviedb.org/3/getting-started/introduction).
Register (its free) and grab your API key, 
then paste it inside: `.../res/values/api_key.xml`

### //TODO: ###
- Few fixes and tweaks
- Add UI tests
- Maybe expand the app functionality

### Used libraries: ###
- [RxJava2](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit2](https://github.com/square/retrofit)
- [AndroidTagView](https://github.com/whilu/AndroidTagView)
- [Picasso](https://github.com/square/picasso)
- [Leakcanary](https://github.com/square/leakcanary)
- [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)

### License: ###
~~~~
Copyright 2018 Yossi Segev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~~
