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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import org.azkfw.io.csv.CSVReader;
import org.azkfw.io.csv.CSVWriter;

/**
 * @author kawakicchi
 */
public class Sample {

    public static void main(final String[] args) {
        long tmStart = -1;
        long tmEnd = -1;

        File tmp = new File("tmp");
        tmp.mkdirs();

        System.out.println("処理時間計測");
        if (true) {
            int size = 100;

            StringBuffer s = new StringBuffer();
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 10; j++) {
                    s.append((char) ('A' + i));
                }
            }
            String key = s.toString();

            tmStart = System.nanoTime();

            CSVWriter writer = null;
            try {
                writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(new File("tmp/test.csv")), "UTF-8"));
                // writer.setLineSeparator("\r\n");

                writer.writeRecord("NO", "ID", "NAME", "MESSAGE", "KEY");

                for (int i = 0; i < size; i++) {
                    writer.writeRecord(Arrays.asList("" + (i + 1),
                                                     String.format("ID%d", (i + 1)),
                                                     String.format("名前%d", (i + 1)),
                                                     String.format("メッセージ%d", (i + 1)),
                                                     key));
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (null != writer) {
                    try {
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            tmEnd = System.nanoTime();
            System.out.println(String.format("書き出し時間 [%.2f sec]", ((double) (tmEnd - tmStart) / 1000000000f)));

        }

        tmStart = System.nanoTime();

        CSVReader reader2 = null;
        try {
            reader2 = new CSVReader(new InputStreamReader(new FileInputStream(new File("tmp/test.csv")), "UTF-8"),
                                    1024 * 8);

            int no = 1;
            List<String> record = null;
            while (null != (record = reader2.readRecord())) {
                System.out.print(String.format("[%5d] ", no++));
                for (int i = 0; i < record.size(); i++) {
                    if (i != 0) {
                        System.out.print(", ");
                    }
                    System.out.print(record.get(i));
                }
                System.out.println("");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != reader2) {
                try {
                    reader2.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        tmEnd = System.nanoTime();
        System.out.println(String.format("読み込み時間 [%.2f sec]", ((double) (tmEnd - tmStart) / 1000000000f)));

    }
}
