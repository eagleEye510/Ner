package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.stanford.nlp.ie.crf.CRFClassifier;

public class main 
{

	//public static Properties props = new Properties();
	public static void main(String[] args) throws IOException  {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		String line="http://blog.sina.com.cn/s/blog_726054450102yz7o.html 抚顺县诚至工程有限公司是一家专注于工程、投资、企业的服务机构。";
		String line1 = line.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t","").replaceAll("\r", "");
//		while((line=br.readLine())!=null){
		SegDemo seg = new SegDemo();

		CRFClassifier crf = SegDemo.getcfr();

		ExtractDemo extractDemo = new ExtractDemo(); 

		//获取分词						
		String segResult = seg.doSegment(line1, crf);
//获取ner
		String nerResult = extractDemo.doNer(segResult);
		System.out.println(nerResult);
//		}

	}

}