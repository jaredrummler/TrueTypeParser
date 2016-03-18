/*
 * Copyright (C) 2016 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/* $Id: TTFFile.java 1395925 2012-10-09 09:13:18Z jeremias $ */

package com.jaredrummler.truetypeparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Reads a TrueType file or a TrueType Collection. The TrueType spec can be found at the Microsoft.
 * Typography site: http://www.microsoft.com/truetype/
 */
public class TTFFile {

  /**
   * Reads a TTF file
   *
   * @param file
   *     The font file
   * @return The TrueType file
   * @throws IOException
   *     if an IO error occurs
   */
  public static TTFFile open(File file) throws IOException {
    return open(new FileInputStream(file));
  }

  /**
   * Reads a TTF file from an InputStream
   *
   * @param is
   *     InputStream to read from
   * @return The TrueType file
   * @throws IOException
   *     if an IO error occurs
   */
  public static TTFFile open(InputStream is) throws IOException {
    TTFFile ttfFile = new TTFFile();
    ttfFile.readFont(new FontFileReader(is));
    return ttfFile;
  }

  private final Set<String> familyNames = new HashSet<>();
  /** The FontFileReader used to read this TrueType font. */
  private FontFileReader fontFile;
  /** Table directory */
  private Map<TTFTableName, TTFDirTabEntry> dirTabs;
  private String postScriptName = "";
  private String fullName = "";
  private String notice = "";
  private String subFamilyName = "";
  private int weightClass;

  TTFFile() {

  }

  /**
   * Returns the font family names of the font.
   *
   * @return Set The family names (a Set of Strings)
   */
  public Set<String> getFamilyNames() {
    return familyNames;
  }

  /**
   * Returns the full name of the font.
   *
   * @return String The full name
   */
  public String getFullName() {
    return fullName;
  }

  public String getNotice() {
    return notice;
  }

  /**
   * Returns the PostScript name of the font.
   *
   * @return String The PostScript name
   */
  public String getPostScriptName() {
    return postScriptName;
  }

  /**
   * Returns the font sub family name of the font.
   *
   * @return String The sub family name
   */
  public String getSubFamilyName() {
    return subFamilyName;
  }

  /**
   * Returns the weight class of this font. Valid values are 100, 200....,800, 900.
   *
   * @return the weight class value (or 0 if there was no OS/2 table in the font)
   */
  public int getWeightClass() {
    return weightClass;
  }

  /**
   * Read Table Directory from the current position in the FontFileReader and fill the global
   * HashMap dirTabs with the table name (String) as key and a TTFDirTabEntry as value.
   *
   * @throws IOException
   *     in case of an I/O problem
   */
  private void readDirTabs() throws IOException {
    fontFile.readTTFLong(); // TTF_FIXED_SIZE (4 bytes)
    int ntabs = fontFile.readTTFUShort();
    fontFile.skip(6); // 3xTTF_USHORT_SIZE

    dirTabs = new HashMap<>();
    TTFDirTabEntry[] pd = new TTFDirTabEntry[ntabs];

    for (int i = 0; i < ntabs; i++) {
      pd[i] = new TTFDirTabEntry();
      String tableName = pd[i].read(fontFile);
      dirTabs.put(TTFTableName.getValue(tableName), pd[i]);
    }
    dirTabs.put(TTFTableName.TABLE_DIRECTORY, new TTFDirTabEntry(0L, fontFile.getCurrentPos()));
  }

  /**
   * Reads the font using a FontFileReader.
   *
   * @param in
   *     The FontFileReader to use
   * @throws IOException
   *     In case of an I/O problem
   */
  void readFont(FontFileReader in) throws IOException {
    fontFile = in;
    readDirTabs();

    TTFDirTabEntry os2Entry = dirTabs.get(TTFTableName.OS2);
    if (os2Entry != null) {
      seekTab(fontFile, TTFTableName.OS2, 0);
      fontFile.readTTFUShort();
      fontFile.skip(2); //xAvgCharWidth
      weightClass = fontFile.readTTFUShort();
    }

    readName();
  }

  /**
   * Read the "name" table.
   *
   * @throws IOException
   *     In case of a I/O problem
   */
  private void readName() throws IOException {
    seekTab(fontFile, TTFTableName.NAME, 2);
    int i = fontFile.getCurrentPos();
    int n = fontFile.readTTFUShort();
    int j = fontFile.readTTFUShort() + i - 2;
    i += 2 * 2;

    while (n-- > 0) {
      fontFile.seekSet(i);
      int platformID = fontFile.readTTFUShort();
      int encodingID = fontFile.readTTFUShort();
      int languageID = fontFile.readTTFUShort();

      int k = fontFile.readTTFUShort();
      int l = fontFile.readTTFUShort();

      if (((platformID == 1 || platformID == 3) && (encodingID == 0 || encodingID == 1))) {
        fontFile.seekSet(j + fontFile.readTTFUShort());
        String txt;
        if (platformID == 3) {
          txt = fontFile.readTTFString(l, encodingID);
        } else {
          txt = fontFile.readTTFString(l);
        }
        switch (k) {
          case 0:
            if (notice.length() == 0) {
              notice = txt;
            }
            break;
          case 1: // Font Family Name
          case 16: // Preferred Family
            familyNames.add(txt);
            break;
          case 2:
            if (subFamilyName.length() == 0) {
              subFamilyName = txt;
            }
            break;
          case 4:
            if (fullName.length() == 0 || (platformID == 3 && languageID == 1033)) {
              fullName = txt;
            }
            break;
          case 6:
            if (postScriptName.length() == 0) {
              postScriptName = txt;
            }
            break;
          default:
            break;
        }
      }
      i += 6 * 2;
    }
  }

  /**
   * Position inputstream to position indicated in the dirtab offset + offset
   *
   * @param in
   *     font file reader
   * @param tableName
   *     (tag) of table
   * @param offset
   *     from start of table
   * @return true if seek succeeded
   * @throws IOException
   *     if I/O exception occurs during seek
   */
  private boolean seekTab(FontFileReader in, TTFTableName tableName, long offset)
      throws IOException {
    TTFDirTabEntry dt = dirTabs.get(tableName);
    if (dt == null) {
      return false;
    } else {
      in.seekSet(dt.getOffset() + offset);
    }
    return true;
  }

}
