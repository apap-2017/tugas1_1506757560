package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.SidukMapper;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.AlamatModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SidukServiceDatabase implements SidukService {
	@Autowired
	private SidukMapper sidukMapper;
    
    public AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan){
    	return sidukMapper.getAlamatLengkapbyIdKel(id_kelurahan);
    }
    
    public AlamatModel getKodeAlamatbyId(int id_kelurahan, int id_kecamatan, int id_kota) {
    	return sidukMapper.getKodeAlamatbyId(id_kelurahan, id_kecamatan, id_kota);
    }
    
    public AlamatModel getKodeAlamatbyIdKel(int id_kelurahan) {
    	return sidukMapper.getKodeAlamatbyIdKel(id_kelurahan);
    }
    
}
