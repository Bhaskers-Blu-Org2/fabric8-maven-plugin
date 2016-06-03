/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package io.fabric8.maven.enricher.api;

import java.util.Collections;
import java.util.Map;

import org.apache.maven.shared.utils.StringUtils;

/**
 * @author roland
 * @since 24/05/16
 */
public class EnricherConfiguration {

    private final String prefix;
    private final Map<String,String> config;

    // Interfaces to use for dealing with configuration values
    public interface ConfigKey {
        String name();
        String defVal();
    }

    public EnricherConfiguration(String prefix, Map<String, String> config) {
        this.config = Collections.unmodifiableMap(config != null ? config : Collections.<String, String>emptyMap());
        this.prefix = prefix;
    }

    /**
     * Get a configuration value
     *
     * @param key key to lookup. If it implements also {@link DefaultValueProvider} then use this for a default value
     * @return the defa
     */
    public String get(ConfigKey key) {
        return get(key, key.defVal());
    }

    /**
     * Get a config value with a default
     * @param key key part to lookup. The whole key is build up from <code>prefix + "." + key</code>. If key is null,
     *            then only the prefix is used for the lookup (this is suitable for enrichers having only one config option)
     * @param defaultVal the default value to use when the no config is set
     * @return the value looked up or the default value.
     */
    public String get(ConfigKey key, String defaultVal) {
        String keyVal = key != null ? key.name() : "";
        String val = config.get(prefix + (StringUtils.isNotEmpty(keyVal) ? "." + key : ""));
        return val != null ? val : defaultVal;
    }

}
