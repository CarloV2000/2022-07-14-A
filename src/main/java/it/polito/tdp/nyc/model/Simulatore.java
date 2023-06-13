package it.polito.tdp.nyc.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.Evento.EventType;

public class Simulatore {
	//STATO DEL SISTEMA-MONDO
	private Graph<codiciNta, DefaultWeightedEdge> grafo;
	private Map<codiciNta, Integer>numShare;//e una mappa che associa ad ogni NTA il suo numero di condivisioni(evento SHARE)
	private List<codiciNta>vertici;
	
	//PARAMETRI DI INGRESSO - TUNING
		private double probShare;
		private int durataShare;
		
	//INDICATORI DI USCITA
		private Map<codiciNta, Integer>numTotShare;
		
	//CODA DEGLI EVENTI
		private PriorityQueue<Evento>queue;

		
		
		//1°metodo : costruttore del simulatore
		public Simulatore(Graph<codiciNta, DefaultWeightedEdge> grafo, double probShare, int durataShare) {
			super();
			this.grafo = grafo;
			this.probShare = probShare;
			this.durataShare = durataShare;
		}
		
		//2°metodo: inizializzatore (eventi che "arrivano dall'esterno")
		public void initialize() {
			this.numShare = new HashMap<codiciNta, Integer>();
			this.numTotShare = new HashMap<codiciNta, Integer>();
			for(codiciNta n : this.grafo.vertexSet()) {//riempio di zeri le mappe(in quanto inizialmente nessun NTA condivide nessun file)
				this.numShare.put(n, 0);
				this.numTotShare.put(n, 0);
			}
			
			this.vertici = new ArrayList<>(this.grafo.vertexSet());
			
			this.queue = new PriorityQueue<Evento>();
			//creo eventi iniziali(ossia la situazione iniziale della simulazione)
			for(int t = 0; t < 100; t++) {
				if(Math.random() <= this.probShare) {
					//se il numero casuale da 0 a 1 è <= alla probabilita inserita: condivido un file su un nta casuale
					int numNTA = (int)(Math.random()*vertici.size());//prendo un numero casuale da 0 al numero dei vertici
					codiciNta ntaParteza = this.vertici.get(numNTA);
					this.queue.add(new Evento(EventType.SHARE, t, ntaParteza, this.durataShare));
				}
			}
		}	

		//3°metodo: il run
		public void run() {
			//dopo aver identificato quando la mia simulazione si può fermare: 1 e 2
			while(!this.queue.isEmpty() ) {//1-se la coda non è vuota: proseguo con la simulazione
				Evento e = this.queue.poll();
				if(e.getTime() >= 100) {//2-se l'evento avviene dopo il giorno 100: termino la simulazione
					break;
				}
				//altrimenti proseguo estraendo dall'evento della coda tutti i suoi campi(time, duration e nta)
				int time = e.getTime();
				int duration = e.getDuration();
				codiciNta nta = e.getNta();
				//a questo punto faccio lo switch sul tipo di evento(in cui dico che per ogni evento e succede qualcosa)
				
				System.out.println("Evento di "+e.getType()+" al tempo " +e.getTime()+" sull'NTACode con codice "+ e.getNta().getCodiceNta());
				switch(e.getType()) {
				
				case SHARE://in questo caso succede che condivido un nuovo ntaCode(aggiorno lo stato del sistema per ricordare che quell'nta sta condividendo un nuovo file)
						   //e genero: 1- un evento di STOP dopo una durata d e 2- una ricondivisione al tempo t+1(ore 00:46:23 del 05/06/2023)
					this.numShare.put(nta, this.numShare.get(nta)+1);
					this.queue.add(new Evento(EventType.STOP, time+duration,nta, 0));//metto 0 alla duration perche se c'è uno stop non serve una duration
					
					break;
					
				case STOP://in questo caso succede che non condivido più l'ntaCode(aggiorno lo stato del sistema per ricordare che quell'nta non sta piu condividendo il file)
					this.numShare.put(nta, this.numShare.get(nta)-1);
					break;
				}
				
				
			}
		}
		
		public SimResult getRisultato() {
			List<codiciNta>codici = new ArrayList<>();
			List<Integer>numShare = new ArrayList<>();
			for(codiciNta x : this.numTotShare.keySet()) {
				codici.add(x);
			}
			for(Integer y : this.numShare.values()) {
				numShare.add(y);
			}
			SimResult sim = new SimResult(codici, numShare);
			return sim;
		}
		
		
}
