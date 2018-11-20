/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heavywater;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hh
 */
public class WordLabel {
    //The first String represents word. The second String represents label
    //The Duobele represents each word's frequcnecy under the label
    Map<String, Map<String, Double>> wordLabelProb = new HashMap<>();
    
     //The first String represents word. The second String represents label
    //The Long represents each word's number under the label
    Map<String, Map<String, Long>> wordLabelCount = new HashMap<>();
    
}
