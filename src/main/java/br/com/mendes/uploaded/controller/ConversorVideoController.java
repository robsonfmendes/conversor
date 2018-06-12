package br.com.mendes.uploaded.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.com.mendes.conversor.bo.ConversorVideoBO;
import br.com.mendes.conversor.client.ZencoderClient;
import br.com.mendes.uploaded.repository.VideoRepository;

@Path("/upload")
public class ConversorVideoController {

	@POST
	@Path("/file")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public String uploadFile(
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
		String urlVideoConvertido = "";
		try {
			File file = new File(fileMetaData.getFileName());
			
			ConversorVideoBO conversor = new ConversorVideoBO();
			urlVideoConvertido = conversor.ConverterVideo(file);
			
			System.out.println(urlVideoConvertido);
			
		} catch(Exception ex) {
			System.out.println("erro"+ex.getMessage());
		}
		return urlVideoConvertido;
	}
	
}
