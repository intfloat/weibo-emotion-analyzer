package edu.pku.emotion.feat;

import java.io.IOException;
import java.util.List;

import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * Extract recursive autoencoder feature<br> 
 * Currently it will not change <code>features</code> parameter.
 * <br>
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class RAEFeatureExtractor implements FeatureExtractorInterface {
    
    private static final String EMBEDDING_ = "embedding_";

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
        if (weibo.getEmbedding() == null || weibo.getEmbedding().length == 0) {
            try {
                weibo.setEmbedding(IOUtils.getEmbedding(IOUtils.getSegmentedCharacters(weibo.getWeiboText()), 
                                   IOUtils.HOST, 
                                   IOUtils.PORT));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        float[] embedding = weibo.getEmbedding();
        for (int i = 0; i < embedding.length; ++i) {
            features.add(new Feature(EMBEDDING_ + i, embedding[i]));
        }
        return;
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
        if (sentence.getEmbedding() == null || sentence.getEmbedding().length == 0) {
            try {
                sentence.setEmbedding(IOUtils.getEmbedding(IOUtils.getSegmentedCharacters(sentence.getText()), 
                        IOUtils.HOST, 
                        IOUtils.PORT));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        float[] embedding = sentence.getEmbedding();
        for (int i = 0; i < embedding.length; ++i) {
            features.add(new Feature(EMBEDDING_ + i, embedding[i]));
        }
        return;
    }
}
