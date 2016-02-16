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

/* $Id$ */

package com.jaredrummler.fontreader.util;

/**
 * <p>Interface for testing glyph properties according to glyph identifier.</p>
 *
 * <p>This work was originally authored by Glenn Adams (gadams@apache.org).</p>
 */
public interface GlyphTester {

    /**
     * Perform a test on a glyph identifier.
     * @param gi glyph identififer
     * @param flags that apply to lookup in scope
     * @return true if test is satisfied
     */
    boolean test(int gi, int flags);

}
