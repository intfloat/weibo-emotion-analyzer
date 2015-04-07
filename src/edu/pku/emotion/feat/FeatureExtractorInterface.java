package edu.pku.emotion.feat;

import java.util.List;

import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public interface FeatureExtractorInterface {
    
    /**
     * Extract features from weibo level
     * 
     * @param weibo
     * @param features
     */
    public void extract(Weibo weibo, List<Feature> features);
    
    /**
     * Extract features for every sentence
     * 
     * @param sentence
     * @param features
     */
    public void extract(Sentence sentence, List<Feature> features);

}
