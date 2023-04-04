# Calendar-Custom-library
# Gradle
Step 1. Add the JitPack repository to your build file.\
Add it in your root build.gradle at the end of repositories.

```
repositories {
    maven { url "https://jitpack.io" }
  }
```
 
  
  Step 2. Add the dependency
  
  ```
 dependencies {
	implementation 'com.github.asadej0951:Calendar-Custom-library:1.1.9'
	}
```
 #Maven
 ```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

[![](https://jitpack.io/v/asadej0951/Calendar-Custom-library.svg)](https://jitpack.io/#asadej0951/Calendar-Custom-library)

#Function xml example
 ```
    <com.example.calendar_custom_library.viewCalender.CalenderCustom
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:color_text_day_calender="#03A9F4"
        app:color_text_name_day="@color/purple_200"
        app:color_text_title="@color/orange_new"
        app:color_mark_day="@color/black"
        app:color_line_name_day="@color/orange_new"
        app:color_text_mark_day_calender="@color/orange_new" />
```
