# TrueTypeParser
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jaredrummler/truetypeparser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.jaredrummler/truetypeparser) [![License](http://img.shields.io/:license-apache-blue.svg)](LICENSE) [![API](https://img.shields.io/badge/API-7%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=7)

TrueType Font Parser for Android based on [Apache FOP](http://xmlgraphics.apache.org/fop/).

Download
--------

Download [the latest AAR](https://repo1.maven.org/maven2/com/jaredrummler/truetypeparser/1.0.0/truetypeparser-1.0.0.aar) or grab via Gradle:

```groovy
compile 'com.jaredrummler:truetypeparser:1.0.0'
```
or Maven:
```xml
<dependency>
  <groupId>com.jaredrummler</groupId>
  <artifactId>truetypeparser</artifactId>
  <version>1.0.0</version>
  <type>aar</type>
</dependency>
```

Usage
-----

```java
TTFFile ttfFile = TTFFile.open(getAssets().open("fonts/your-font.ttf"));
String name = ttfFile.getFullName();
String family = ttfFile.getSubFamilyName();
int fontWeight = ttfFile.getWeightClass();
String copyright = ttfFile.getCopyrightNotice();
Map<Integer, Map<Integer, Integer>> kerning = ttfFile.getKerning();
```

TrueTypeParser Light
--------------------

If you don't need to read kerning pairs consider using the light version. The light version will read the font file's name, families, weight, and notice. The method count is much smaller.

```groovy
compile 'com.jaredrummler:truetypeparser-light:1.0.0'
```

License
--------

    Copyright (C) 2016 Jared Rummler

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
