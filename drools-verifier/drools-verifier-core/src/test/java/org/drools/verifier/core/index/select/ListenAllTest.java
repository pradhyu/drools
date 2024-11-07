/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.drools.verifier.core.index.select;

import java.util.Collection;
import java.util.List;

import org.drools.verifier.core.index.keys.Value;
import org.drools.verifier.core.index.matchers.ExactMatcher;
import org.drools.verifier.core.maps.KeyDefinition;
import org.drools.verifier.core.maps.MultiMap;
import org.drools.verifier.core.maps.MultiMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ListenAllTest {

    private Listen<String> listen;
    private MultiMap<Value, String, List<String>> map;
    private Collection<String> all;

    @BeforeEach
    public void setUp() throws Exception {
        map = MultiMapFactory.make(true);

        listen = new Listen<>(map,
                               new ExactMatcher(KeyDefinition.newKeyDefinition().withId("ID").build(),
                                                "value"));

        listen.all(new AllListener<String>() {
            @Override
            public void onAllChanged(final Collection<String> all) {
                ListenAllTest.this.all = all;
            }
        });
    }

    @Test
    void testEmpty() throws Exception {
        assertThat(all).isNull();
    }

    @Test
    void testAll() throws Exception {
        map.put(new Value("value"), "hello");

        assertThat(all).contains("hello");
    }
}