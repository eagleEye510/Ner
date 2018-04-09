package com.zkdp.nlp.hadoopner;

import org.bson.Document;

import com.mongodb.Bytes;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

//import com.mongodb.Block;
//import com.mongodb.DB;
//import com.mongodb.Mongo;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoCredential;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Indexes;
//import com.mongodb.client.result.DeleteResult;
//import com.mongodb.client.result.UpdateResult;
//
//import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MongoDBJDBC {

//	private MongoClient mongoClient;
//	private MongoDatabase database;
//	
	public MongoCollection<Document> collection;

	@SuppressWarnings("deprecation")
	public MongoDBJDBC() {
//		mongoClient = new MongoClient("192.168.1.193", 27017);
//		mongoClient = new MongoClient("192.168.1.106", 9999);
		
		 String user = "zhfr_mongodb_root"; // the user name
		 String database = "portal"; // the name of the database in which the user is defined
		 String password = "zkfr_DUBA@0406mgdb#com"; // the password as a character array
//创建权限admin 设置最高权限 
		 MongoCredential credential = MongoCredential.createCredential(user, "admin", password.toCharArray());

//		 ssh 建立连接
		 MongoClientOptions options = MongoClientOptions.builder().sslEnabled(false).build();

		 MongoClient mongoClient = new MongoClient(new ServerAddress("122.115.46.176", 38228), Arrays.asList(credential), options);
		 
		 System.out.println(mongoClient);
		 
		MongoDatabase db = mongoClient.getDatabase(database);

		collection = db.getCollection("web_data");
		
		 

	    
	

	}
//	public static void main(String[] args){
//		MongoDBJDBC xx = new MongoDBJDBC();
//		xx.getCursor();
//	}

	public MongoCursor<Document> getCursor(){
		
		MongoCursor<Document> cursor = collection.find().batchSize(80).iterator();
		 
		
		
//		while(cursor.hasNext()){
//			System.out.println(cursor.next());
//		}

		return cursor;
	} 
	
}