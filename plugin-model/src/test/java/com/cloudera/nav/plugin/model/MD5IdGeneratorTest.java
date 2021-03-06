/*
 * Copyright (c) 2015 Cloudera, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera.nav.plugin.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.*;

public class MD5IdGeneratorTest {

  @Test
  public void testBasic() {
    String hash = MD5IdGenerator.generateIdentity("foo");
    assertEquals(MD5IdGenerator.generateIdentity("foo"), hash);
    assertNotEquals(MD5IdGenerator.generateIdentity("bar"), hash);
    assertEquals(hash.length(), 32);
  }
}
