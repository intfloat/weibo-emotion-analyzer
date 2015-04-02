package edu.pku.emotion.feat;

import java.util.List;

import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public interface FeatureExtractorInterface {
    
    /**
     * 
     * @param weibo
     * @param features
     */
    public void extract(Weibo weibo, List<Feature> features);

}
