package edu.pku.emotion.feat;

import java.util.HashMap;
import java.util.List;

import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class LexicalFeatureExtractor implements FeatureExtractorInterface {

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        String[] words = IOUtils.getSegmentedCharacters(weibo.getWeiboText()).split("\\s++");
        this.addBOWFeatures(words, features);
        return;
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
        String[] words = IOUtils.getSegmentedCharacters(sentence.getText()).split("\\s++");
        this.addBOWFeatures(words, features);
        return;
    }
    
    /**
     * 
     * @param words
     * @param features
     */
    private void addBOWFeatures(String[] words, List<Feature> features) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String s : words) {
            if (map.containsKey(s)) map.put(s, map.get(s) + 1);
            else map.put(s, 1);
        }
        for (String key : map.keySet()) {
            features.add(new Feature(key, map.get(key)));
        }
        return;
    }

}
