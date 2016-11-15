package cl.citiaps.neo4j.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {
	
		public static void main(String[] args) throws IOException {
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient("localhost", 27018);
			MongoDatabase db = mongoClient.getDatabase("db");

			MongoCollection<Document> collection = db.getCollection("collection");
			
			
			Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "neo4j" ) );
			Session session = driver.session();

			System.out.println(session.isOpen());

			List<MongoCollection<Document>> collections = new ArrayList<>();
			collections.add(collection);

			TwitterProcessor twitterProcessor = new TwitterProcessor();

			twitterProcessor.process(collection, session);

			session.close();
			driver.close();

		}
		
	

}