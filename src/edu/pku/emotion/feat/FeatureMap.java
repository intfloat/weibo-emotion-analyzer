package edu.pku.emotion.feat;

import java.util.HashMap;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class FeatureMap {
        
    private static HashMap<String, Integer> map;    
    private static HashMap<String, Integer> feature_frequency;
    
    /**
     * 
     * @param f is a feature instance
     * @return index of feature f
     */
    public static int getIndex(Feature f) {
        if (null == map) 
            map = new HashMap<String, Integer>();
        if (null == feature_frequency) 
            feature_frequency = new HashMap<String, Integer>();       
        
//      insert feature f into map if it doesn't exist
        if (!map.containsKey(f.getDesc())) {
            map.put(f.getDesc(), map.size());
            feature_frequency.put(f.getDesc(), 1);
        }
        else {
            feature_frequency.put(f.getDesc(), feature_frequency.get(f.getDesc()) + 1);
        }        
        return map.get(f.getDesc());
    }    
    
    /**
     * 
     * @param f
     * @return
     */
    public static int getFeatureFrequency(Feature f) {
        return feature_frequency.get(f.getDesc());
    }   
    
    
    /**
     * Clear current feature map
     */
    public static void clearFeatureMap() {
        if (null == map) map = new HashMap<String, Integer>();
        else map.clear();
        
        if (null == feature_frequency) feature_frequency = new HashMap<String, Integer>();
        else feature_frequency.clear();
        
        return;
    }
    
    /**
     * Print feature mapping information to console
     */
    public static void printMapping() {
        if (null == map) {
            System.out.println("No feature mapping available yet");
            return;
        }
        for (String s : map.keySet()) {
            System.out.println("< " + s + ", " + map.get(s) + ">");
        }
        return;
    }   

}
