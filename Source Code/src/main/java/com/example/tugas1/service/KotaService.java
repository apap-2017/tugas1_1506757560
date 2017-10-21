package com.example.tugas1.service;

import java.util.List;

import com.example.tugas1.model.KotaModel;

public interface KotaService {
  
    KotaModel selectKotabyID(int id_kota);
    
    List<KotaModel> selectKotaList();
}
