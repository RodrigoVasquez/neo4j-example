package cl.citiaps.neo4j.main;

import java.io.IOException;

import org.bson.Document;
import org.neo4j.driver.v1.Session;

import com.mongodb.client.MongoCollection;

public class TwitterProcessor{
	

	public void process(MongoCollection<Document> collection, Session session) throws IOException {


		for (Document doc : collection.find().limit(100)) {
			
			Document user = (Document)doc.get("user");
			if (doc.get("in_reply_to_screen_name") == null ) 
				continue;
			
			String replyToUserId = doc.getString("in_reply_to_screen_name").toLowerCase();
			String userId = user.getString("screen_name").toLowerCase();
						
			System.out.println(userId + "   " +replyToUserId);
			

			session.run( String.format("CREATE (%s:User {screen_name:\"%s\"})", userId, userId) );
			session.run( String.format("CREATE (%s:User {screen_name:\"%s\"})", replyToUserId, replyToUserId) );
			
			session.run( String.format("CREATE (%s)-[:REPLY]->(%s)", userId, replyToUserId) );

		}

	}


	
	
	

}
