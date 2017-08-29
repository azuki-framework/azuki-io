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
import java.io.Writer;
import java.util.List;

/**
 * @author kawakicchi
 */
public class CSVWriter {

    private final Writer writer;

    private String lineSeparator;

    public CSVWriter(final Writer writer) {
        this.writer = writer;

        this.lineSeparator = System.lineSeparator();
    }

    public void close() throws IOException {
        writer.close();
    }

    public void setLineSeparator(final String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public void writeRecord(final String... strings) throws IOException {
        if (null != strings && 0 < strings.length) {
            for (int i = 0; i < strings.length; i++) {
                if (0 != i) {
                    writer.write(",");
                }
                writer.write(toField(strings[i]));
            }
            writer.write(lineSeparator);
        }
    }

    public void writeRecord(final List<String> record) throws IOException {
        if (null != record && 0 < record.size()) {
            for (int i = 0; i < record.size(); i++) {
                if (0 != i) {
                    writer.write(",");
                }
                writer.write(toField(record.get(i)));
            }
            writer.write(lineSeparator);
        }
    }

    private String toField(final String string) {
        String buffer = string;
        if (null != buffer) {

            boolean escape = false;

            if (-1 != buffer.indexOf("\"")) {
                escape = true;
            } else if (-1 != buffer.indexOf(",")) {
                escape = true;
            } else if (-1 != buffer.indexOf("\r")) {
                escape = true;
            } else if (-1 != buffer.indexOf("\n")) {
                escape = true;
            }
            if (escape) {
                buffer = buffer.replaceAll("\"", "\"\"");
                buffer = "\"" + buffer
                    + "\"";
            }
        }
        return buffer;
    }
}
