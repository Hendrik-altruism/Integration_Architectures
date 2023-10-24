package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;

import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class QuickStart {


    public static void main( String[] args ) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("Salesmen_Performances");
        ManagePersonalImpl control = new ManagePersonalImpl(database);

        SalesMan testSalesman = new SalesMan("Hendrik", "Oude Hengel", 1);

        EvaluationRecord testRecord = new EvaluationRecord(1, 5);

        control.createSalesMan(testSalesman);

        control.addPerformanceReord(testRecord, 1);

        System.out.println(control.querySalesMan("firstname", "Hendrik"));

        System.out.println(control.readSalesMan(1));

        System.out.println(control.readEvaluationRecords(1));
        /* Test
        Document document = new Document();
        document.append("firstname" , "Sascha");
        document.append("lastname" , "Alda");
        document.append("id" , 90133);

        // ... now storing the object
        collection.insertOne(document);

        collection.find().first();
        */
        try {
            //client = new MongoClient("localhost", 27017);

            // Get database 'highperformance' (creates one if not available)
            //supermongo = client.getDatabase("highperformance");


        } catch (Exception e){
            System.out.println(e);
        }
    }
}