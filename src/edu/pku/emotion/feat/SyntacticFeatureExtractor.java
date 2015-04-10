package edu.pku.emotion.feat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pku.emotion.util.DicModel;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;
import edu.stanford.nlp.trees.TypedDependency;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class SyntacticFeatureExtractor implements FeatureExtractorInterface {
	private static final String ParseWord_="ParseWord_";
	private String[] Relation={"amod","advmod","assmod"};

    @Override
    public void extract(Weibo weibo, List<Feature> features) {
        // TODO Auto-generated method stub
    	Collection<TypedDependency> parserResult=weibo.getParseResult();
    	ArrayList<String> wordList=DicModel.loadWordList();
    	 int ParseWord[]=new int[wordList.size()];
    	 for(int i=0;i<wordList.size();i++)
    		 ParseWord[i]=0; 
    	for(TypedDependency w:parserResult)
    	{
    		String s=w.toString();
    		for(int i=0;i<Relation.length;i++)
    		{
    			if(s.contains(Relation[i]))
    			{
    				String[] temp=s.split(",");
    				temp[0]=getWord(temp[0]);
    				temp[1]=getWord(temp[1]);
    				for(int j=0;j<wordList.size();j++)
    				{
    					if(wordList.get(j).contains(temp[0])||wordList.get(j).contains(temp[1]))
    					{
    						 if(ParseWord[i]==0)
    	    					 ParseWord[i]=1;
    					}
    				}
    				
    			}
    		}
    	}
    	 for(int i=0;i<ParseWord.length;i++)
    	 {
    		 features.add(new Feature(ParseWord_+i,ParseWord[i]));
    	 }
        
        
    }

  
    private String getWord(String aline)
    {
    	if(aline.contains("("))
    	{
    		int index1=aline.indexOf("(");
    		int index2=aline.indexOf("-");
    		String temp=aline.substring(index1+1, index2);
    		return temp;
    	}
    	else
    	{
    		int index1=aline.indexOf("-");
    		String temp=aline.substring(0,index1);
    		temp=temp.replace(" ", "");
    		return temp;
    	}
    }
  @Override
    public void extract(Sentence sentence, List<Feature> features) {
        // TODO Auto-generated method stub
    	Collection<TypedDependency> parserResult=sentence.getParseResult();
    	ArrayList<String> wordList=DicModel.loadWordList();
    	 int ParseWord[]=new int[wordList.size()];
    	 for(int i=0;i<wordList.size();i++)
    		 ParseWord[i]=0; 
    	for(TypedDependency w:parserResult)
    	{
    		String s=w.toString();
    		for(int i=0;i<Relation.length;i++)
    		{
    			if(s.contains(Relation[i]))
    			{
    				String[] temp=s.split(",");
    				temp[0]=getWord(temp[0]);
    				temp[1]=getWord(temp[1]);
    				for(int j=0;j<wordList.size();j++)
    				{
    					if(wordList.get(j).contains(temp[0])||wordList.get(j).contains(temp[1]))
    					{
    						 if(ParseWord[i]==0)
    	    					 ParseWord[i]=1;
    					}
    				}
    				
    			}
    		}
    	}
    	 for(int i=0;i<ParseWord.length;i++)
    	 {
    		 features.add(new Feature(ParseWord_+i,ParseWord[i]));
    	 }
        
    }

}
