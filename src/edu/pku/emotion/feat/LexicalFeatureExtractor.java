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
    		 return;
            // weibo.getSeggedText();
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
    		 int index=wordList.indexOf(w);
			 if(index>0)
			 {
				 if(BagOfWord[index]==0)
					 BagOfWord[index]=1;
			 }
    		 int index_e=emotionList.indexOf(w);
			 if(index_e>0)
			 {
				 if(Emotion[index_e]==0)
					 Emotion[index_e]=1;
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
    		 return;
            // sentence.getSeggedText();
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
    		 System.out.print(""+w+" ");
    		 int index=wordList.indexOf(w);
			 if(index>0)
			 {
				 if(BagOfWord[index]==0)
					 BagOfWord[index]=1;
			 }
    		 int index_e=emotionList.indexOf(w);
			 if(index_e>0)
			 {
				 if(Emotion[index_e]==0)
					 Emotion[index_e]=1;
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
