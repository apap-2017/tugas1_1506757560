package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;

public interface KelurahanService {
    
    KelurahanModel selectKelurahanbyID(int id_kelurahan);

    int getIdbyKodeKelurahan(String kode_kelurahan);
    
    List<KelurahanModel> selectKelurahanList(String nama_kecamatan);
}
