# koverage [![Build Status](https://travis-ci.org/asarazan/koverage.svg?branch=master)](https://travis-ci.org/asarazan/koverage)
100% code coverage for Kotlin data classes and other generated kotlin code.

It's a known problem that Kotlin generates a lot of code under the hood, and that standard coverage techniques are not equipped to ignore these paths. This library is a brute force solution that forces the traversal of those paths at test-time, leading to 100% coverage numbers for generated logic.

### Get
```gradle
dependencies {
  testImplementation 'net.sarazan:koverage:1.1.1'
}
```

### Use
For every supported Kotlin class in your project, create a unit test that calls `Koverage.cover<YOUR_CLASS_HERE>()`. See the app module for an example.

### Supported Use Cases
We've tried to hit the most broad use cases, and still fail gracefully if things don't work out. We've confirmed basic compatibility for generated code in the following cases:
* Field-backed properties
* Enums
* Data Classes
* Companion Objects
* Singleton Objects

#### TODO
* This is not terribly well-tested. There are probably some use cases where it falls over.
* I suspect it does not do the right thing on field-backed properties with non-default getters/setters.
* It would be cool to create a gradle plugin that automatically runs `Koverage.cover` on all detected data classes. PRs welcome!
