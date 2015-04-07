package edu.pku.emotion.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
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
    public static final String HOST = "162.105.80.102";
    public static final int PORT = 6789;
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
    
    /**
     * Dump all instance to a single file, for the convenience of later classification
     * 
     * @param data
     * @param path
     */
    public static void dumpAllInstance(ArrayList<Weibo> data, File path) {
        assert path.isDirectory() == false; // path can not exist now.
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            for (Weibo weibo : data) {
                writer.write(weibo.dump() + "\n");
                for (Sentence sentence : weibo.getSentences()) {
                    writer.write(sentence.dump() + "\n");
                }
            }
            writer.flush();
            if (writer != null) writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return;
    }
    
    /**
     * for Chinese, text should be segmented first, such as<br>
     * <code> getEmbedding("情 绪 不 稳 定 的 女 人 啊 。 。 。")</code>
     * <br>for English, use space to segment words.
     * 
     * @param text 
     * @param host normally will be 162.105.80.102
     * @param port normally will be 6789
     * @return vector representation of given text
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public static float[] getEmbedding(String text, String host, int port) throws UnknownHostException, IOException {
        Socket clientSocket = new Socket(host, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeUTF(text + "\n");
        String vector = inFromServer.readLine().trim();
        String[] embedding = vector.split("\\s++");
        float[] res = new float[embedding.length];
        for (int i = 0; i < embedding.length; ++i) res[i] = Float.parseFloat(embedding[i]);
        clientSocket.close();
        return res;
    }
    
    /**
     * 
     * @param out
     * @throws Exception
     */
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
    
    /**
     * 
     * @param writer
     * @param data
     * @throws Exception
     */
    private static void dumpAux(FileWriter writer, ArrayList<Weibo> data) throws Exception {
        for (Weibo weibo : data) {
            for (Sentence sent : weibo.getSentences()) {
                String text = sent.getText();
                if (text.length() == 0) continue;
                writer.write(getSegmentedCharacters(text));
                writer.write("\n");
            }
        }
        return;
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static String getSegmentedCharacters(String text) {
        String res = "";
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c == ' ') continue;
//          detect emotion symbols
            if (c == '[') {
                int pos = i + 2; // should not be empty between two brackets 
                boolean emotion = false;
                while (pos < text.length() && pos - i <= 8) {
                    if (text.charAt(pos) == ']') {
                        emotion = true;
                        res += text.subSequence(i, pos + 1) + " ";
                        i = pos;
                        break;
                    }
                    ++pos;
                }
                if (emotion) continue;
            }
//          detect english words
            if (isEnglishLetter(c)) {
                int pos = i + 1;
                while (pos < text.length() && isEnglishLetter(text.charAt(pos))) ++pos;
                res += text.subSequence(i, pos) + " ";
                i = pos - 1;
                continue;
            }
//          detect number
            if (isNumber(c)) {
                int pos = i + 1;
                while (pos < text.length() && isNumber(text.charAt(pos))) ++pos;
                res += text.subSequence(i, pos) + " ";
                i = pos - 1;
                continue;
            }
            res += c + " ";
        }
        return res.trim();
    }
    
    private static boolean isEnglishLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    
    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
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
