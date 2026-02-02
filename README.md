# chortle
Right-To-Left script support for libGDX (meaning, Arabic and Hebrew).

## Note
This is nearly untested except for one small test,
[RtlTest.java](src/test/java/com/github/tommyettinger/chortle/test/RtlTest.java) . You probably will want to read that
test's code to see how this library is used. In short,
```java
Chortle chortle = new Chortle();
String translated = chortle.getText(myText);
```

Where `myText` is a String that may contain any of Arabic, Hebrew, and/or left-to-right text. Newlines in `myText` will
still create new lines in the result, and if the text was right-to-left before the newline, it will still be
right-to-left after the newline.

## Get
In your core/build.gradle file, in its last `dependencies` block:

```groovy
dependencies {
    api 'com.github.tommyettinger:chortle:05b713979d'
}
```

If you use GWT, in your html/build.gradle file, in its last `dependencies` block:

```groovy
dependencies {
    implementation 'com.github.tommyettinger:chortle:05b713979d:sources'
}
```
and also only if you use GWT, in your `GdxDefinition.gwt.xml` file, with other `inherits` lines:
```xml
<inherits name="com.github.tommyettinger.chortle" />
```

You can substitute any commit hash from [JitPack.io's page for chortle](https://jitpack.io/#tommyettinger/chortle)
instead of `05b713979d` . There may be newer commits. Avoid `-SNAPSHOT` as a version; it is not replicable, and may
break or change at any point in time.

## License

[Apache License v2.0](LICENSE).

## Thanks

This is closely based on
[iibrahimbakr's libGDX-RTL-Language repo](https://github.com/iibrahimbakr/libGDX-RTL-Language),
which is also licensed under the Apache License v2.0 . Some changes have been applied to that
repo, ranging from optimizations, to making LTR text (including digits and Latin/Greek/Cyrillic-script
text) able to be placed inside blocks of Arabic text while keeping the LTR text's directionality.