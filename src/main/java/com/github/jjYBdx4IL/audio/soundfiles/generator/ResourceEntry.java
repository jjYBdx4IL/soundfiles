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
package com.github.jjYBdx4IL.audio.soundfiles.generator;

public class ResourceEntry implements Comparable<ResourceEntry> {

    private final String resourceId;
    private final String absoluteResourcePath;

    public ResourceEntry(String resourceId, String absoluteResourcePath) {
        this.resourceId = resourceId;
        this.absoluteResourcePath = absoluteResourcePath;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getAbsoluteResourcePath() {
        return absoluteResourcePath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResourceEntry [resourceId=").append(resourceId).append(", absoluteResourcePath=")
                .append(absoluteResourcePath).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((absoluteResourcePath == null) ? 0 : absoluteResourcePath.hashCode());
        result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceEntry other = (ResourceEntry) obj;
        if (absoluteResourcePath == null) {
            if (other.absoluteResourcePath != null)
                return false;
        } else if (!absoluteResourcePath.equals(other.absoluteResourcePath))
            return false;
        if (resourceId == null) {
            if (other.resourceId != null)
                return false;
        } else if (!resourceId.equals(other.resourceId))
            return false;
        return true;
    }

    @Override
    public int compareTo(ResourceEntry o) {
        return absoluteResourcePath.compareTo(o.absoluteResourcePath);
    }

}
