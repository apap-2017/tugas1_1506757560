package com.example.tugas1.service;

import java.util.List;


import com.example.tugas1.model.PendudukModel;

public interface PendudukService {
	
	PendudukModel selectPendudukbyNIK(String nik);

	PendudukModel addPenduduk(PendudukModel penduduk);
	
	boolean updatePenduduk(PendudukModel penduduk);
	
	void updateStatusKematian(PendudukModel penduduk);
	
	List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);
	
	PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan);
}
