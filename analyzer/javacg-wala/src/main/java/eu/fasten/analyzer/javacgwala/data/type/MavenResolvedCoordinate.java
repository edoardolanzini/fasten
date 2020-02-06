/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package eu.fasten.analyzer.javacgwala.data.type;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.gradle.tooling.model.GradleModuleVersion;
import org.gradle.tooling.model.idea.IdeaSingleEntryLibraryDependency;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;


public final class MavenResolvedCoordinate extends MavenCoordinate implements Serializable {
    public final Path jarPath;

    /**
     * Construct maven resolve coordinate based on groupID, artifactID, version and
     * a path the jar file.
     *
     * @param groupId    - group ID
     * @param artifactId - artifact ID
     * @param version    - version
     * @param jarPath    - path to jar file
     */
    public MavenResolvedCoordinate(String groupId, String artifactId,
                                   String version, Path jarPath) {
        super(groupId, artifactId, version);
        this.jarPath = jarPath;
    }

    /**
     * Create new {@link MavenResolvedCoordinate} given a {@link MavenResolvedArtifact}.
     *
     * @param artifact - maven resolved artifact
     * @return - new Maven Resolved Coordinate
     */
    public static MavenResolvedCoordinate of(MavenResolvedArtifact artifact) {
        return new MavenResolvedCoordinate(
                artifact.getCoordinate().getGroupId(),
                artifact.getCoordinate().getArtifactId(),
                artifact.getCoordinate().getVersion(),
                artifact.as(Path.class));
    }

    /**
     * Create new {@link MavenResolvedCoordinate} give a {@link IdeaSingleEntryLibraryDependency}.
     *
     * @param d - Idea Single Entry Library Dependency
     * @return - new Maven Resolved Coordinate
     */
    public static MavenResolvedCoordinate of(IdeaSingleEntryLibraryDependency d) {
        GradleModuleVersion mod = d.getGradleModuleVersion();
        return new MavenResolvedCoordinate(
                mod.getGroup(),
                mod.getName(),
                mod.getVersion(),
                Paths.get(d.getFile().toURI()));
    }
}
