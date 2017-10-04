package com.example.tugas1.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;

public interface KecamatanService {
    
    KecamatanModel selectKecamatanbyID(int id_kecamatan);
    
}
