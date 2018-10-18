/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.geronimo.microprofile.opentracing.microprofile.zipkin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.geronimo.microprofile.opentracing.common.config.GeronimoOpenTracingConfig;
import org.apache.geronimo.microprofile.opentracing.common.impl.FinishedSpan;
import org.apache.geronimo.microprofile.opentracing.common.impl.IdGenerator;
import org.apache.geronimo.microprofile.opentracing.common.microprofile.zipkin.ZipkinConverter;
import org.apache.geronimo.microprofile.opentracing.common.microprofile.zipkin.ZipkinSpan;

@ApplicationScoped
public class CdiZipkinConverter extends ZipkinConverter {

    @Inject
    private Event<ZipkinSpan> zipkinSpanEvent;

    @Inject
    private GeronimoOpenTracingConfig config;

    @Inject
    private IdGenerator idGenerator;

    @PostConstruct
    public void init() {
        setConfig(config);
        setIdGenerator(idGenerator);
        setZipkinSpanEvent(zipkinSpanEvent::fire);
        super.init();
    }

    public void onSpan(@Observes final FinishedSpan finishedSpan) {
        super.onEvent(finishedSpan);
    }
}
