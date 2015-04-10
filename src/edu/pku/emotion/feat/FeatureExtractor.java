package edu.pku.emotion.feat;

import java.util.ArrayList;
import java.util.List;

import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class FeatureExtractor {
    
    private static List<FeatureExtractorInterface> extractors = null;   
    
    /**
     * setup function<br>
     * To add a new kind of feature,
     * you need to write a class implementing <b>FeatureExtractorInterface</b> class
     * and register your class in setup() function
     */
    private static void setup() {
//      THIS IS THE ONLY POSITION THAT SHOULD BE MODIFIED!        
      //  registerExtractor(new RAEFeatureExtractor());
        registerExtractor(new LengthFeatureExtractor());
        registerExtractor(new LexicalFeatureExtractor());
        registerExtractor(new SyntacticFeatureExtractor()); 
        return;
    }
    
    /**
     * Register feature extractor
     */
    private static void registerExtractor(FeatureExtractorInterface ext) {
        extractors.add(ext);
    }
    
    /**
     * Given an instance, extract relevant features
     * 
     * @param ins
     * @return
     */
    public static void extract(Weibo weibo) {
        if (null == extractors) {
            extractors = new ArrayList<FeatureExtractorInterface>();
            setup();
        }
        List<Feature> list = weibo.getFeatures();
        assert list != null;
        list.clear();
        for (FeatureExtractorInterface ext : extractors) {
            ext.extract(weibo, list);
        }
        
//      extract feature for each sentence in current weibo
        for (Sentence s : weibo.getSentences()) {
            list = s.getFeatures();
            assert list != null;
            list.clear();
            for (FeatureExtractorInterface ext : extractors) {
                ext.extract(s, list);
            }
        }
        return;
    } // end method extract
}
