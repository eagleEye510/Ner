package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.bson.BasicBSONObject;
import org.bson.Document;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCursor;

import edu.stanford.nlp.ie.crf.CRFClassifier;//�ִ�
//mappper_article
public class mapper_title {
// private static Logger logger = Logger.getLogger(Mapper.class);  
 public int extract_ner(String line,String ner,String begin_seg,String end_seg,String URL,Integer real_time,Integer create_time) {

	if( line.equals(null)){

		return 0;
	}
	int head=line.indexOf(begin_seg);
	if( head == -1 ){
		
		return 0;
	}else{
		head += begin_seg.length();
	}
	int tail=line.indexOf(end_seg,head);
	if( tail == -1 ){
		
		return 0;
	}

	String res = line.substring(head, tail);

	String[] l = res.split(" "); 
	

	for (String string : l) {
		
	System.out.println(ner + '-' + string+"\t"+create_time+"&"+real_time+"#"+URL);
	}

	return 0;
}

 
 
 
public Vector<MyString> read_ner_seg()  
{

	InputStream in = null;
	try {

//		in = new FileInputStream("D://workspace//Eclipse//MapRed2//user_data//ner_segs.txt");
//		in = new FileInputStream("./temp_data/config_ner.txt");
		in = new FileInputStream("./user_data/ner_segs.txt");
	} catch (FileNotFoundException e1) {

	
	}
	
		BufferedReader br= new BufferedReader(new InputStreamReader(in));
	   
	if(br == null ){
		System.out.println("can not open the file");
	}
	String inputLine = null;
	String ner = null, begin_seg = null, end_seg = null;
	Vector<MyString> ner_segs = new Vector<MyString>();
	try {
		
		while((inputLine = br.readLine()) != null){
            
			MyString ner_seg = new MyString();
			String[] arr = inputLine.split("\t");
			if (arr.length != 3) {
				 
				continue;
			}
			ner = arr[0]; begin_seg = arr[1]; end_seg = arr[2];
			ner_seg.setFirstS(ner);
			ner_seg.setSecondS(begin_seg);
			ner_seg.setThirdS(end_seg);
			ner_segs.addElement(ner_seg);

		}
	}
	 catch (IOException e) {

	}
	
	return ner_segs;
	
}



	public static void main(String[] args) throws IOException {


		SegDemo seg = new SegDemo();

		CRFClassifier crf = seg.getcfr();

		ExtractDemo extractDemo = new ExtractDemo(); 

		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
			mapper_title mp = new mapper_title();
			Vector<MyString> ner_segs = mp.read_ner_seg();
//			System.out.println(ner_segs);
			String line;
//处理行
				while((line = br.readLine())!= null){
//转对象		
				JSONObject json = new JSONObject();	
//获得正文段落数组	
				
					 String lines=line.replaceAll("\t","").replaceAll("\r\n", "").replace("\r", "").
	                   replace("\n", "").replace("\\s","");
					JSONObject jsonObj = JSON.parseObject(lines);

				
//					System.out.println("------------------");
//					System.out.println(jsonObj);
//中转正文段落		
					String str="None";
					if(line.indexOf(str)!=-1){
					continue;
				}else{
				
				JSONArray array = jsonObj.getJSONArray("title");
				
				
//				System.out.println("------------------");
//				System.out.println(array);
				
				
				StringBuffer reslut = new StringBuffer();
//获得URL字符对象
				String url = (String) jsonObj.get("url");
				Integer real_time = (Integer) jsonObj.get("real_time");
				Integer create_time = (Integer) jsonObj.get("create_time");
//拼接正文段落获得正文内容
				for (int j=0;j<array.size();j++) {
					String tmp_article = (array).get(j).toString();
//					tmp_article.replaceAll("\\p{Punct}|\\d","").replaceAll("\r\n", " ").replace("\r", " ").
//	                   replace("\n", " ");
					reslut.append(tmp_article);
					
					System.out.println(reslut);
					}

				String reslut_end = reslut.toString();
//去掉\r\n
				reslut_end = reslut_end.replaceAll("\\p{Punct}|\\d","").replaceAll("\r\n", " ").replace("\r", " ").
				                   replace("\n", " ");
//				reslut_end = reslut_end.replaceAll("\r\n", "").replaceAll(" ", "").replaceAll("\n", "")
//						.replaceAll("\t","");
//获取分词						
				String segResult = seg.doSegment(reslut_end, crf);
//获取ner
				String nerResult = extractDemo.doNer(segResult);

					for( int i=0; i< ner_segs.size(); ++i){
//						System.out.println(ner_segs.size());
						mp.extract_ner(nerResult , ner_segs.get(i).getFirstS(), ner_segs.get(i).getSecondS(),ner_segs.get(i).getThirdS(),url,real_time,create_time);
						
					}
				
				}
				}
			
		}
}
