/*
 * Copyright © 2017 jjYBdx4IL (https://github.com/jjYBdx4IL)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jjYBdx4IL.audio.soundfiles;

import static org.junit.Assert.*;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceBundleTest {

    @Test
    public void testCount() throws IOException {
        int counter = 0;
        for (ResourceBundle item : ResourceBundle.values()) {
            counter++;
        }
        assertTrue(counter > 5);
    }

    @Test
    public void testAsUrl() throws IOException {
        for (ResourceBundle item : ResourceBundle.values()) {
            assertNotNull(item.asUrl());
        }
    }

    @Test
    public void testAsStream() throws IOException {
        for (ResourceBundle item : ResourceBundle.values()) {
            try (InputStream is = item.asStream()) {
                byte[] bytes = IOUtils.toByteArray(is);
                assertNotNull(bytes);
                assertTrue(bytes.length > 10);
            }
        }
    }
}
