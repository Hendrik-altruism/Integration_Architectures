package test;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.code.ManagePersonalImpl;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTripTest {
    private MongoClient client;
    private MongoDatabase supermongo;
    private ManagePersonalImpl control;

    @BeforeEach
    void setUp() {

        client = new MongoClient("localhost", 27017);

        supermongo = client.getDatabase("Salesmen_Performances");

        control = new ManagePersonalImpl(supermongo);
    }

    @Test
    void roundTrip() {
        // CREATE test User
        SalesMan testUser = new SalesMan("Hendrik", "Oude Hengel", 139284);

        // ... now storing the object
        control.createSalesMan(testUser);

        // READ (Finding) the stored Documnent
        SalesMan salesMan = control.readSalesMan(139284);
        System.out.println("Printing the object (JSON): " + salesMan.toDocument() );

        // Assertion
        Integer id = salesMan.getId();
        assertEquals( 139284 , id );

        // UPDATE change User firstname
        control.updateSalesman(139284, "firstname", "Paul");

        // READ (Finding) the stored Documnent
        salesMan = control.readSalesMan(139284);
        System.out.println("Printing the object (JSON): " + salesMan.toDocument() );

        // Assertion
        String firstname = salesMan.getFirstname();
        assertEquals( "Paul" , firstname );

        // DELETE the user
        control.deleteSalesman(139284);
    }
}
