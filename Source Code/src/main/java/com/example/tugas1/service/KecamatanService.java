package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;

public interface KecamatanService {
    
    KecamatanModel selectKecamatanbyID(int id_kecamatan);
    
    List<KecamatanModel> selectKecamatanList(int id_kota);
    
    List<KecamatanModel> selectKecamatanList();
    
}
