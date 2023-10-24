package de.hbrs.ia.model;

import org.bson.Document;

public class EvaluationRecord {

    private Integer evaluation_id;

    private Integer evaluation_value;

    public EvaluationRecord(Integer evaluation_id, Integer evaluation_value) {
        this.evaluation_id = evaluation_id;
        this.evaluation_value = evaluation_value;
    }

    public Integer getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(Integer evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public Integer getEvaluation_value() {
        return evaluation_value;
    }

    public void setEvaluation_value(Integer evaluation_value) {
        this.evaluation_value = evaluation_value;
    }

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("evaluation" , this.evaluation_id );
        document.append("value" , this.evaluation_value);
        return document;
    }
}
