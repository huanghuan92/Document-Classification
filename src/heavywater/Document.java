/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import java.util.HashMap;
import java.util.Map;

/* This class record label, totoal word number
 * and each word's word number of each file
 */

/**
 *
 * @author hh
 */
public class Document {
    //key are the words in this file.
    //Value is the number of word.
    Map<String, Long> wordCount = new HashMap<>();
    String label = "";
    int totalNumber;
}
