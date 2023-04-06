package org.test;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class PdfDocumentRepository implements PanacheMongoRepository<PdfDocument> {

    public String findById(String documentId) {
        return documentId;
    }
}
