/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import static heavywater.Heavywater.testDocuments;
import static heavywater.Heavywater.wordLabel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hh
 */
public class Test {

    public void readFile(String testFilePath) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        String[] words;
        Set<String> lines = new LinkedHashSet<>();
        File dir = new File(testFilePath);
        String str = null;
        String label;
        String upperCaseWord = null;
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                br = new BufferedReader(new FileReader(file));
                while ((str = br.readLine()) != null) {
                    Document document = new Document();
                    if (file.getName().contains("=")) {
                        document.label = file.getName().substring(0, file.getName().indexOf("="));
                    }        
                    words = str.split(" ");
                    document.totalNumber = words.length;
                    for (int j = 0; j < words.length; j++) {
                        if (!words[j].equals("")) {
                            document.wordCount.put(words[j], document.wordCount.getOrDefault(words[j], 0L) + 1);
                        }
                    }
                    testDocuments.add(document);
                }
            }
        }
        br.close();
    }

}
