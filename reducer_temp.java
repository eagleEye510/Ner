package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class reducer_temp {
	public void mprint(String sentence,Map<String,Integer> map){
//		String regex = "\t ";
		String[] words = sentence.split("	");
//		System.out.println("sentence after split : ");
		for (int i = 0; i<words.length; i++){
//			System.out.println("<"+words[i]+">");
		}
				
		if(map.containsKey(words[0]))
		{
			
				// ˵��map�У����ڸ�Ԫ��
			int num = map.get(words[0]);
			map.put(words[0], ++num);
	
		}	
			else{
				// ��һ��key
				map.put(words[0],1);
				}
		if(words[0]==words[0].intern()&&words[1]!=words[1].intern()){
			
			words[0]+=words[1];
			System.out.println(words[0]);
			
		}else if(words[1]==words[1].intern()){
			
			words[1].concat(words[1].intern());
		}
}
	

	public static void main(String[] args) throws IOException{
		reducer_temp hm = new reducer_temp();
		BufferedReader sc = null;
		Map<String,Integer> map = new HashMap<String,Integer>();

				String sentence = "a;lsdhfaklfho p]" ;
		sc=new BufferedReader(new InputStreamReader(System.in));
		
		do{

			try {
				sentence = sc.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (sentence == null){
				
				break;
			}
			
			if (sentence.indexOf("-") != -1 && sentence.indexOf("\t") != -1){
				
				hm.mprint(sentence,map);
			}
			else{
				continue;
			}
			
			
			
		}while(true);
	}
}
