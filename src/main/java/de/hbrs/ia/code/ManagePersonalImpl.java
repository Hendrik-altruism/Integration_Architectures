package de.hbrs.ia.code;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DBCollectionCountOptions;
import com.mongodb.client.model.Filters;
import de.hbrs.ia.code.interfaces.ManagePersonal;
import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagePersonalImpl implements ManagePersonal {


    private MongoCollection<Document> salesmen_collection;

    private MongoCollection<Document> performance_collection;

    public ManagePersonalImpl(MongoDatabase database) {
        salesmen_collection = database.getCollection("Salesmen");
        performance_collection = database.getCollection("Performances");
    }

    public void createSalesMan( SalesMan record ){
        salesmen_collection.insertOne(record.toDocument());
    }

    public void addPerformanceReord(EvaluationRecord record , int sid ){
        Document performance_salesman = performance_collection.find(Filters.eq("salesman", sid)).first();
        if(performance_salesman == null){
            Document salesman_entry = new Document();
            salesman_entry.append("salesman", sid);
            salesman_entry.append("records", Arrays.asList(record.toDocument()));
            performance_collection.insertOne(salesman_entry);
        }else {
            List list = (List) performance_salesman.get("records");
            list.add(record.toDocument());
            performance_collection.updateOne(new Document("salesman", sid), new Document ("$set", new Document( "records", list)));
        }
    }

    public SalesMan readSalesMan(int sid ){
        Document performance_salesman = salesmen_collection.find(Filters.eq("id", sid)).first();
        return toSalesMan(performance_salesman);
    }

    public List<SalesMan> querySalesMan(String attribute , String key ){
        List<SalesMan> salesMEN = new ArrayList<>();
        FindIterable<Document> performance_salesman = salesmen_collection.find(Filters.eq(attribute, key));
        MongoCursor<Document> iterator = performance_salesman.iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            salesMEN.add(toSalesMan(document));
        }
        return salesMEN;
    }

    public EvaluationRecord readEvaluationRecords( int sid ){
        Document performance_salesman = performance_collection.find(Filters.eq("salesman", sid)).first();
        List<Document> list = (List) performance_salesman.get("records");
        return toEvaluationRecord(list.get(list.size()-1));
    }

    public void updateSalesman(Integer id, String attribute, Object value){
        SalesMan user = readSalesMan(id);
        salesmen_collection.updateOne(user.toDocument(), new Document("$set", new Document(attribute, value)));
    }

    public void deleteSalesman(Integer sid){
        Document delete = salesmen_collection.find(Filters.eq("id", sid)).first();
        if(delete != null){
            salesmen_collection.deleteOne(delete);
        }
    }

    public void deletePerformances(Integer sid){
        Document delete = performance_collection.find(Filters.eq("salesman", sid)).first();
        if(delete != null){
            performance_collection.deleteOne(delete);
        }
    }

    private SalesMan toSalesMan(Document document){
        if(document == null){
            return null;
        }else{
        return new SalesMan((String)document.get("firstname"), (String)document.get("lastname"), (Integer)document.get("id"));
        }
    }

    private EvaluationRecord toEvaluationRecord(Document document){
        if(document == null){
            return null;
        }else{
            return new EvaluationRecord((Integer)document.get("evaluation"), (Integer)document.get("value"));
        }
    }
}
