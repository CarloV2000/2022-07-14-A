package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import it.polito.tdp.nyc.model.CoppiaA;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.codiciNta;

public class NYCDao {
	
	
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getAllBoroughs(){
		String sql = "SELECT * "
				+ "FROM nyc_wifi_hotspot_locations l1 "
				+ "GROUP BY borough ";

		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Borough"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
//metodo per prendere dal db i vertici
	public List<codiciNta> getAllCodiciNTA(String borgo) {
		String sql = "SELECT distinct h.NTACode "
				+ "FROM nyc_wifi_hotspot_locations h "
				+ "WHERE h.Borough = ? ";
		List<codiciNta> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borgo);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				String s = res.getString("NTACode");
				
				codiciNta c = new codiciNta(s);
				result.add(c);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	//metodo per dare pesi agli archi
	public Integer getNumeroSSID(codiciNta nta1, codiciNta nta2, String borgo) {
		String sql = "SELECT SUM(a+b) AS peso "
				+ "FROM( "
				+ "SELECT COUNT(distinct l1.SSID)AS a, COUNT(distinct l2.SSID) AS b "
				+ "FROM  nyc_wifi_hotspot_locations l1, nyc_wifi_hotspot_locations l2 "
				+ "WHERE l1.NTACode = ? AND l2.NTACode = ? "
				+ "AND l1.Borough = ? AND l2.Borough = ? "
				+ ") AS t ";
		Integer n = 0;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nta1.getCodiceNta());
			st.setString(2, nta2.getCodiceNta());
			st.setString(3, borgo);
			st.setString(4, borgo);
			ResultSet res = st.executeQuery();

			while(res.next()) {
				n = res.getInt("peso");
		    }
			
			conn.close();
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

	}


	/*public List<CoppiaA> getAllCoppie(Map<String, codiciNta>idMap) {
		final String sql = "SELECT distinct id_stazP, id_stazA "
				+ "FROM connessione ";

		List<CoppiaA>allCoppie = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				CoppiaA coppia = new CoppiaA(idMap.get(rs.getInt("id_stazP")), idMap.get(rs.getInt("id_stazA")));
				allCoppie.add(coppia);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}
		return allCoppie;
	}*/
}

