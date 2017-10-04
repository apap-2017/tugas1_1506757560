package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select * from kelurahan where id = #{id_kelurahan}")
	
    KelurahanModel selectKelurahanbyID(int id_kelurahan);
	
	@Select("select id from kelurahan where kode_kelurahan = #{kode_kelurahan}")
	int getIdbyKodeKelurahan(String kode_kelurahan);
}
