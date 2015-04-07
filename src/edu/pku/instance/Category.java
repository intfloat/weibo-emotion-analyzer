package edu.pku.instance;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Category {
	/**
	 * Anger, Disgust, Fear, Happiness, Like, Sadness and Surprise
	 */
	private static final String ANGER = "anger";
	private static final String DISGUST = "disgust";
	private static final String FEAR = "fear";
	private static final String HAPPINESS = "happiness";
	private static final String LIKE = "like";
	private static final String SADNESS = "sadness";
	private static final String SURPRISE = "surprise";
	private static final String NONE = "none";
	private static final String[] emotions = {NONE, DISGUST, FEAR, 
		                                      HAPPINESS, LIKE, SADNESS, 
		                                      SURPRISE, ANGER};
	
	/**
	 * 
	 * @param emotion
	 * @return
	 * @throws Exception
	 */
	public static int getEmotionIndex(String emotion) throws Exception {	    
	    for (int i = 0; i < emotions.length; ++i) {
	        if (emotion.equalsIgnoreCase(emotions[i]))
	            return i;
	    }
	    throw new Exception("Illegal emotion: " + emotion);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static String getEmotionString(int index) {
	    if (index < 0) return NONE;
	    assert index < emotions.length;	    
	    return emotions[index];
	}
}
