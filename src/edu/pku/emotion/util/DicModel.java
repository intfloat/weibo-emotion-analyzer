package edu.pku.emotion.util;

import java.util.HashSet;
import java.util.Properties;

import edu.pku.Main;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

/**
 * 
 * @author labyrinth@pku.edu.cn, intfloat@pku.edu.cn
 *
 */
public class DicModel {
	private static HashSet<String> wordList;
	private static HashSet<String> emotionList;
	private static CRFClassifier segmentor;
	private static LexicalizedParser lp;
	
	public DicModel(String word) throws Exception
	{
		setWordlist();
		setEmotionlist();
		setSegmentor();
		setParser();	
	}
	
	public DicModel() throws Exception
	{
		setSegmentor();
		setParser();
	}
	
	private void setWordlist() throws Exception
	{
		wordList = new HashSet<String>(IOUtils.loadWordList());
	}
	
	public void setEmotionlist() throws Exception
	{
		emotionList = new HashSet<String>(IOUtils.loadEmotionWord());
		if (Main.DEBUGMODE) {
    		int cnt = 0;
    		for (String s : emotionList) {
    		    if (cnt > 10) break;
    		    System.err.println(s);
    		    cnt++;
    		}
		}
	}
	
	public void setSegmentor()
	{
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "segment");
		props.setProperty("serDictionary","segment/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		segmentor = new CRFClassifier(props);
		segmentor.loadClassifierNoExceptions("segment/ctb.gz", props);
		segmentor.flags.setProperties(props);
	}
	
	public void setParser()
	{
		String grammars = "edu/stanford/nlp/models/lexparser/chinesePCFG.ser.gz";  
		lp = LexicalizedParser.loadModel(grammars);  
	}
	
	public static HashSet<String> loadWordList()
	{
		return wordList;
	}
	
	public static HashSet<String> loadEmotionList()
	{
		return emotionList;
	}
	
	public static CRFClassifier loadSegment()
	{
		return segmentor;
	}
	
	public static LexicalizedParser loadParser()
	{
		return lp;
	}

}
