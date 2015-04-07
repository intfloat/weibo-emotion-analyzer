package edu.pku.emotion.feat;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub        
        ArrayList<Sentence> arr = weibo.getSentences();
        for (Sentence s : arr) {
            try {
                s.setEmbedding(IOUtils.getEmbedding(s.getText(), IOUtils.HOST, IOUtils.PORT));
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            weibo.setEmbedding(IOUtils.getEmbedding(weibo.getWeiboText(), IOUtils.HOST, IOUtils.PORT));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
