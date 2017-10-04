package com.example.tugas1.service;

import java.util.List;


import com.example.tugas1.model.PendudukModel;

public interface PendudukService {
	
	PendudukModel selectPendudukbyNIK(String nik);

	List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);

	void addPenduduk(PendudukModel penduduk);
}
