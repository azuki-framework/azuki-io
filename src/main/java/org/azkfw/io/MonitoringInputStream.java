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
package org.azkfw.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kawakicchi
 */
public class MonitoringInputStream extends InputStream {

    private InputStream stream;

    private long readedSize;

    public MonitoringInputStream(final InputStream stream) {
        this.stream = stream;
        this.readedSize = 0;
    }

    public long getReadedSize() {
        return readedSize;
    }

    @Override
    public int read() throws IOException {
        int c = stream.read();
        if (-1 != c) {
            readedSize++;
        }
        return c;
    }

    public int read(final byte b[]) throws IOException {
        int sz = stream.read(b);
        if (-1 != sz) {
            readedSize += sz;
        }
        return sz;
    }

    public int read(byte b[],
        int off,
        int len) throws IOException {
        int sz = stream.read(b, off, len);
        if (-1 != sz) {
            readedSize += sz;
        }
        return sz;
    }

    public long skip(long n) throws IOException {
        long sz = stream.skip(n);
        readedSize += sz;
        return sz;
    }

    public int available() throws IOException {
        return 0;
    }

    public void close() throws IOException {
    	if (null != stream) {
    		stream.close();
    	}
    }

    public synchronized void mark(int readlimit) {
    }

    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    public boolean markSupported() {
        return false;
    }
}
