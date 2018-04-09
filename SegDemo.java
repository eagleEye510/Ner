package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;

public class SegDemo 
{
	public static String doSegment(String data, CRFClassifier c) {
		String[] strs = (String[]) c.segmentString(data).toArray();

		StringBuffer buf = new StringBuffer();
		
		
		for (String s : strs) {
			buf.append(s + " ");
		}

		return buf.toString();
	}
	
	public static CRFClassifier getcfr(){
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "data");
//		props.setProperty("serDictionary", "/home/lxq/NER/data/dict-chris6.ser.gz");
		props.setProperty("serDictionary", "./data/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		CRFClassifier classifier = new CRFClassifier(props);
//		����
//		classifier.loadClassifierNoExceptions("/home/lxq/NER/data/ctb.gz", props);
		classifier.loadClassifierNoExceptions("./data/ctb.gz", props);
		classifier.flags.setProperties(props);
		return classifier;
	}
	//public static Properties props = new Properties();
	public static void main(String[] args) throws IOException  {
		//System.out.println("abc");
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "data");
//		props.setProperty("serDictionary", "/home/lxq/NER/data/dict-chris6.ser.gz");
		props.setProperty("serDictionary", "./data/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");
		CRFClassifier classifier = new CRFClassifier(props);
//		classifier.loadClassifierNoExceptions("/home/lxq/NER/data/ctb.gz", props);
		classifier.loadClassifierNoExceptions("./data/ctb.gz", props);
		classifier.flags.setProperties(props);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//		String txt="12月28日晚上7时左右，纽约市北端的布朗克斯一座5层楼的公寓突发大火致12人死亡。美国纽约消防局29日表示，火灾是由一名3岁半的儿童玩厨房炉灶引起的。";
		String txt=br.readLine();
		String ret = doSegment(txt, classifier);
		System.out.println(ret);
		//System.out.println("abc");

	}

}