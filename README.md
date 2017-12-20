# koverage
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
For every data class in your project, create a unit test that calls `DataClasses.cover<YOUR_CLASS_HERE>()`. See the app module for an example.

#### TODO
* This is not well tested at all. There are probably some use cases where it falls over. I suspect it won't hit 100% if your data class contains other data classes.
* It would be cool to create a gradle plugin that automatically runs `DataClasses.cover` on all detected data classes. PRs welcome!
