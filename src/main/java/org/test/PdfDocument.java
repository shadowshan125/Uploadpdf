package org.test;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

import org.bson.types.Binary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties({ "id" })
@MongoEntity(collection = "pdf_documents", database = "Project")
public class PdfDocument extends PanacheMongoEntity {
    public String documentId;
    public String name;
    public Binary data;
}
