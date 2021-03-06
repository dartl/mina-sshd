/*
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
package org.apache.sshd.common.file.root;

import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;

import org.apache.sshd.common.file.util.BasePath;
import org.apache.sshd.common.file.util.ImmutableList;

/**
 * @author <a href="mailto:dev@mina.apache.org">Apache MINA SSHD Project</a>
 */
public class RootedPath extends BasePath<RootedPath, RootedFileSystem> {

    public RootedPath(RootedFileSystem fileSystem, String root, ImmutableList<String> names) {
        super(fileSystem, root, names);
    }

    public URI toUri() {
        // TODO
        return null;
    }

    public RootedPath toRealPath(LinkOption... options) throws IOException {
        RootedPath absolute = toAbsolutePath();
        fileSystem.provider().checkAccess(absolute);
        return absolute;
    }

}
