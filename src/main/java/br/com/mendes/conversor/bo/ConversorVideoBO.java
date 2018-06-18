package br.com.mendes.conversor.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import br.com.mendes.conversor.client.ZencoderClient;
import br.com.mendes.conversor.repositorio.VideoRepository;

public class ConversorVideoBO {

	private VideoRepository videoRepository = new VideoRepository();
	
	public String ConverterVideo(File video){
		
		String urlFinal = "";

		try {
	        //Armazena o arquivo carregado no aws		
			String urlArmazenamentoVideo = armazenaVideoCarregado(video);
			System.out.println("Url Armazenamento: "+ urlArmazenamentoVideo);			
			
			if(!urlArmazenamentoVideo.equals("") && urlArmazenamentoVideo != null) {
			
				//Converte o arquivo para um formato conhecido
				String urlVideoConvertido = converteVideo(urlArmazenamentoVideo);
				
				urlFinal = urlVideoConvertido;
				System.out.println("Url Video Convertido: "+ urlVideoConvertido);

				if(!urlVideoConvertido.equals("") && urlVideoConvertido != null) {
			        //Armazena o arquivo convertido no aws							
					String tempDir = System.getProperty("java.io.tmpdir"); 
					String caminhoTemporario = tempDir + FilenameUtils.removeExtension(video.getName()) + "_convertido.mp4";

					System.out.println("Caminho temporario: "+ caminhoTemporario);
					
					HttpClient client = HttpClientBuilder.create().build();
					HttpGet request = new HttpGet(urlVideoConvertido);
					HttpResponse response = client.execute(request);
					InputStream is =  response.getEntity().getContent();
					
					File videoConvertido = new File(caminhoTemporario);
					videoConvertido.deleteOnExit();
					
					FileOutputStream fos = new FileOutputStream(videoConvertido);
					
					 int bytes = 0;
					
					while ((bytes = is.read()) != -1) {
			            fos.write(bytes);
			        }
			        is.close();
					fos.close();
					
					urlFinal = armazenaVideoCarregado(videoConvertido);				
					System.out.println("URL Final: "+ urlFinal);
				}
			}
		} catch (IOException e) {
			System.out.println("erro encontrado ao converter o arquivo.");
			e.printStackTrace();
		}							
		return urlFinal;
	}	
	
	private String armazenaVideoCarregado(File video) {
		videoRepository.ArmazenarVideo(video);
		return videoRepository.RecuperarUrlVideo(video.getName());
	}
	
	private String converteVideo(String urlArmazenamentoVideo) {		
		ZencoderClient client = new ZencoderClient();
		return client.converteVideoeDevolveUrl(urlArmazenamentoVideo);
	}
}
