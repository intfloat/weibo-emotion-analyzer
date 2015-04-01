package edu.pku.instance;

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
	
	/**
	 * 
	 * @param id
	 * @param opinion
	 * @param emotion1
	 * @param emotion2
	 * @param text
	 * @throws Exception 
	 */
	public Sentence(int id, boolean opinion, String emotion1, String emotion2, String text) throws Exception {
	   this.id = id;
	   this.opinionated = opinion;
	   this.emotionType1 = Category.getEmotionIndex(emotion1);
	   this.emotionType2 = Category.getEmotionIndex(emotion2);
	   this.text = text;
	}
	
	/**
	 * 
	 * @param id
	 * @param opinion
	 * @param emotion1
	 * @param emotion2
	 * @param text
	 */
	public Sentence(int id, boolean opinion, int emotion1, int emotion2, String text) {
	    this.id = id;
	    this.opinionated = opinion;
	    this.emotionType1 = emotion1;
	    this.emotionType2 = emotion2;
	    this.text = text;
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
}
