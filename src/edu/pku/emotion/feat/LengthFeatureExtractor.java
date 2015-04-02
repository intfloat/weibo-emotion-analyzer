package edu.pku.emotion.feat;

import java.util.List;

import edu.pku.instance.Weibo;

public class LengthFeatureExtractor implements FeatureExtractorInterface {

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        features.add(new Feature("NUMBER_OF_SENTENCE", weibo.getSentences().size()));
    }

}
