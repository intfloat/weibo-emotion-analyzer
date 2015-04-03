package edu.pku.emotion.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class IOUtils {
    
    private static final String confPath = "conf/weibo.conf";
    public static final String trainClass = "train_classification";
    public static final String testClass = "test_classification";
    public static final String trainExpression = "train_expression";
    public static final String testExpression = "test_expression";
    private static HashMap<String, String> conf = null;
    
    public static ArrayList<Weibo> loadClassTrainData() throws Exception {
        if (conf == null) loadConf();        
        return XMLUtils.readXML(new File(conf.get(trainClass)));
    }
    
    public static ArrayList<Weibo> loadClassTestData() throws Exception {
        if (conf == null) loadConf();        
        return XMLUtils.readXML(new File(conf.get(testClass)));
    }
    
    public static ArrayList<Weibo> loadExpressionTrainData() throws Exception {
        if (conf == null) loadConf();        
        return XMLUtils.readXML(new File(conf.get(trainExpression)));
    }
    
    public static ArrayList<Weibo> loadExpressionTestData() throws Exception {
        if (conf == null) loadConf();        
        return XMLUtils.readXML(new File(conf.get(testExpression)));
    }
    
    public static void dumpAllCharacters(File out) throws Exception {
        if (conf == null) loadConf();
        FileWriter writer = new FileWriter(out);
        ArrayList<Weibo> data = IOUtils.loadClassTrainData();        
        dumpAux(writer, data);
        data = IOUtils.loadClassTestData();
        dumpAux(writer, data);
        data = IOUtils.loadExpressionTrainData();
        dumpAux(writer, data);
        data = IOUtils.loadExpressionTestData();
        dumpAux(writer, data);
        writer.flush();
        return;
    }
    
    private static void dumpAux(FileWriter writer, ArrayList<Weibo> data) throws Exception {
        for (Weibo weibo : data) {
            for (Sentence sent : weibo.getSentences()) {
                String text = sent.getText();
                if (text.length() == 0) continue;
                for (int i = 0; i < text.length(); ++i) {
                    char c = text.charAt(i);
                    if (c == ' ') continue;
                    writer.write(c + " ");
                }
                writer.write("\n");
            }
        }
        return;
    }
    
    /**
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String getConfValue(String key) throws Exception {
        if (conf == null) loadConf();
        if (!conf.containsKey(key))
            throw new Exception("Can not find key: " + key);
        return conf.get(key);
    }
    
    @SuppressWarnings("resource")
    private static void loadConf() throws Exception {
        if (conf == null) conf = new HashMap<String, String>();
        File file = new File(confPath);
        Scanner reader = new Scanner(file);        
        while (reader.hasNextLine()) {
            String line = reader.nextLine().trim();
            if (line.length() == 0) continue;
            if (line.startsWith("#")) continue;
            if (!line.contains(":")) throw new Exception("Invalid configuration: " + line);
            int pos = line.indexOf(':');
            String key = line.substring(0, pos).trim();
            String value = line.substring(pos + 1).trim();
            conf.put(key, value);
        }
        return;
    }

}
