package edu.pku.emotion.feat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.pku.emotion.util.DicModel;
import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class LexicalFeatureExtractor implements FeatureExtractorInterface {

	private static final String BagOfWord_ = "BagOfWord_";  
	private static final String EmotionWord_="EmotionWord_";
    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
    	 if(weibo.getSeggedText()==null||weibo.getSeggedText().size()==0)
    	 {
             weibo.getSeggedText();
         }
    	 ArrayList<String> wordList=DicModel.loadWordList();
    	 ArrayList<String> emotionList=DicModel.loadEmotionList();
    	 int BagOfWord[]=new int[wordList.size()];
    	 for(int i=0;i<wordList.size();i++)
    		 BagOfWord[i]=0; 
    	 int Emotion[]=new int[emotionList.size()];
    	 for(int i=0;i<Emotion.length;i++)
    		 Emotion[i]=0;
    	 for(String w:weibo.getSeggedText())
    	 {
    		 for(int i=0;i<wordList.size();i++)
    		 {
    			 if(w.equals(wordList.get(i)))
    			 {
    				 if(BagOfWord[i]==0)
    					 BagOfWord[i]=1;
    			 }
    		 }
    		 for(int i=0;i<emotionList.size();i++)
    		 {
    			 if(w.equals(wordList.get(i)))
    			 {
    				 if(Emotion[i]==0)
    					 Emotion[i]=1;
    			 }
    		 }
    	 }
    	 
    	 for(int i=0;i<BagOfWord.length;i++)
    	 {
    		 features.add(new Feature(BagOfWord_+i,BagOfWord[i]));
    	 }
    	 for(int i=0;i<Emotion.length;i++)
    	 {
    		 features.add(new Feature(EmotionWord_+i,Emotion[i]));
    	 }
         return;   
    }

    @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
    	 if(sentence.getSeggedText()==null||sentence.getSeggedText().size()==0)
    	 {
             sentence.getSeggedText();
         }
    	 ArrayList<String> wordList=DicModel.loadWordList();
    	 ArrayList<String> emotionList=DicModel.loadEmotionList();
    	 int BagOfWord[]=new int[wordList.size()];
    	 for(int i=0;i<wordList.size();i++)
    		 BagOfWord[i]=0; 
    	 int Emotion[]=new int[emotionList.size()];
    	 for(int i=0;i<Emotion.length;i++)
    		 Emotion[i]=0;
    	 for(String w:sentence.getSeggedText())
    	 {
    		 for(int i=0;i<wordList.size();i++)
    		 {
    			 if(w.equals(wordList.get(i)))
    			 {
    				 if(BagOfWord[i]==0)
    					 BagOfWord[i]=1;
    			 }
    		 }
    		 for(int i=0;i<emotionList.size();i++)
    		 {
    			 if(w.equals(wordList.get(i)))
    			 {
    				 if(Emotion[i]==0)
    					 Emotion[i]=1;
    			 }
    		 }
    	 }
    	 
    	 for(int i=0;i<BagOfWord.length;i++)
    	 {
    		 features.add(new Feature(BagOfWord_+i,BagOfWord[i]));
    	 }
    	 for(int i=0;i<Emotion.length;i++)
    	 {
    		 features.add(new Feature(EmotionWord_+i,Emotion[i]));
    	 }
         return;   
    }

}
