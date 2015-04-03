package edu.pku;

import java.util.ArrayList;
import java.util.List;

import edu.pku.emotion.feat.Feature;
import edu.pku.emotion.feat.FeatureExtractor;
import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Main {
	
	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {	    
	    System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.trainClass));
		ArrayList<Weibo> train_data = IOUtils.loadClassTrainData();
		System.err.println("Start to extract feature......");
		for (Weibo weibo : train_data) {
		    List<Feature> features = FeatureExtractor.extract(weibo);
		    System.out.println("Weibo: ");
		    System.out.println(weibo);
		    System.out.println("Features: ");
		    for (Feature f : features)
		        System.out.println(f);
		}
		System.err.println("Done");
		return;
	}
}
