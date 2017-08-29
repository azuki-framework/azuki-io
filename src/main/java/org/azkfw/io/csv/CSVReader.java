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
package org.azkfw.io.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kawakicchi
 */
public class CSVReader {

    private static final int DEFAULT_MAX_BUFFER_SIZE = 1024 * 8;

    private final Reader reader;

    private final int maxBufferSize;

    private final char buffer[];

    private int bufferSize;

    private int pt;

    private boolean eof;

    public CSVReader(final Reader reader) {
        this(reader, DEFAULT_MAX_BUFFER_SIZE);
    }

    public CSVReader(final Reader reader, final int size) {
        this.reader = reader;
        this.maxBufferSize = size;
        this.buffer = new char[maxBufferSize];

        this.bufferSize = 0;
        this.pt = 0;

        this.eof = false;
    }

    public void close() throws IOException {
        reader.close();
    }

    public List<String> readRecord() throws IOException {
        if (eof) {
            return null;
        }

        final List<String> result = new ArrayList<String>();

        boolean escape = false;
        boolean eol = false;

        StringBuffer s = new StringBuffer();
        for (; !eol;) {
            int size = fill();
            if (-1 == size) {
                eof = true;
                if (0 == bufferSize) {
                    break;
                }
            }

            for (; pt < bufferSize; pt++) {
                char c1 = buffer[pt];
                if (escape) {
                    if ('"' == c1) {
                        if (pt + 1 == bufferSize) {
                            if (eof) {
                                pt += 1;
                                eol = true;
                                escape = false;
                            }
                            break;
                        } else {
                            char c2 = buffer[pt + 1];
                            if ('"' == c2) {
                                s.append('"');
                                pt += 1;
                            } else {
                                escape = false;
                            }
                        }
                    } else {
                        s.append(c1);
                    }
                } else {
                    if (',' == c1) {
                        result.add(s.toString());
                        s = new StringBuffer();
                    } else if ('"' == c1) {
                        escape = true;
                    } else if ('\r' == c1) {
                        if (pt + 1 == bufferSize) {
                            if (eof) {
                                pt += 1;
                                eol = true;
                            }
                            break;
                        } else {
                            char c2 = buffer[pt + 1];
                            if ('\n' == c2) {
                                pt += 2;
                            } else {
                                pt += 1;
                            }
                            eol = true;
                            break;
                        }
                    } else if ('\n' == c1) {
                        pt += 1;
                        eol = true;
                        break;
                    } else {
                        s.append(c1);
                    }
                }
            }
        }
        result.add(s.toString());

        return result;
    }

    private int fill() throws IOException {
        int readSize = -1;
        if (bufferSize == pt) {
            bufferSize = 0;
            readSize = reader.read(buffer, 0, maxBufferSize);
            if (0 < readSize) {
                bufferSize = readSize;
            }
            pt = 0;
        } else if (pt + 1 > bufferSize) {
            // 追加読み込みがサイズより大きい値を指定
            System.arraycopy(buffer, pt, buffer, 0, bufferSize - pt);
            bufferSize -= pt;
            readSize = reader.read(buffer, bufferSize, maxBufferSize - bufferSize);
            if (0 < readSize) {
                bufferSize += readSize;
            }
            pt = 0;
        } else {
            readSize = 0;
        }
        return readSize;
    }

}
