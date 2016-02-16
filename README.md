# TrueTypeParser
TrueType Font Parser for Android

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

About
-----

Based on [Apache FOP](http://xmlgraphics.apache.org/fop/)

License
--------

    Copyright (C) 2015, Jared Rummler

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
