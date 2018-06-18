package br.com.mendes.conversor.repositorio;

import java.io.File;
import java.io.IOException;

import br.com.mendes.conversor.servico.ServicoAws;

public class VideoRepository {

	ServicoAws armazenadorDeArquivos = new ServicoAws();
	
	public void ArmazenarVideo(File arquivo) {
		try {
			armazenadorDeArquivos.armazenaVideoNoAws(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String RecuperarUrlVideo(String nomeArquivo) {
		try {
			return armazenadorDeArquivos.recuperaUrlVideoArmazenado(nomeArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
