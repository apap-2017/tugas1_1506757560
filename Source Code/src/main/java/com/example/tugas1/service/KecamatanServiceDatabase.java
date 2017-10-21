package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KecamatanMapper;
import com.example.tugas1.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	@Autowired
	private KecamatanMapper kecamatanMapper;

    public KecamatanModel selectKecamatanbyID(int id_kecamatan) {
    	log.info("select kecamatan with id  kecamatan ()", id_kecamatan);
    	return kecamatanMapper.selectKecamatanbyID(id_kecamatan);
    }
    
    public List<KecamatanModel> selectKecamatanList(int id_kota){
    	return kecamatanMapper.selectKecamatanList(id_kota);
    }
    
    public List<KecamatanModel> selectKecamatanList(){
    	return kecamatanMapper.selectAllKecamatanList();
    }
}
