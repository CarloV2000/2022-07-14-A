package it.polito.tdp.nyc.model;

public class codiciNta {
	private String codiceNta;
	private int nSSID;
	
	public codiciNta(String codiceNta) {
		this.codiceNta = codiceNta;
		
	}

	public String getCodiceNta() {
		return codiceNta;
	}

	public void setCodiceNta(String codiceNta) {
		this.codiceNta = codiceNta;
	}

	public int getnSSID() {
		return nSSID;
	}

	public void setnSSID(int nSSID) {
		this.nSSID = nSSID;
	}
	
	
	
}
