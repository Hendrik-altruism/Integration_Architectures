package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class QuickStart {


    public static void main( String[] args ) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("Salesmen_Performances");
        MongoCollection<Document> collection = database.getCollection("Salesmen");

        Document document = new Document();
        document.append("firstname" , "Sascha");
        document.append("lastname" , "Alda");
        document.append("id" , 90133);

        // ... now storing the object
        collection.insertOne(document);

        collection.find().first();

        try {
            //client = new MongoClient("localhost", 27017);

            // Get database 'highperformance' (creates one if not available)
            //supermongo = client.getDatabase("highperformance");


        } catch (Exception e){
            System.out.println(e);
        }
    }
}