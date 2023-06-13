package it.polito.tdp.nyc.model;

import java.util.List;

public class SimResult {

	private List<codiciNta>listaCodiciNTA;
	private List<Integer>listaShare;
	
	public SimResult(List<codiciNta> listaCodiciNTA, List<Integer> listaShare) {
		this.listaCodiciNTA = listaCodiciNTA;
		this.listaShare = listaShare;
	}

	public List<codiciNta> getListaCodiciNTA() {
		return listaCodiciNTA;
	}

	public void setListaCodiciNTA(List<codiciNta> listaCodiciNTA) {
		this.listaCodiciNTA = listaCodiciNTA;
	}

	public List<Integer> getListaShare() {
		return listaShare;
	}

	public void setListaShare(List<Integer> listaShare) {
		this.listaShare = listaShare;
	}
	
	
}
