package com.example.tugas1.service;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;

public interface KeluargaService {

	KeluargaModel selectKeluargabyNKK(String nkk);	
	
	KeluargaModel selectKeluargabyID(int id_keluarga);
	
	String addKeluarga(KeluargaModel keluarga);
	
	void updateKeluarga(KeluargaModel keluarga, AlamatModel kode_alamat);
	
	void updateStatusBerlaku(int id);
}
