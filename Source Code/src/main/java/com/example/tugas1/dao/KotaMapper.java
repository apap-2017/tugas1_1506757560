package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select * from kota where id = #{id_kota}")
    KotaModel selectKotabyID(int id_kota);
	
	@Select("select id, nama_kota from kota")
    List<KotaModel> selectKotaList();
}
