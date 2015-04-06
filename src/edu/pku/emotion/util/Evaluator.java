package edu.pku.emotion.util;

import java.util.ArrayList;

import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class Evaluator {
    
    /**
     * Evaluate emotion for weibo
     * 
     * @param pred
     * @param gold
     */
    public static void evaluateEmotionClassification(ArrayList<Weibo> pred, ArrayList<Weibo> gold) {
        validCheck(pred, gold);
        double precision = 0.0;
        for (int i = 0; i < pred.size(); ++i) {
            Weibo w1 = pred.get(i), w2 = gold.get(i);
            if (w1.getWeiboEmotionType1() == w2.getWeiboEmotionType1()) precision += 1.0;
            else precision += 0.0;
            
            if (w2.getWeiboEmotionType2() == w1.getWeiboEmotionType2()) precision += 1.0;
            else if (w2.getWeiboEmotionType2() == w1.getWeiboEmotionType1()) precision += 0.5;
            else precision += 0.0;
        }
        precision = precision / pred.size();
        System.err.println("Average precision: " + precision);
        return;
    }
    
    /**
     * Evaluate opinion & emotion for sentence
     * 
     * @param pred
     * @param gold
     */
    public static void evaluateExpressionIdent(ArrayList<Weibo> pred, ArrayList<Weibo> gold) {
        validCheck(pred, gold);
        double opinion = 0.0;
        double emotion = 0.0;
        int opinionCnt = 0, emotionCnt = 0;
        for (int i = 0; i < pred.size(); ++i) {
            ArrayList<Sentence> w1 = pred.get(i).getSentences(), w2 = gold.get(i).getSentences();
            if (w1.size() != w2.size()) {
                System.err.println("Wrong sentence number: pred " + w1.size() + " gold: " + w2.size());
                System.exit(1);
            }
            for (int j = 0; j < w1.size(); ++j) {
                Sentence s1 = w1.get(j), s2 = w2.get(j);
                ++opinionCnt;
                if (s1.isOpinionated() == s2.isOpinionated()) ++opinion;
                if (s2.isOpinionated()) {
                    ++emotionCnt;
                    if (s2.getEmotionType1() == s1.getEmotionType1()) emotion += 1.0;
                    else emotion += 0.0;
                    
                    if (s2.getEmotionType2() == s1.getEmotionType2()) emotion += 1.0;
                    else if (s2.getEmotionType2() == s1.getEmotionType1()) emotion += 0.5;
                    else emotion += 0.0;
                }
            }
        }
        opinion = opinion / opinionCnt;
        emotion = emotion / emotionCnt;  // this value could be greater than 1
        System.err.println("Opinion classfication accuracy: " + opinion);
        System.err.println("Emotion classfication accuracy: " + emotion);
        return;
    }
    
    /**
     * Do some basic check
     * 
     * @param pred
     * @param gold
     */
    private static void validCheck(ArrayList<Weibo> pred, ArrayList<Weibo> gold) {
        if (pred.size() == 0 || pred.size() != gold.size()) {
            System.err.println("Wrong size: pred " + pred.size() + ", gold: " + gold.size());
            System.exit(1);
        }
        return;
    }

}
