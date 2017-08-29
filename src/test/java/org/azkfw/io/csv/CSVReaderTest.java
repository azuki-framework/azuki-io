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

import java.io.InputStreamReader;
import java.util.List;

import junit.framework.TestCase;

import org.azkfw.io.csv.CSVReader;
import org.junit.Test;

/**
 * @author
 */
public class CSVReaderTest extends TestCase {

    @Test
    public void test01() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test01.csv"),
                                                                 "UTF-8"));

        List<String> record = null;

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(1, record.size());
        assertEquals("", record.get(0));

        record = reader.readRecord();
        assertNull(record);

        reader.close();
    }

    @Test
    public void test02() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test02.csv"),
                                                                 "UTF-8"));

        List<String> record = null;

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(1, record.size());
        assertEquals("ABCDE", record.get(0));

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(1, record.size());
        assertEquals("", record.get(0));

        record = reader.readRecord();
        assertNull(record);

        reader.close();
    }

    @Test
    public void test03() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test03.csv"),
                                                                 "UTF-8"));

        List<String> record = null;

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(5, record.size());
        assertEquals("ABCDE", record.get(0));
        assertEquals("FGHIJ", record.get(1));
        assertEquals("KLMNO", record.get(2));
        assertEquals("PQRST", record.get(3));
        assertEquals("UVWXY", record.get(4));

        record = reader.readRecord();
        assertNull(record);

        reader.close();
    }

    @Test
    public void test04() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test04.csv"),
                                                                 "UTF-8"));

        List<String> record = null;

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(2, record.size());
        assertEquals("ABCDE", record.get(0));
        assertEquals("FGHIJ", record.get(1));

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(2, record.size());
        assertEquals("12345", record.get(0));
        assertEquals("67890", record.get(1));

        record = reader.readRecord();
        assertNull(record);

        reader.close();
    }

    @Test
    public void test05() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test05.csv"),
                                                                 "UTF-8"));

        List<String> record = null;

        record = reader.readRecord();
        assertNotNull(record);
        assertEquals(6, record.size());
        assertEquals("", record.get(0));
        assertEquals("", record.get(1));
        assertEquals("ABCDE", record.get(2));
        assertEquals("\"12345\"", record.get(3));
        assertEquals("1,2,3,4,5", record.get(4));
        assertEquals("A\r\n1", record.get(5));

        record = reader.readRecord();
        assertNull(record);

        reader.close();
    }

    public void aa() throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(this.getClass()
                                                                     .getResourceAsStream("/org/azkfw/io/csv/test01.csv"),
                                                                 "UTF-8"));

        int no = 1;
        List<String> line = null;
        while (null != (line = reader.readRecord())) {
            System.out.print("[" + no
                + "] ");
            for (int i = 0; i < line.size(); i++) {
                if (i != 0) {
                    System.out.print(", ");
                }
                System.out.print(line.get(i));
            }
            System.out.println("");

            no++;
        }

        reader.close();

    }
}
