package edu.pku.emotion.feat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.pku.emotion.util.DicModel;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class LexicalFeatureExtractor implements FeatureExtractorInterface {

    private static final String BagOfWord_ = "BoW_";  
    private static final String EmotionWord_="EmoW_";    
    
    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        assert weibo.getSeggedText() != null && !weibo.getSeggedText().isEmpty();
        this.addBOWFeature(weibo.getSeggedText(), features);         
        return;   
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
        assert sentence.getSeggedText() != null && !sentence.getSeggedText().isEmpty();
        this.addBOWFeature(sentence.getSeggedText(), features);
        return;   
    }
    
    /**
     * 
     * @param seggedText
     * @param features
     */
    private void addBOWFeature(ArrayList<String> seggedText, List<Feature> features) {
        HashSet<String> emotionList = DicModel.loadEmotionList();         
        HashMap<String, Integer> counter = new HashMap<String, Integer>();
        for(String w : seggedText) {
            if (!counter.containsKey(w)) counter.put(w, 1);
            else counter.put(w, counter.get(w) + 1);
            
//          find if this is an emotional word
            if (emotionList.contains(w)) {
                features.add(new Feature(EmotionWord_ + w));
            }
         }
         
         for (String key : counter.keySet()) {
             features.add(new Feature(BagOfWord_ + key, counter.get(key)));
         }
         return;
    }

}
