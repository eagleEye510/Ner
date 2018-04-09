package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import edu.stanford.nlp.ie.crf.CRFClassifier;
//mappper_article
public class mapper_article {
 private static Logger logger = Logger.getLogger(mapper_article.class);  
 String res1 ;
 public int extract_ner(String line,String ner,String begin_seg,String end_seg,String mongoid,String crawling_time,String create_time ) throws IOException {
	
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
	
	 String filterDic;
	 Boolean flag = null;
	 
//	BufferedReader appellation_filter = new BufferedReader(new FileReader("./user_data/appellation_filter.txt"));
//	HashSet<String> appellfilterSet = new HashSet<String>();

//	for(String filter:appellfilterSet){
//		if (res.contains(filter)){
//			flag=true;
//			logger.fatal(res);
//		}
//	}
//显示NER结果
//	String[] l = res.split(">"); 
	
	
	String[] l = res.split(" <");

	for (String string : l) {
//		logger.fatal("ner"+string);
			if(string.contains("某")){continue;}
			System.out.println(ner + "NER" + string+"\t"+"create_time"+create_time+"&"+crawling_time+"ID:"+mongoid);
			
	
	}
	
	return 0;
}
 
 
 
 
 
 
 
//---------------------------各命名实体配置文件-------------------------------------------------------
public Vector<MyString> read_ner_seg()  
{

	InputStream in = null;
	try {
//		in = new FileInputStream("D://workspace//Eclipse//MapRed2//user_data//ner_segs.txt");
		in = new FileInputStream("./user_data/ner_segs.txt");
//		in = new FileInputStream("/home/lxq/NER/user_data/ner_segs.txt");
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
//			logger.fatal("input "+inputLine);
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
	}catch (IOException e){

	}
	
	return ner_segs;
	
}

//---------------------------各命名实体配置文件----------------------------------------------------------------



	public static void main(String[] args) throws IOException {


		SegDemo seg = new SegDemo();

		CRFClassifier crf = seg.getcfr();

		ExtractDemo extractDemo = new ExtractDemo(); 

		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
			mapper_article mp = new mapper_article();
			Vector<MyString> ner_segs = mp.read_ner_seg();
//			System.out.println(ner_segs);
			String line;
			boolean flag = false;
//处理行
				while((line = br.readLine())!= null){
//转对象		
				JSONObject json = new JSONObject();	
//获得正文段落数组	
				
				
				JSONObject jsonObj = JSON.parseObject(line);
//				 JSONArray  jsonObj = JSONArray.parseArray(line);

				
//					System.out.println("------------------");
//					System.out.println(jsonObj);
//中转正文段落		
					String str="None";
					if(line.indexOf(str)!=-1){
					continue;
				}else{
				
				JSONArray array = jsonObj.getJSONArray("article");

				
				StringBuffer reslut = new StringBuffer();
//获得URL字符对象
//				String url = (String) jsonObj.get("url");
				String id = (String) jsonObj.get("_id");
				String mongoid=id.substring(10, id.indexOf("')"));
//				ObjectId id = (ObjectId)jsonObj.get( "_id" );
//				String mongoid=String.valueOf(id);
				String crawling_time = (String) jsonObj.get("crawling_time");
				String create_time = (String) jsonObj.get("create_time");
				
//				System.out.println(jsonObj.get("real_time"));
//				Integer create_time = Integer.valueOf(( jsonObj.get("create_time").toString()));

//				Integer create_time = ((BigDecimal)jsonObj.get("create_time")).intValue();
//				System.out.println(jsonObj.get("create_time"));
//拼接正文段落获得正文内容
				for (int j=0;j<array.size();j++) {
					String tmp_article = (array).get(j).toString();
					reslut.append(tmp_article);
					}
				String reslut_end = reslut.toString();
//去掉\r\n
//				reslut_end = reslut_end.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t","").replaceAll("\r", "").replaceAll("#", "");
//获取分词						
				String segResult = seg.doSegment(reslut_end, crf);
//				logger.fatal(segResult);
				
//获取ner
//				String nerResult = extractDemo.doNer(segResult);
				
				
//				----------filter alibaba---------
				
				
				String nerResult;
				String ners=extractDemo.doNer(segResult);
				
				String filterDic;
				BufferedReader exceptFilter = new BufferedReader(new FileReader("./user_data/alibaba_filter.txt"));
//				BufferedReader exceptFilter = new BufferedReader(new FileReader("/home/lxq/NER/user_data/alibaba_filter.txt"));
				TreeSet<String> exceptFilterSet = new TreeSet<String>();
				while ((filterDic = exceptFilter.readLine()) != null) {
					exceptFilterSet.add(filterDic.trim());
				}
				for (String except : exceptFilterSet) {

					if (segResult.contains(except)) {
//						logger.fatal("except"+except);
						flag=true;
					}
				}				
				if(flag==true){
					
					nerResult=ners.replaceAll("<GPE>阿里巴巴</GPE>", "<ORG>阿里巴巴</ORG>").replaceAll("<GPE>阿里</GPE>", "<ORG>阿里</ORG>").replaceAll("<MISC>阿里巴巴</MISC>", "<ORG>阿里巴巴</ORG>").replaceAll("<PERSON>阿里巴巴</PERSON>", "<ORG>阿里巴巴</ORG>").replaceAll("<PERSON>乐视</PERSON>", "<ORG>乐视</ORG>").replace("<GPE>阿里 帝国</GPE>", "<ORG>阿里 帝国</ORG>").replace("<PERSON>张家口</PERSON>", "<GPE>张家口</GPE>");
//					 logger.fatal("****"+nerResult);
				}else{
					nerResult = ners;
//					logger.fatal("---------"+nerResult);
				}
				
//		------------filter alibab----------------
////				Vector<MyString> ner_segs = mp.read_ner_seg();

					for( int i=0; i< ner_segs.size(); ++i){
//						System.out.println(ner_segs.size());
//						logger.fatal("1"+ner_segs.get(i).getFirstS());
//						logger.fatal("3"+ner_segs.get(i).getThirdS());
						mp.extract_ner(nerResult , ner_segs.get(i).getFirstS(), ner_segs.get(i).getSecondS(),ner_segs.get(i).getThirdS(),mongoid,crawling_time,create_time);
						
					}
				
				}
				}
			
		}
}
