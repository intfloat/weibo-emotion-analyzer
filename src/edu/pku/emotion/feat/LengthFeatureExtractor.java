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
public class LengthFeatureExtractor implements FeatureExtractorInterface {
    
    private static final String NUMBER_OF_SENTENCE = "number_of_sentence";
    private static final String NUMBER_OF_CHAR = "number_of_char";

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        features.add(new Feature(NUMBER_OF_SENTENCE, weibo.getSentences().size()));
        features.add(new Feature(NUMBER_OF_CHAR, weibo.getWeiboText().length()));
        return;
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
        features.add(new Feature(NUMBER_OF_CHAR, sentence.getText().length()));
        return;
    }

}
