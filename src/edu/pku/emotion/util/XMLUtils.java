package edu.pku.emotion.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pku.instance.Sentence;
import edu.pku.instance.Weibo;

/**
 * 
 * @author intfloat@pku.edu.cn
 * @author labyrinth@pku.edu.cn
 *
 */
public class XMLUtils {
    
    private static DocumentBuilderFactory dbf;
    private static DocumentBuilder db;
    private static Document doc;
    private static Element root;
    
    /**
     * 
     * @param file
     * @return
     * @throws Exception
     */
    public static ArrayList<Weibo> readXML(File file) throws Exception {
        if (!file.exists() || file.isDirectory()) return null;
        readXMLAux(file);
        NodeList nodeList = root.getElementsByTagName("weibo");
        ArrayList<Weibo> result = new ArrayList<Weibo>();
//      for every weibo
        for (int i = 0; i < nodeList.getLength(); ++i) {
            if (i % 200 == 0) System.err.println(i);
            Element weibo = (Element) nodeList.item(i);
            int id = Integer.parseInt(weibo.getAttribute("id"));
            String emotion1 = null;
            String emotion2 = null;
            if (weibo.hasAttribute("emotion-type1"))
                emotion1 = weibo.getAttribute("emotion-type1");
            if (weibo.hasAttribute("emotion-type2"))
                emotion2 = weibo.getAttribute("emotion-type2");
            Weibo val = new Weibo(id, emotion1, emotion2);
            NodeList ss = weibo.getElementsByTagName("sentence");
            assert ss.getLength() > 0;
//          for every sentence in current weibo
            for (int j = 0; j < ss.getLength(); ++j) {
                Element sentence = (Element) ss.item(j);
                int sid = Integer.parseInt(sentence.getAttribute("id"));
                String text = sentence.getTextContent();
                boolean opinion = false;                
                if (sentence.hasAttribute("opinionated")) {                    
                    String op = sentence.getAttribute("opinionated");
                    if (op.equalsIgnoreCase("Y")) opinion = true;
                }
                String em1 = null;
                String em2 = null;
                String expression = null;
                if (sentence.hasAttribute("emotion-1-type"))
                    em1 = sentence.getAttribute("emotion-1-type");
                if (sentence.hasAttribute("emotion-2-type"))
                    em2 = sentence.getAttribute("emotion-2-type");
                if (sentence.hasAttribute("keyexpression1"))
                    expression = sentence.getAttribute("keyexpression1");
//              there is no opinion label, probably be test data
                if (null == em1 || null == em2) val.addSentence(new Sentence(sid, text));
                else val.addSentence(new Sentence(sid, opinion, em1, em2, expression, text));
            }
            result.add(val);
        }
        return result;
    }
    
    /**
     * 
     * @param file
     */
    private static void readXMLAux(File file) {
        dbf = DocumentBuilderFactory.newInstance();
        db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        doc = null;
        try {
            doc = db.parse(file);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
        root = doc.getDocumentElement();  
    }
    
}
