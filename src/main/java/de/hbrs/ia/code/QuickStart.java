package de.hbrs.ia.code;

import static com.mongodb.client.model.Filters.eq;

import de.hbrs.ia.model.EvaluationRecord;
import de.hbrs.ia.model.SalesMan;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.Scanner;


public class QuickStart {

    enum operations {
        createSalesMan,
        MEDIUM,
        HIGH
    }

    public static void main( String[] args ) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("Salesmen_Performances");
        ManagePersonalImpl control = new ManagePersonalImpl(database);
        Scanner scan = new Scanner(System.in);
        String sentence;
        Integer userid = 1;
        Integer performanceid = 1;
        String firstname;
        String lastname;
        Integer sid;
        Integer performance_value;

        do {

            System.out.println("Datenbank-operationen: \n \n" +
                    "createSalesMan \n" +
                    "addPerformanceRecord \n" +
                    "readSalesMan \n" +
                    "querySalesMan \n" +
                    "readEvaluationRecords \n" +
                    "updateSalesMan \n" +
                    "deleteSalesMan \n" +
                    "deletePerformances \n \n" +
                    "end -> für Beendung des Programms \n"+
                    "Bitte geben sie eine gewünschte Operation an"
            );

            sentence = scan.nextLine();

            switch (sentence) {

                default:
                    System.out.println("falsche Eingabe");

                case "createSalesMan":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Vorname: \n");
                    firstname = scan.nextLine();
                    System.out.println("Nachname: \n");
                    lastname = scan.nextLine();
                    control.createSalesMan(new SalesMan(firstname, lastname, userid++));
                    System.out.println("Nutzer erstellt");

                case "addPerformanceRecord":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Performance Value(Int): \n");
                    performance_value = scan.nextInt();
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    control.addPerformanceRecord(new EvaluationRecord(performanceid++, performance_value), sid);
                    System.out.println("Performance Record hinzugefügt");

                case "readSalesMan":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    System.out.println(control.readSalesMan(sid).toDocument());

                case "querySalesMan":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Attribut: \n");
                    firstname = scan.nextLine();
                    System.out.println("Parameter: \n");
                    lastname = scan.nextLine();
                    List<SalesMan> sales = control.querySalesMan(firstname, lastname);
                    sales.forEach(salesMan -> System.out.println(salesMan.toDocument()+"\n"));

                case "readEvaluationRecords":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    System.out.println(control.readEvaluationRecords(sid).toDocument());

                case "updateSalesMan":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    System.out.println("Attribut: \n");
                    firstname = scan.nextLine();
                    System.out.println("Key: \n");
                    lastname = scan.nextLine();
                    control.updateSalesman(sid, firstname, lastname);
                    System.out.println("Salesman updated");

                case "deleteSalesMan":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    control.deleteSalesman(sid);
                    System.out.println("Salesman gelöscht");

                case "deletePerformances":
                    System.out.println("geben sie nach einander die Parameter ein\n");;
                    System.out.println("Salesman ID: \n");
                    sid = scan.nextInt();
                    control.deletePerformances(sid);
                    System.out.println("Performances von Salesman: "+ sid +" gelöscht");
            }
        } while (!sentence.equals("end"));
        System.out.println("Vielen Dank für die Nutzung des Systems");
    }
}