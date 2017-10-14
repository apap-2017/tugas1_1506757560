package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KelurahanMapper;
import com.example.tugas1.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	public KelurahanModel selectKelurahanbyID(int id_kelurahan) {
		log.info("select kelurahan with id  kelurahan ()", id_kelurahan);
		return kelurahanMapper.selectKelurahanbyID(id_kelurahan);
	}
 
	public int getIdbyKodeKelurahan(String kode_kelurahan) {
		return kelurahanMapper.getIdbyKodeKelurahan(kode_kelurahan);
	}
	
	public List<KelurahanModel> selectKelurahanList(int id_kecamatan){
		return kelurahanMapper.selectKelurahanList(id_kecamatan);
	}
	
	public List<KelurahanModel> selectKelurahanList(){
		return kelurahanMapper.selectAllKelurahanList();
	}
}
