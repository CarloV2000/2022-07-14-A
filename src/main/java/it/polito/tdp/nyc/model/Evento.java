package it.polito.tdp.nyc.model;

public class Evento implements Comparable<Evento>{

	public enum EventType{//ci sono due possibili eventi da gestire
		SHARE, STOP
	}
	private EventType type;
	private int time;
	private codiciNta nta;
	private int duration;
	public Evento(EventType type, int time, codiciNta nta, int duration) {
		super();
		this.type = type;
		this.time = time;
		this.nta = nta;
		this.duration = duration;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public codiciNta getNta() {
		return nta;
	}
	public void setNta(codiciNta nta) {
		this.nta = nta;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	
	
}
