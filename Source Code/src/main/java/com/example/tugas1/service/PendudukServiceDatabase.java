package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	
	public PendudukModel selectPendudukbyNIK(String nik) {
		log.info ("select penduduk with nik {}", nik);
		return pendudukMapper.selectPendudukbyNIK(nik);
	}
	
	public List<PendudukModel> selectAnggotaKeluarga(int id_keluarga){
		log.info ("select penduduk with id keluarga {}", id_keluarga);
		return pendudukMapper.selectAnggotaKeluarga(id_keluarga);
	}
	
	public void addPenduduk(PendudukModel penduduk) {
		log.info ("add penduduk with id keluarga {}", penduduk.getNik());
		pendudukMapper.addPenduduk(penduduk);
	}
}