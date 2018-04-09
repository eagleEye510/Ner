package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//NER
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class ExtractDemo {
	private static AbstractSequenceClassifier<CoreLabel> ner;

	public ExtractDemo() {
		InitNer();
	}

	public void InitNer() {
		
		
//		String serializedClassifier = "/home/lxq/NER/classifiers/chinese.misc.distsim.crf.ser.gz"; // chinese.misc.distsim.crf.ser.gz
		String serializedClassifier = "./classifiers/chinese.misc.distsim.crf.ser.gz"; // chinese.misc.distsim.crf.ser.gz
//		System.out.println("123");
		if (ner == null) {
//			ner = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
			ner=CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		}
	}

	public String doNer(String sent) {
		return ner.classifyWithInlineXML(sent);
	}

	public static void main(String args[]) throws IOException {
		
		// BufferedReader str=new BufferedReader(new
		// InputStreamReader(System.in));
//		 String line=str.readLine();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ExtractDemo extractDemo = new ExtractDemo();
//		String txt="12 月 28 日 晚上 7 时 左右 ， 纽约市 北端 的 布朗克斯 一 座 5 层 楼 的 公寓 突发 大火 致 12 人 死亡 。 美国 纽约 消防局 29 日 表示 ， 火灾 是 由 一";
		String txt = br.readLine();
		System.out.println(extractDemo.doNer(txt));
//		
//		String ners=extractDemo.doNer(txt);
//		if(txt.contains("阿里巴巴")){
//			
//			String txt1=ners.replace("<GPE>阿里巴巴</GPE>", "<org>阿里巴巴</org>");
////			txt=txt1;
//			System.out.println(txt1);
//			 
//		}
		System.out.println("Complete!");
	}

}