package edu.pku.emotion.feat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

public class EmotionFeatureExtractor implements FeatureExtractorInterface {
    
    private static final String EMOTION = "EMOTION_";

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        this.addEmotionFeature(IOUtils.getSegmentedCharacters(weibo.getWeiboText()), features);
        return;
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
        this.addEmotionFeature(IOUtils.getSegmentedCharacters(sentence.getText()), features);
        return;
    }
    
    /**
     * 
     * @param seggedText
     * @param features
     */
    private void addEmotionFeature(ArrayList<String> text, List<Feature> features) {                 
        HashMap<String, Integer> counter = new HashMap<String, Integer>();
        for(String w : text) {
            if (!counter.containsKey(w)) counter.put(w, 1);
            else counter.put(w, counter.get(w) + 1);
        }
         
         for (String key : counter.keySet()) {
             features.add(new Feature(EMOTION + key, counter.get(key)));
         }
         return;
    }

}
