package com.zkdp.nlp.hadoopner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.print.DocFlavor.STRING;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;


public class reducer_article {
	//private static Logger logger = Logger.getLogger(Mapper.class);  
	public void print_key_and_mongoids(String key,HashSet<String> mongoid){	
		if(key.equals("")){
			return ;
		}
		
		System.out.print(key);		
		
		for ( String mongoid_val: mongoid){
			
			System.out.print("\t"+mongoid_val+"end");
		}
		System.out.print("LxQ");
		System.out.println("");

	}
// 		main 
	public static void main(String[] args) throws IOException{
		BufferedReader sc = null;
		reducer_article reducer = new reducer_article();
		String sentence = "" ;
		sc=new BufferedReader(new InputStreamReader(System.in));

		String pre_key = new String("");
		HashSet<String> mongoid = new HashSet<String>();
		sentence = sc.readLine();
		while( (sentence = sc.readLine()) != null ){
		
			String[] arry_words = sentence.split("\t");
			if( !(arry_words[0].equals(pre_key) )){
				//get new key
				reducer.print_key_and_mongoids(pre_key, mongoid);
				pre_key = arry_words[0];
				mongoid.clear();
				mongoid.add(arry_words[1]);
			}else{
				mongoid.add(arry_words[1]);
				
			}
		}
		reducer.print_key_and_mongoids(pre_key, mongoid);
		

	}
}

