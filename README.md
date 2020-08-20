[![](https://jitpack.io/v/kAvEh--/blur_view.svg)](https://jitpack.io/#kAvEh--/blur_view)

A simple library for blur and unblur Views/Images for Android.

## Usage

1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
2. Add the dependency to app build.gradle
```groovy
dependencies {
        implementation 'com.github.kAvEh--:blur_view:1.0.0'
}
```
### BlurView
1. Insert `BlurView` widget in your layout.
```xml
<com.kaveh.blurview.BlurView
        android:id="@+id/blurView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:srcCompat="Your Image" />
```
2. Set the BlurRadius of image (Must be Float between 0-25)
```kotlin
    findViewById<BlurView>(R.id.blurView).blurRadius = progress / 4F
    findViewById<BlurView>(R.id.blurView).blur()
```
3. For unblurring the image just set the radius to zero
```kotlin
     findViewById<BlurView>(R.id.blurView).blurRadius = 0F
     findViewById<BlurView>(R.id.blurView).blur()
 ```
### Blur Everything
1. Just call blur function on ViewGroup
```kotlin
    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
    viewGroup.blur(this@BlurActivity, 20F)
```
2. For removing blur effect from ViewGroup
```kotlin
    val viewGroup = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
    viewGroup.remove()
```

## Requirements
Android 4.4+ (API 19)

## Developed By
* Kaveh Fazaeli - <kaveh.fazaeli@gmail.com>

## License

    Copyright 2020 kAvEh--

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

