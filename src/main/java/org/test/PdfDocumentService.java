package org.test;

import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PdfDocumentService {

    private static final Logger LOGGER = Logger.getLogger(PdfDocumentService.class);

    @Inject
    private PdfDocumentRepository pdfDocumentRepository;

    public void savePdfDocument(String name, InputStream inputStream) throws IOException {
        LOGGER.infof("Saving PDF document with name '%s'", name);
        byte[] data = inputStream.readAllBytes();
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocumentRepository.persist(pdfDocument);
    }
}
