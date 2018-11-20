/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import static heavywater.Heavywater.V;
import static heavywater.Heavywater.idf;
import static heavywater.Heavywater.labelSet;
import static heavywater.Heavywater.labelcount;
import static heavywater.Heavywater.priorProb;
import static heavywater.Heavywater.testDocuments;
import static heavywater.Heavywater.trainingDocuments;
import static heavywater.Heavywater.trainingFileNumber;
import static heavywater.Heavywater.wordLabel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hh
 */
public class Model {
    void trainModel() {
        V = wordLabel.wordLabelCount.size();
        for (String word : idf.keySet()) {
            idf.put(word, Math.log(trainingFileNumber * 1.0 / idf.get(word)));
        }
        for (String label : priorProb.keySet()) {
            priorProb.put(label, priorProb.get(label) * 1.0 / trainingFileNumber);
        }
        for (int i = 0; i < trainingDocuments.size(); i++) {
            Map<String, Long> wordCount = trainingDocuments.get(i).wordCount;
            for (String word : wordCount.keySet()) {
                if (idf.get(word) == 0 || wordLabel.wordLabelProb.containsKey(word) && wordLabel.wordLabelProb.get(word).containsKey(trainingDocuments.get(i).label)) {
                    continue;
                }
                if (!wordLabel.wordLabelProb.containsKey(word)) {
                    Map<String, Double> tempMap = new HashMap<>();
                    tempMap.put(trainingDocuments.get(i).label, (wordLabel.wordLabelCount.get(word).get(trainingDocuments.get(i).label) + 1) * 1.0 / (labelcount.get(trainingDocuments.get(i).label) + V));
                    wordLabel.wordLabelProb.put(word, tempMap);
                } else {
                    wordLabel.wordLabelProb.get(word).put(trainingDocuments.get(i).label, (wordLabel.wordLabelCount.get(word).get(trainingDocuments.get(i).label) + 1) * 1.0 / (labelcount.get(trainingDocuments.get(i).label) + V));
                }
            }
        }
    }

    void testModel() {
        double p = 1.0;
        int resetTimes = 0;
        double maxP = 0.0;
        int maxResetTimes = Integer.MAX_VALUE;
        String predictedLabel = "";
        int correctNumber = 0;
        double totalFrequency = 1.0;
        double wordFrequcney = 1.0;
        for (int i = 0; i < testDocuments.size(); i++) {
            Map<String, Long> wordCount = testDocuments.get(i).wordCount;
            for (String label : labelSet) {
                p = p * priorProb.get(label);
                for (String word : wordCount.keySet()) {
                    if (idf.get(word) != null && idf.get(word) == 0) {
                        continue;
                    }
                    if (wordLabel.wordLabelProb.get(word) == null || wordLabel.wordLabelProb.get(word).get(label) == null) {
                        totalFrequency = 1.0 / (V + labelcount.get(label));
                    } else {
                        totalFrequency = wordLabel.wordLabelProb.get(word).get(label);
                    }
                    double tempidf = 0.0;
                    if (idf.get(word) == null) {
                        tempidf = 1.0 / testDocuments.get(i).totalNumber;
                    } else {
                        tempidf = idf.get(word);
                    }
                    wordFrequcney = Math.pow(totalFrequency, wordCount.get(word));
                    p = p * wordFrequcney * tempidf;
                    //if p overflow, reset it and record reset time.
                    if (p <= 0) {
                        p = 1.0;
                        resetTimes++;
                    }
                }
                //The smaller reset time, the bigger probability
                if (resetTimes < maxResetTimes || resetTimes == maxResetTimes && p > maxP) {
                    predictedLabel = label;
                    maxP = p;
                    maxResetTimes = resetTimes;
                }
                p = 1.0;
                resetTimes = 0;
            }
            maxP = 0.0;
            maxResetTimes = Integer.MAX_VALUE;
            if (predictedLabel.equals(testDocuments.get(i).label)) {
                correctNumber++;
            }
            predictedLabel = "";
        }
        System.out.println("Accuracy: " + correctNumber * 1.0 / testDocuments.size());
    }
}
