package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

public interface SidukService {
    
	AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan);
    
	
	AlamatModel getKodeAlamatbyId(int id_kelurahan, int id_kecamatan, int id_kota);
	
	AlamatModel getKodeAlamatbyIdKel(int id_kelurahan);	

}
