# Memorize  
 
Japanese-English-Mongolian dictionary. It lets you find words, kanji and more quickly and easily. 

<!--[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png"
      alt="Download from Google Play"
      height="80">](https://play.google.com/store/apps/details?id=mn.opengineer.memorize)
-->
[<img src=".github/direct-apk-download.png"
      alt="Direct apk download"
      height="80">](https://github.com/opengineer/memorize/releases/latest)


| [Features][] | [Screenshots][] | [Architecture][] | [Authors][] | [Contributors][] | [License][] |
|---|---|---|---|---|---|

## Features 

- 100% functional offline
- Flash cards
- Quiz
- Great search 
- Find common and more often used words more easily
- Minimalist structure allows for efficient studying and reduces distractions

## Screenshots

| Home | Search | Details | Quiz | Flash cards |
|:-:|:-:|:-:|:-:|:-:|
| ![Home](/.github/home.jpg?raw=true) | ![Search](/.github/search.jpg?raw=true) |![details](/.github/details.jpg?raw=true) |![Quiz](/.github/quiz.jpg?raw=true) |![flash](/.github/flash-card.jpg?raw=true) |

## Architecture

The architecture of our Android apps is based on the [MVP](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) (Model View Presenter) pattern.

![](/.github/mvp-arch.jpeg?raw=true)

* __Helpers (Model)__: A set of classes, each of them with a very specific responsibility. Their function can range from talking to APIs or a database to implementing some specific business logic. Every project will have different helpers but the most common ones are:
	- __DatabaseHelper__: It handles inserting, updating and retrieving data from a local SQLite database, using [Room](https://developer.android.com/topic/libraries/architecture/room.html)
	- __PreferencesHelper__: It saves and gets data from `SharedPreferences`.

* __Data Manager (Model)__: It's a key part of the architecture. It keeps a reference to every helper class and uses them to satisfy the requests coming from the presenters. Its methods make extensive use of [Room](https://developer.android.com/topic/libraries/architecture/room.html), transform or filter the output coming from the helpers in order to generate the desired output ready for the Presenters. It returns observables that emit data models.

* __Presenters__: Subscribe to observables provided by the `DataManager` and process the data in order to call the right method in the View.

* __Activities, Fragments, ViewGroups (View)__: Standard Android components that implement a set of methods that the Presenters can call. They also handle user interactions such as clicks and act accordingly by calling the appropriate method in the Presenter. These components also implement framework-related tasks such us managing the Android lifecycle, inflating views, etc.

## Authors

[![Turtuvshin Byambaa](https://avatars2.githubusercontent.com/u/9257227?s=80)](https://github.com/tortuvshin) | [![Enkhbayar Doljinsuren](https://avatars1.githubusercontent.com/u/12738721?s=80)](https://github.com/doljko) 
---|---
[Turtuvshin](https://github.com/tortuvshin) | [Doljinsuren](https://github.com/doljko) 

## Contributors

You may contribute in several ways like creating new features, fixing bugs, improving documentation and examples
or translating any document here to your language. [Find more information in CONTRIBUTING.md](CONTRIBUTING.md).
<a href="https://github.com/opengineer/memorize/graphs/contributors">Contributors</a>

## License

> Copyright (C) 2019 Opengineer.  
> Memorize is open-sourced software licensed under the [MIT](https://opensource.org/licenses/MIT) license.  
> (See the [LICENSE](https://github.com/opengineer/memorize/blob/master/LICENSE) file for the whole license text.)

**[⬆ back to top](#memorize)**

[Features]:#features
[Screenshots]:#screenshots
[Architecture]:#architecture
[Authors]:#authors
[Contributors]:#contributors
[License]:#license
