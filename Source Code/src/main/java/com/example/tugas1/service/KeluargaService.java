package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KeluargaModel;

public interface KeluargaService {

	KeluargaModel selectKeluargabyNKK(String nkk);	
	
	KeluargaModel selectKeluargabyID(int id_keluarga);
	
	void addKeluarga(KeluargaModel keluarga);
	
	void updateKeluarga(KeluargaModel keluarga, int id);
	
	void updateStatusBerlaku(int id);
}
