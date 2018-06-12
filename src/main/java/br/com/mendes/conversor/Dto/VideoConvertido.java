package br.com.mendes.conversor.Dto;

import java.util.ArrayList;
import java.util.List;


public class VideoConvertido {

	private int id;
	private List<Output> outputs;
	private boolean test;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Output> getOutputs() {
		if(null == outputs) {
			outputs = new ArrayList<Output>();
		}
		return outputs;
	}
	public void setOutputs(List<Output> outputs) {
		this.outputs = outputs;
	}
	
	public boolean isTest() {
		return test;
	}
	public void setTest(boolean test) {
		this.test = test;
	}
}
