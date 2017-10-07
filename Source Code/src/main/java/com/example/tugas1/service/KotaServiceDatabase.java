package com.example.tugas1.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KotaMapper;
import com.example.tugas1.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {
	@Autowired
	private KotaMapper kotaMapper;
	
    public KotaModel selectKotabyID(int id_kota) {
    	log.info("select kota with id  kota ()", id_kota);
    	return kotaMapper.selectKotabyID(id_kota);
    }
    
    public List<KotaModel> selectKotaList(){
    	return kotaMapper.selectKotaList();
    }
    
}
