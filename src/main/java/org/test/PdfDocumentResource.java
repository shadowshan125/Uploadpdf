package org.test;

import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import org.bson.types.Binary;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Path("/pdf")
public class PdfDocumentResource {

    @Inject
    PdfDocumentRepository pdfDocumentRepository;

    @Inject
    PdfDocumentService pdfService;

    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response uploadPdfDocument(InputStream inputStream, @QueryParam("name") String name)
            throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.name = name;
        pdfDocument.data = new Binary(inputStream.readAllBytes());
        pdfDocumentRepository.persist(pdfDocument);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PdfDocument> listAll() {
        return pdfService.listAll();
    }

    // @GET
    // @Path("/{id}")
    // @Produces(MediaType.APPLICATION_OCTET_STREAM)
    // public byte[] getById(@PathParam("id") String id) {
    // return pdfService.getContent(id);
    // }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getByName(@PathParam("name") String name) {
        PdfDocument pdf = pdfService.findByName(name);
        return pdf.data.getData();
    }

    // @GET
    // @Path("/download/{name}")
    // @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    // @Produces(MediaType.APPLICATION_OCTET_STREAM)
    // public Response downloadPdfbinary(@PathParam("name") String name) {
    // PdfDocument pdf = pdfService.findByName(name);
    // InputStream inputStream = pdfService.getDataStream(name);
    // ResponseBuilder response = Response.ok(inputStream);
    // response.header("Content-Disposition", "attachment; name=" + pdf.name);
    // return response.build();
    // }

    @GET
    @Path("/download/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadPdf(@PathParam("name") String name) {
        PdfDocument pdf = pdfService.findByName(name);

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException,
                    WebApplicationException {
                InputStream inputStream = pdfService.getDataStream(name);

                try {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.flush();
                } catch (IOException e) {
                    throw new RuntimeException("Error streaming file.", e);
                } finally {
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException("Error closing streams.", e);
                    }
                }
            }
        };

        Response.ResponseBuilder response = Response.ok(stream);
        response.header("Content-Disposition", "attachment; filename=\"" + pdf.name +
                ".pdf\"");
        response.type("application/pdf");

        return response.build();
    }

}
