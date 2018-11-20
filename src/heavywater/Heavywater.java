/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author hh
 */
public class Heavywater {

    static Set<String> labelSet = new HashSet<>();
    static List<Document> trainingDocuments = new ArrayList<>();
    static List<Document> testDocuments = new ArrayList<>();
    static Map<String, Double> idf = new HashMap<>();
    static WordLabel wordLabel = new WordLabel();
    static int trainingFileNumber = 62204;
    static int V = 0;
    static Map<String, Double> priorProb = new HashMap<>();
    static Map<String, Long> labelcount = new HashMap<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Model model = new Model();
        FileOperation fileOperation = new FileOperation();
        Test test = new Test();
        String path = "shuffled-full-set-hashed.csv";    
        fileOperation.readFile(path);       
        model.trainModel();
        test.readFile("./TestFiles");
        model.testModel();
        System.out.print("111");
    }
}
