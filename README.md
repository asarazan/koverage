# koverage [![Build Status](https://travis-ci.org/asarazan/koverage.svg?branch=master)](https://travis-ci.org/asarazan/koverage)
100% code coverage for Kotlin data classes

### Get
```gradle
dependencies {
  testImplementation 'net.sarazan:koverage:1.0'
}
```

### Use
Currently Koverage's only feature is data classes. If there are other areas of Kotlin that you find problematic, file an issue and I'll see what I can do.

#### Data Classes
For every data class in your project, create a unit test that calls `Koverage.cover<YOUR_CLASS_HERE>()`. See the app module for an example.

#### TODO
* This is not terribly well-tested. There are probably some use cases where it falls over.
* It would be cool to create a gradle plugin that automatically runs `Koverage.cover` on all detected data classes. PRs welcome!
