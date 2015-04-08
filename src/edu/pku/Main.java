package edu.pku;

import java.io.File;
import java.util.ArrayList;

import edu.pku.emotion.feat.FeatureExtractor;
import edu.pku.emotion.feat.LabelMap;
import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Main {
    
    private static final String trainClassIns = "data/train_class.txt";
    private static final String testClassIns = "data/test_class.txt";
//    private static final String trainExpressionIns = "data/train_expression.txt";
//    private static final String testExpressionIns = "data/test_expression.txt";
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {        
        System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.trainClass));        
        ArrayList<Weibo> train_data = IOUtils.loadClassTrainData();
        System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.testClass));
        ArrayList<Weibo> test_data = IOUtils.loadClassTestData();
        
        System.err.println("Start to extract feature......");
        for (Weibo weibo : train_data) {
            FeatureExtractor.extract(weibo);            
//            debug purpose only
//            List<Feature> features = weibo.getFeatures();
//            System.out.println("Weibo: ");
//            System.out.println(weibo);
//            System.out.println("Features: ");
//            for (Feature f : features)
//                System.out.println(f);
        }
        IOUtils.dumpAllInstance(train_data, new File(trainClassIns));
        for (Weibo weibo : test_data) {
            FeatureExtractor.extract(weibo);
        }
        IOUtils.dumpAllInstance(test_data, new File(testClassIns));
        System.err.println("Label mapping: \n" + LabelMap.getLabelMapping());
        System.err.println("Done");
        return;
    }
}
