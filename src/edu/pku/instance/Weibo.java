package edu.pku.instance;

import java.util.ArrayList;

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
    
    /**
     * 
     * @param id
     * @param emotion1
     * @param emotion2
     * @throws Exception
     */
    public Weibo(int id, String emotion1, String emotion2) throws Exception {
        this.weiboId = id;
        this.weiboEmotionType1 = Category.getEmotionIndex(emotion1);
        this.weiboEmotionType2 = Category.getEmotionIndex(emotion2);
        this.sentences = new ArrayList<Sentence>();
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
    

}
