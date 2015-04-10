package edu.pku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.pku.emotion.feat.FeatureExtractor;
import edu.pku.emotion.feat.LabelMap;
import edu.pku.emotion.feat.RAEFeatureExtractor;
import edu.pku.emotion.util.DicModel;
import edu.pku.emotion.util.IOUtils;
import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

import java.util.*;
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
    	 System.err.println("Start to load segment model");
         DicModel a=new DicModel("load");
    	System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.trainClass));        
        ArrayList<Weibo> train_data = IOUtils.loadClassTrainData();
        System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.testClass));
        ArrayList<Weibo> test_data = IOUtils.loadClassTestData();
        System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.trainExpression));
        ArrayList<Weibo> train_expression = IOUtils.loadExpressionTrainData();
        System.err.println("Start to load data from: " + IOUtils.getConfValue(IOUtils.testExpression));
        ArrayList<Weibo> test_expression = IOUtils.loadExpressionTestData();
        train_data.addAll(train_expression);
        test_data.addAll(test_expression);

        
        System.err.println("Number of train instance: " + train_data.size());
        System.err.println("Number of test instance: " + test_data.size());
        
        System.err.println("Start to extract feature......");
        extractAndDump(train_data, new File(trainClassIns));
        extractAndDump(test_data, new File(testClassIns));
        
        System.err.println("Label mapping: \n" + LabelMap.getLabelMapping());
        System.err.println("Done");
        return;
    }
    
    /**
     * 
     * @param data
     * @param path
     */
    private static void extractAndDump(ArrayList<Weibo> data, File path) {
        int cnt = 0;
        for (Weibo weibo : data) {
            ++cnt;
            if (cnt % 10 == 0) System.err.println("train_data: " + cnt);
            FeatureExtractor.extract(weibo);
        }
        IOUtils.dumpAllInstance(data, path);
        return;
    }
}
