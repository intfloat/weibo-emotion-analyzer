package edu.pku.instance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pku.emotion.feat.Feature;
import edu.pku.emotion.feat.LabelMap;
import edu.pku.emotion.util.DicModel;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseGrammaticalStructure;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 * 
 * <br>Represent single sentence in a <code>Weibo</code> class
 *
 */
public class Sentence {
	
	private int id;
	private boolean opinionated;
	private String keyExpression;
	private int emotionType1;
	private int emotionType2;
	private String text;
	private String segtext;
	private float[] embedding;
	private List<String> seggedText;
	private Collection<TypedDependency> parseResult;
	private ArrayList<Feature> features;
	
	/**
	 * 
	 * @param id
	 * @param opinion
	 * @param emotion1
	 * @param emotion2
	 * @param text
	 * @throws Exception 
	 */
	public Sentence(int id, boolean opinion, String emotion1, String emotion2, String expression, String text) throws Exception {
	   this.id = id;
	   this.opinionated = opinion;
	   this.emotionType1 = Category.getEmotionIndex(emotion1);
	   this.emotionType2 = Category.getEmotionIndex(emotion2);
	   this.keyExpression = expression;
	   this.text = text;
	   this.features = new ArrayList<Feature>();
	   this.parseResult=null;
	   this.seggedText=null;
	}
	
	/**
	 * 
	 * @param id
	 * @param opinion
	 * @param emotion1
	 * @param emotion2
	 * @param text
	 */
	public Sentence(int id, boolean opinion, int emotion1, int emotion2, String expression, String text) {
	    this.id = id;
	    this.opinionated = opinion;
	    this.emotionType1 = emotion1;
	    this.emotionType2 = emotion2;
	    this.keyExpression = expression;
	    this.text = text;
	    this.features = new ArrayList<Feature>();
	    this.parseResult=null;
		this.seggedText=null;
		setSegment();
		setParse();
	}
	
	/**
     * 
     * @param id
     * @param text
     */
    public Sentence(int id, String text) {
        this.id = id;
        this.text = text;
        this.emotionType1 = this.emotionType2 = -1;
        this.keyExpression = null;
        this.features = new ArrayList<Feature>();
        this.parseResult=null;
        this.seggedText=null;
        setSegment();
		setParse();
    }
	
	/**
	 * Dump sentence to a string, for example:
	 * SID:3 N_LIKE_DISGUST 1:2.0 3:0.23
	 * 
	 * @return
	 */
	public String dump() {
	    String res = "SID:" + this.id;
	    res += " " + LabelMap.getIndex(getLabel());
	    for (Feature feature : this.features) {
	        res += " " + feature.getIndex() + ":" + feature.getValue();
	    }
	    return res;
	}
	
	public void setSegment(){
		 CRFClassifier classifier=DicModel.loadSegment();
		 System.out.println(text);
		 seggedText=classifier.segmentString(text);
		 StringBuffer sb=new StringBuffer();
		 for(String w:seggedText)
		 {
			 sb.append(w+" ");
		 }
		 segtext=sb.toString();
	}
	public void setParse(){
		LexicalizedParser lp=DicModel.loadParser();
		Tree t = lp.parse(segtext);   
	    ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(t);  
	    parseResult = gs.typedDependenciesCollapsed();  
	}
	private String getLabel() {
	    String label = "";
	    if (this.opinionated) label = "Y";
	    else label = "N";
	    try {
            label += "_" + Category.getEmotionString(emotionType1);
            label += "_" + Category.getEmotionString(emotionType2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }	    
	    return label;
	}
	
	@Override
	public String toString() {
	    String res = null;
        try {
            res = "SentenceId: " + id + " "
                        + "Opinionated: " + this.opinionated + " ";
            if (this.opinionated) {
                res += "Emotion1: " + Category.getEmotionString(this.emotionType1) + " "
                        + "Emotion2: " + Category.getEmotionString(this.emotionType2) + " ";
            }
            res += "text: " + this.text;
                     
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return res;
	}	
	
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isOpinionated() {
        return opinionated;
    }
    public void setOpinionated(boolean opinionated) {
        this.opinionated = opinionated;
    }
    public String getKeyExpression() {
        return keyExpression;
    }
    public void setKeyExpression(String keyExpression) {
        this.keyExpression = keyExpression;
    }
    public int getEmotionType1() {
        return emotionType1;
    }
    public void setEmotionType1(int emotionType1) {
        this.emotionType1 = emotionType1;
    }
    public int getEmotionType2() {
        return emotionType2;
    }
    public void setEmotionType2(int emotionType2) {
        this.emotionType2 = emotionType2;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public float[] getEmbedding() {
        return embedding;
    }
    public List<String> getSeggedText(){
    	return seggedText;
    }
    public Collection<TypedDependency> getParseResult(){
    	return parseResult;
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
