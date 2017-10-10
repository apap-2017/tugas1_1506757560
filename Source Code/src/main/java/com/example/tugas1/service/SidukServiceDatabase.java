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
    
    public String getNIKSebelum(String digitnik) {
    	return sidukMapper.getNIKSebelum(digitnik);
    }
    
    public String getNKKSebelum(String digitnkk) {
    	return sidukMapper.getNKKSebelum(digitnkk);
    }
    
    public AlamatModel getKodeAlamatbyNama(String nama_kelurahan, String nama_kecamatan, String nama_kota) {
    	return sidukMapper.getKodeAlamatbyNama(nama_kelurahan, nama_kecamatan, nama_kota);
    }
    
    public AlamatModel getKodeAlamatbyIdKel(int id_kelurahan) {
    	return sidukMapper.getKodeAlamatbyIdKel(id_kelurahan);
    }
    
}
