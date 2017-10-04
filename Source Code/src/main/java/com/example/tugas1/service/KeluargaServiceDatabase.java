package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;

	public KeluargaModel selectKeluargabyNKK(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluargabyNKK(nkk);
	}
	
	public KeluargaModel selectKeluargabyID(int id_keluarga) {
		log.info("select keluarga with id  keluarga ()", id_keluarga);
		return keluargaMapper.selectKeluargabyID(id_keluarga);
	}
	
	public 	void addKeluarga(KeluargaModel keluarga) {
		keluargaMapper.addKeluarga(keluarga);
	}
}
