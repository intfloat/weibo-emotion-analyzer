package edu.pku.instance;

import java.util.ArrayList;

import edu.pku.emotion.feat.Feature;
import edu.pku.emotion.feat.FeatureMap;
import edu.pku.emotion.feat.LabelMap;
import edu.pku.emotion.util.IOUtils;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Weibo {
    
    private ArrayList<Sentence> sentences;
    private int weiboId;
    private int weiboEmotionType1;
    private int weiboEmotionType2;
    private float[] embedding;
    private ArrayList<Feature> features;
    private static final int mincount = Integer.parseInt(IOUtils.getConfValue(IOUtils.MINCOUNT));
    
    /**
     * 
     * @param id
     * @param emotion1
     * @param emotion2
     * @throws Exception
     */
    public Weibo(int id, String emotion1, String emotion2) throws Exception {
        this.weiboId = id;
        if (emotion1 == null) this.weiboEmotionType1 = -1;
        else this.weiboEmotionType1 = Category.getEmotionIndex(emotion1);
        if (emotion2 == null) this.weiboEmotionType2 = -1;
        else this.weiboEmotionType2 = Category.getEmotionIndex(emotion2);
        this.sentences = new ArrayList<Sentence>();
        this.features = new ArrayList<Feature>();
    }
    
    /**
     * 
     * @param id
     * @param emotion1
     * @param emotion2
     */
    public Weibo(int id, int emotion1, int emotion2) {
        this.weiboId = id;
        this.weiboEmotionType1 = emotion1;
        this.weiboEmotionType2 = emotion2;
        this.sentences = new ArrayList<Sentence>();
        this.features = new ArrayList<Feature>();
    }
    
    /**
     * Dump string representation of current weibo
     * @return
     */
    public String dump() {
//      primary sentiment
        String sent1 = "WPID:" + this.weiboId + " ";
        sent1 += LabelMap.getIndex(Category.getEmotionString(weiboEmotionType1));
        sent1 += getFeatureString();
        
//      secondary sentiment
        String sent2 = "WPID:" + this.weiboId + " ";
        sent2 += LabelMap.getIndex(Category.getEmotionString(weiboEmotionType2));
        sent2 += getFeatureString();
        
        return sent1 + "\n" + sent2;
    }
    
    private String getFeatureString() {
        String res = "";
        for (Feature feature : this.features) {
            if (FeatureMap.getFeatureFrequency(feature) >= mincount)
                res += " " + feature.getIndex() + ":" + feature.getValue();
        }
        return res;
    }
    
    /**
     * 
     * @param s
     */
    public void addSentence(Sentence s) {
        if (this.sentences == null) this.sentences = new ArrayList<Sentence>();
        this.sentences.add(s);
        return;
    }
    
    public String getWeiboText() {
        if (this.sentences == null) return "";
        String res = "";
        for (Sentence s : this.sentences) {
            res = res + " " + s.getText();
        }
        return res;
    }
    
    @Override
    public String toString() {
        String res = null;
        try {
            res = "Id: " + weiboId + " "
                        + "Emotion1: " + Category.getEmotionString(weiboEmotionType1) + " "
                        + "Emotion2: " + Category.getEmotionString(weiboEmotionType2) + " \n";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Sentence st : sentences)
            res += st + " \n";
        return res;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public int getWeiboEmotionType1() {
        return weiboEmotionType1;
    }

    public void setWeiboEmotionType1(int weiboEmotionType1) {
        this.weiboEmotionType1 = weiboEmotionType1;
    }

    public int getWeiboEmotionType2() {
        return weiboEmotionType2;
    }

    public void setWeiboEmotionType2(int weiboEmotionType2) {
        this.weiboEmotionType2 = weiboEmotionType2;
    }

    public float[] getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float[] embedding) {
        this.embedding = embedding;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }
    

}
