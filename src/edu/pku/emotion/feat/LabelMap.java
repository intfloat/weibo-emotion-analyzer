package edu.pku.emotion.feat;

import java.util.HashMap;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class LabelMap {
    
    private static HashMap<String, Integer> map = null;
    
    /**
     * 
     * @param label
     * @return
     */
    public static int getIndex(String label) {
        if (null == map) map = new HashMap<String, Integer>();
        if (map.containsKey(label)) return map.get(label);
        else {
            map.put(label, map.size());
            return map.get(label);
        }
    }    
    
    /**
     * 
     * @return
     */
    public static String getLabelMapping() {
        if (null == map) return "null";
        String[] arr = new String[map.size()];
        for (String key : map.keySet()) {
            arr[map.get(key)] = key;
        }
        String res = "";
        for (int i = 0; i < arr.length; ++i) {
            res += i + ": " + arr[i] + "\n";
        }
        return res;
    }

}
