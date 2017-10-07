package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select * from kota where id = #{id_kota}")
    KotaModel selectKotabyID(int id_kota);
	
	@Select("select * from kota")
    List<KotaModel> selectKotaList();
}
