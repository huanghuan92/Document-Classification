/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import static heavywater.Heavywater.idf;
import static heavywater.Heavywater.labelSet;
import static heavywater.Heavywater.labelcount;
import static heavywater.Heavywater.priorProb;
import static heavywater.Heavywater.testDocuments;
import static heavywater.Heavywater.trainingDocuments;
import static heavywater.Heavywater.wordLabel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hh
 */
public class FileOperation {

    void readFile(String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        String label;
        String[] words;
        while ((str = br.readLine()) != null) {
            Document document = new Document();
            label = str.substring(0, str.indexOf(","));
            document.label = label;
            str = str.substring(str.indexOf(",") + 1);
            words = str.split(" ");
            document.totalNumber = words.length;
            for (int j = 0; j < words.length; j++) {
                if (!words[j].equals("")) {
                    document.wordCount.put(words[j], document.wordCount.getOrDefault(words[j], 0L) + 1);
                    if (!wordLabel.wordLabelCount.containsKey(words[j])) {
                        Map<String, Long> tempMap = new HashMap<>();
                        tempMap.put(document.label, 1L);
                        wordLabel.wordLabelCount.put(words[j], tempMap);
                    } else {
                        wordLabel.wordLabelCount.get(words[j]).put(document.label, wordLabel.wordLabelCount.get(words[j]).getOrDefault(document.label, 0L) + 1);
                    }
                }

            }
            trainingDocuments.add(document);
            if (!labelSet.contains(label)) {
                labelSet.add(label);
            }
            labelcount.put(label, labelcount.getOrDefault(label, 0L) + document.totalNumber);
            priorProb.put(label, priorProb.getOrDefault(label, 0.0) + 1);
            for (String word : document.wordCount.keySet()) {
                idf.put(word, idf.getOrDefault(word, 0.0) + 1);
            }
        }

    }

}
