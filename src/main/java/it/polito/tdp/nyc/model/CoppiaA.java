package it.polito.tdp.nyc.model;

public class CoppiaA implements Comparable<CoppiaA>{

	private codiciNta nta1;
	private codiciNta nta2;
	private double peso;
	
	public CoppiaA(codiciNta nta1, codiciNta nta2, double peso) {
		this.nta1 = nta1;
		this.nta2 = nta2;
		this.peso = peso;
	}
	public codiciNta getNta1() {
		return nta1;
	}
	public void setNta1(codiciNta nta1) {
		this.nta1 = nta1;
	}
	public codiciNta getNta2() {
		return nta2;
	}
	public void setNta2(codiciNta nta2) {
		this.nta2 = nta2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  nta1.getCodiceNta() + ", " + nta2.getCodiceNta() + ",(" + peso + ")"+"\n";
	}
	@Override
	public int compareTo(CoppiaA o) {
		return (int) (-this.getPeso()+o.getPeso());
	}
	
}
