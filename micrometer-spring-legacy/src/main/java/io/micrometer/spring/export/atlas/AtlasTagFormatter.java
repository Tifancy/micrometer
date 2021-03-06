/**
 * Copyright 2017 Pivotal Software, Inc.
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
package io.micrometer.spring.export.atlas;

import io.micrometer.core.instrument.TagFormatter;
import org.springframework.util.StringUtils;

public class AtlasTagFormatter implements TagFormatter {
    @Override
    public String formatName(String name) {
        return format(name);
    }

    @Override
    public String formatTagKey(String key) {
        return format(key);
    }

    @Override
    public String formatTagValue(String value) {
        return format(value);
    }

    private String format(String tagKeyOrValue) {
        String sanitized = tagKeyOrValue
                .replaceAll("\\{(\\w+):.+}(?=/|$)", "-$1-") // extract path variable names from regex expressions
                .replaceAll("/", "_")
                .replaceAll("[{}]", "-");
        if (!StringUtils.hasText(sanitized)) {
            sanitized = "none";
        }
        return sanitized;
    }
}
