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

package com.jaredrummler.fontreader.util;

import com.jaredrummler.fontreader.complexscripts.fonts.GlyphContextTester;

/**
 * <p>Interface for providing script specific context testers.</p>
 *
 * <p>This work was originally authored by Glenn Adams (gadams@apache.org).</p>
 */
public interface ScriptContextTester {

  /**
   * Obtain a glyph context tester for the specified feature.
   *
   * @param feature
   *     a feature identifier
   * @return a glyph context tester or null if none available for the specified feature
   */
  GlyphContextTester getTester(String feature);

}
