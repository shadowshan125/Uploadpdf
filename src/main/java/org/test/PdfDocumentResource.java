package org.test;

import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import org.bson.types.Binary;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/pdf")
public class PdfDocumentResource {

    @Inject
    PdfDocumentRepository pdfDocumentRepository;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadPdfDocument(InputStream inputStream, @QueryParam("filename") String filename)
            throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.filename = filename;
        pdfDocument.data = new Binary(inputStream.readAllBytes());
        pdfDocumentRepository.persist(pdfDocument);
        return Response.status(Response.Status.CREATED).build();
    }

    // @GET
    // @Produces(MediaType.APPLICATION_OCTET_STREAM)
    // @Path("/{id}")
    // public Response downloadPdfDocument(@PathParam("documentId") String
    // documentId) {
    // PdfDocument pdfDocument = pdfDocumentRepository.findById(documentId);
    // if (pdfDocument == null) {
    // throw new NotFoundException();
    // }
    // StreamingOutput streamingOutput = output ->
    // output.write(pdfDocument.data.getData());
    // return Response.ok(streamingOutput).header("Content-Disposition",
    // "attachment; filename=\"" + pdfDocument.filename + "\"").build();
    // }

    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public List<PdfDocument> listPdfDocuments() {
    // PanacheQuery<PdfDocument> pdfDocuments =
    // pdfDocumentRepository.findAll(Sort.by("filename"));
    // return pdfDocuments.list();
    // }

}
