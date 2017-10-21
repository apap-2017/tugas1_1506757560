package com.example.tugas1.service;

import com.example.tugas1.model.AlamatModel;

public interface LokasiService {
    
	AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan);    
	
	AlamatModel getKodeAlamatbyId(int id_kelurahan, int id_kecamatan, int id_kota);
	
	AlamatModel getKodeAlamatbyIdKel(int id_kelurahan);	

}
