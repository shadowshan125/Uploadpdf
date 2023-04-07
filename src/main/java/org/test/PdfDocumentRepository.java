package org.test;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class PdfDocumentRepository implements PanacheMongoRepository<PdfDocument> {

    public PdfDocument findByName(String name) {
        return find("name", name).firstResult();
    }

    public PdfDocument findById(String documentId) {
        return find("documentId", documentId).firstResult();
    }
}
