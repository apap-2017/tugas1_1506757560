package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;

@Mapper
public interface KecamatanMapper {
	 @Select("select * from kecamatan where id = #{id_kecamatan}")
	 KecamatanModel selectKecamatanbyID(int id_kecamatan);
	 
	 @Select("select nama_kecamatan from kecamatan JOIN "
	 		+ "(select id from kota where nama_kota = #{nama_kota}) AS kota "
	 		+ "ON kota.id = kecamatan.id_kota")
	 List<KecamatanModel> selectKecamatanList(@Param("nama_kota")String nama_kota);
}
