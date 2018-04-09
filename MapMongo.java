package com.zkdp.nlp.hadoopner;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

public class MapMongo {
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		MongoDBJDBC mogo = new MongoDBJDBC ();
		MongoCursor<Document> cursor = mogo.getCursor();
		int count=0;
		String dirName = "./mogodb_data";
//		String dirName = "./mogodb_data";
		String fileName = "./mogodb_data/mogodb_article.txt";
//		File file = new File(dirName + fileName);
		File file = new File(fileName);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(file);	
		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setOut(printStream);
	
		
		while(cursor.hasNext()){
			Document doc = cursor.next();
			count++;
			System.out.println(doc.toJson());
//			if(count == 70000){break;}
			String str="_id";
//			System.out.println(count);
			if(doc.toJson().indexOf(str)==-1){
				
				break;
			}
			
	}

}	
}
