package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

public interface KeluargaService {

	KeluargaModel selectKeluargabyNKK(String nkk);	
	
	KeluargaModel selectKeluargabyID(int id_keluarga);
	
	List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);
	
	String addKeluarga(KeluargaModel keluarga);
	
	boolean updateKeluarga(KeluargaModel keluarga, AlamatModel kode_alamat);
	
	void updateStatusBerlaku(int id);
}
