package org.test;

import org.jboss.logging.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PdfDocumentService {

    private static final Logger LOGGER = Logger.getLogger(PdfDocumentService.class);

    @Inject
    private PdfDocumentRepository pdfRepository;

    // public void savePdfDocument(String name, InputStream inputStream) throws
    // IOException {
    // LOGGER.infof("Saving PDF document with name '%s'", name);
    // byte[] data = inputStream.readAllBytes();
    // PdfDocument pdfDocument = new PdfDocument();
    // pdfDocumentRepository.persist(pdfDocument);
    // }

    // public void uploadPdf(String name, InputStream inputStream) {
    // Binary binaryContent = new Binary(inputStream.readAllBytes());
    // Pdf pdf = new Pdf(name, binaryContent);
    // pdfRepository.persist(pdf);
    // }

    public List<PdfDocument> listAll() {
        return pdfRepository.listAll();
    }

    public PdfDocument findById(String documentId) {
        return pdfRepository.findById(documentId);
    }

    // public byte[] getContent(String documentId) {
    // PdfDocument pdf = findById(documentId);
    // return pdf.data.getData();
    // }

    public InputStream getDataStream(String name) {
        PdfDocument pdf = findByName(name);
        byte[] data = pdf.data.getData();
        return new ByteArrayInputStream(data);
    }

    public PdfDocument findByName(String name) {
        return pdfRepository.findByName(name);
    }
}
