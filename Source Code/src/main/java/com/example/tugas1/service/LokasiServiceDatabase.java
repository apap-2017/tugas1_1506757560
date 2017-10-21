package com.example.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.LokasiMapper;
import com.example.tugas1.model.AlamatModel;

@Service
public class LokasiServiceDatabase implements LokasiService {
	@Autowired
	private LokasiMapper sidukMapper;
    
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
