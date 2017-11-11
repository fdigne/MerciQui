package com.merciqui.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.merciqui.entities.Comedien;
import com.merciqui.metier.IMerciQuiMetier;

@Controller
public class GestionComediensController {

	@Autowired
	IMerciQuiMetier merciquimetier ;
	
	@RequestMapping("/comedienIndex")
	public String index() {
		return "ComedienView";
	}
	
	@RequestMapping("/consulterComedien")
	public String consulter(Model model, String id3T) {
		model.addAttribute("id3T", id3T);
		try {
			Comedien com = merciquimetier.consulterComedien(id3T);
			List<Comedien> listeComediens = (List<Comedien>) merciquimetier.listeComediens();
			model.addAttribute("comedien", com);
			model.addAttribute("listeComediens", listeComediens);
			
		} catch (Exception e) {
			
			model.addAttribute("exception",e);
		}
		return "ComedienView";
		
	}
	
	@PostMapping("/saisieComedien")
	public String saisieComedien(Model model, String id3T, String nomComedien, String prenomComedien,
			String dateNaissanceComedien, String numSecuComedien, String sexeComedien, String adressePostaleComedien,
			String adresseEmailComedien, String numTelComedien) {
		try {
		Comedien com = new Comedien();
		com.setAdresseEmail(adresseEmailComedien);
		com.setAdressePostale(adressePostaleComedien);
		com.setId3T(id3T);
		com.setNomPersonne(nomComedien);
		com.setPrenomPersonne(prenomComedien);
		com.setNumSecu(numSecuComedien);
		com.setNumTel(numTelComedien);
		com.setSexe(sexeComedien);
		com.setDateNaissance(mefDateNaissance(dateNaissanceComedien));
		
		merciquimetier.creerComedien(com);
		}
		catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterComedien?id3T="+id3T+"&error="+e.getMessage() ;
		}
		
		return "redirect:/consulterComedien?id3T="+id3T ;
}
	
	private Date mefDateNaissance(String dateNaissance) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date utilDate;
			try {
				utilDate = sdf.parse(dateNaissance);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());	
				return sqlDate;	
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
	}
	
}
