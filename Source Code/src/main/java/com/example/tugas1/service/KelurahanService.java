package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KelurahanModel;

public interface KelurahanService {
    
    KelurahanModel selectKelurahanbyID(int id_kelurahan);

    int getIdbyKodeKelurahan(String kode_kelurahan);
    
    List<KelurahanModel> selectKelurahanList(int id_kecamatan);
    
    List<KelurahanModel> selectKelurahanList();
    
}
