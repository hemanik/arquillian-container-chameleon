/*
 * JBoss, Home of Professional Open Source
 * Copyright 2016 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arquillian.container.chameleon.spi.model;

public class Container {

    private String name;
    private String versionExpression;
    private String defaultProtocol;

    private Adapter[] adapters;
    private String defaultType;

    private Dist dist;

    private String[] exclude;

    public String getName() {
        return name;
    }

    public Dist getDist() {
        return dist;
    }

    public Adapter[] getAdapters() {
        return this.adapters;
    }

    public ContainerAdapter matches(Target target) {
        if (target.getServer().equalsIgnoreCase(name)) {
            if (target.getVersion().matches(versionExpression)) {
                Target.Type definedType = target.getType();
                if (target.getType() == Target.Type.Default) {
                    if (defaultType != null) {
                        definedType = Target.Type.from(defaultType);
                    }
                }
                for (Adapter adapter : adapters) {
                    if (adapter.isType(definedType)) {
                        return new ContainerAdapter(
                            target.getVersion(),
                            definedType,
                            adapter,
                            dist,
                            exclude,
                            defaultProtocol);
                    }
                }
            }
        }
        return null;
    }

    public boolean isVersionMatches(Target target) {
        return target.getServer().equalsIgnoreCase(name) && target.getVersion().matches(versionExpression);
    }
}
