package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private Graph<codiciNta, DefaultWeightedEdge> grafo;
	private List<codiciNta>codiciNTA;
	private Map<String, codiciNta>idMap;
	private NYCDao dao;
	private List<String>allBorghi;
	
	private Integer numVerticiBilancioMaggioreDiA1ToccatiMAX;
	private List<codiciNta> migliore;
	
	public Model() {
		this.codiciNTA = new ArrayList<>();
		this.idMap = new HashMap<>();
		this.dao = new NYCDao();
		this.allBorghi = new ArrayList<>(dao.getAllBoroughs());
	}
	
	public String creaGrafo(String borgo) {
		//grafo
		this.grafo = new SimpleWeightedGraph<codiciNta, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//vertici
		this.codiciNTA = dao.getAllCodiciNTA(borgo);
		Graphs.addAllVertices(grafo, this.codiciNTA);
		
		// idMap
		for(codiciNta x : codiciNTA) {
			this.idMap.put(x.getCodiceNta(), x);
		}
		//archi1
		for(codiciNta x : this.codiciNTA) {
			for(codiciNta y : this.codiciNTA) {
				int peso = dao.getNumeroSSID(x, y, borgo);
				if(!x.equals(y) && peso != 0) {
					grafo.addEdge(x, y);
					grafo.setEdgeWeight(x, y, peso);
				}
			}	
		}
		return ("Grafo creato con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi");
		
	}
	
	/**
	 * Alla pressione del bottone “Analisi Archi”, popolare la TableView presente nell’interfaccia grafica con gli archi il cui peso
	 *  sia maggiore del peso medio di tutti gli archi presenti nelgrafo. Gli archi vanno stampati in ordine decrescente di peso. 
	 *  Per ogni arco, si stampino i due codici NTA ed il relativo peso
	 */
	
	public List<DefaultWeightedEdge> analisiArchi() {
		List<DefaultWeightedEdge>result = new ArrayList<DefaultWeightedEdge>();
		Set<DefaultWeightedEdge>allArchi = new HashSet<>(grafo.edgeSet());
		double media = this.analisiCalcoloMedia();
		for(DefaultWeightedEdge x : allArchi) {
			if(grafo.getEdgeWeight(x) > media) {
				result.add(x);
			}
		}
		return result;
		
	}
	
	public double analisiCalcoloMedia() {
		Set<DefaultWeightedEdge>allArchi = new HashSet<>(grafo.edgeSet());
		double somma = 0;
		int n = 0;
		double media = 0.0;
		for(DefaultWeightedEdge x : allArchi) {
			somma += grafo.getEdgeWeight(x);
			n++;
		}
		media = somma/n;
		return media;
		
	}
	


	public Graph<codiciNta, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(Graph<codiciNta, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public List<codiciNta> getCodiciNTA() {
		return codiciNTA;
	}

	public void setCodiciNTA(List<codiciNta> codiciNTA) {
		this.codiciNTA = codiciNTA;
	}

	public NYCDao getDao() {
		return dao;
	}

	public void setDao(NYCDao dao) {
		this.dao = dao;
	}

	public List<String> getAllBorghi() {
		return allBorghi;
	}

	public void setAllBorghi(List<String> allBorghi) {
		this.allBorghi = allBorghi;
	}
	
	
	public List<CoppiaA> getInfoArchi() {
		List<CoppiaA>archi = new ArrayList<>();
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			codiciNta v1 = this.grafo.getEdgeSource(e);
			codiciNta v2 = this.grafo.getEdgeTarget(e);
			double peso = grafo.getEdgeWeight(e);
			CoppiaA edge = new CoppiaA(v1, v2, peso);
			archi.add(edge);
		}
		Collections.sort(archi);
		return archi;
	}
	
	public SimResult simula(double probShare, int durationShare) {
		Simulatore sim = new Simulatore(grafo, probShare, durationShare);
		sim.initialize();
		sim.run();
		SimResult res = sim.getRisultato();
		return res;
	}
	
	
}
