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
package com.cloudera.nav.plugin.client.writer.registry;

import com.cloudera.nav.plugin.model.annotations.MProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;

public class MPropertyRegistryFactory extends
    AbstractRegistryFactory<MPropertyEntry> {

  private static final String[] PREFIXES = {"get", "is"};

  @Override
  protected MPropertyEntry createEntry(Method method) {
    return new MPropertyEntry(getAttributeName(method), method);
  }

  @Override
  protected Class<? extends Annotation> getTypeClass() {
    return MProperty.class;
  }

  private String getAttributeName(Method method) {
    // return attribute from MProperty annotation if explicitly specified
    // otherwise create the attribute from the method name
    // with first letter lowercased
    String attributeName = method.getAnnotation(MProperty.class).attribute();
    if (StringUtils.isEmpty(attributeName)) {
      String methodName = method.getName();
      for (String prefix : PREFIXES) {
        if (methodName.startsWith(prefix) &&
            methodName.length() > prefix.length()) {
          methodName = String.valueOf(methodName.charAt(prefix.length()))
              .toLowerCase() + methodName.substring(prefix.length() + 1);
          break;
        }
      }
      attributeName = methodName;
    }
    return attributeName;
  }
}
