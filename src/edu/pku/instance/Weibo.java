package edu.pku.instance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pku.emotion.feat.Feature;
import edu.pku.emotion.feat.LabelMap;
import edu.stanford.nlp.trees.TypedDependency;

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
    private List<String> seggedText;
	private Collection<TypedDependency> parseResult;
    private ArrayList<Feature> features;
    
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
        this.seggedText=null;
        this.parseResult=null;
        
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
        this.seggedText=null;
        this.parseResult=null;
    }
    
    /**
     * Dump string representation of current weibo, for example:<br>
     * ID:2 HAPPINESS_ANGER 2:0.4 4:0.23
     * 
     * @return
     */
    public String dump() {
        String res = "ID:" + this.weiboId;
        res += " " + LabelMap.getIndex(getLabel());
//      conform to LibSVM & XGBoost format
        for (Feature feature : this.features) {
            res += " " + feature.getIndex() + ":" + feature.getValue();
        }
        return res;
    }
    
    private String getLabel() {
        String label = "";
        try {
            label = Category.getEmotionString(this.weiboEmotionType1);
            label += "_" + Category.getEmotionString(this.weiboEmotionType2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return label;
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
    public List<String> getSeggedText() {
    	if(this.sentences==null) return null;
    	List<String> res=this.sentences.get(0).getSeggedText();
    	for(int i=1;i<this.sentences.size();i++)
    	{
    		 List<String> temp=this.sentences.get(i).getSeggedText();
    		 for(String s:temp)
    		 {
    			 res.add(s);
    		 }
    	}
    	return res;	
    }
    public Collection<TypedDependency> getParseResult(){
    	if(this.sentences==null) return null;
    	Collection<TypedDependency>res =this.sentences.get(0).getParseResult();
    	for(int i=1;i<this.sentences.size();i++)
    	{
    		Collection<TypedDependency>temp=this.sentences.get(i).getParseResult();
    		res.addAll(temp);
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
   // public void setBagOfWord(String[] )

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

}
