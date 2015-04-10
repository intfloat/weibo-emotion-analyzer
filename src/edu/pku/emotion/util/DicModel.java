package edu.pku.emotion.util;

import java.util.ArrayList;
import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class DicModel {
	private static ArrayList<String> wordList;
	private static ArrayList<String> emotionList;
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
	public void setWordlist() throws Exception
	{
		wordList=IOUtils.loadWordList();
	}
	public void setEmotionlist() throws Exception
	{
		emotionList=IOUtils.loadEmotionWord();
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
	public static ArrayList<String> loadWordList()
	{
		return wordList;
	}
	public static ArrayList<String> loadEmotionList()
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
