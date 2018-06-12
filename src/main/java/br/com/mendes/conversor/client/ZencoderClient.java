package br.com.mendes.conversor.client;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.mendes.conversor.Dto.VideoConvertido;

public class ZencoderClient {

	private static int HTTP_COD_CREATE = 201;
	private static String URL_SERVICO_CONVERSOR = "https://app.zencoder.com/api/v2/jobs?input=";
	private static String API_KEY = "Zencoder-Api-Key";
	private static String API_PASS = "5573695e9edc48b532bbd42ff5e0be62";
	
	public String converteVideoeDevolveUrl(String urlArmazenamentoVideo) {
		    
	    Client client = Client.create();
	    
		WebResource webResource = client.resource(URL_SERVICO_CONVERSOR+urlArmazenamentoVideo);

		ClientResponse response = webResource.header(API_KEY , API_PASS)
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class);
	
	    if (response.getStatus() != HTTP_COD_CREATE) {
	    	throw new RuntimeException("Failed : HTTP error code : "
	    			+ response.getStatus());
	    }		
		
		VideoConvertido videoConvertido = new Gson().fromJson(response.getEntity(String.class), VideoConvertido.class); 
		return videoConvertido.getOutputs().get(0).getUrl();
	}	
}
