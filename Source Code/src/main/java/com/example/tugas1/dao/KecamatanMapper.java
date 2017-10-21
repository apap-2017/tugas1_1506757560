package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	 @Select("select * from kecamatan where id = #{id_kecamatan}")
	 KecamatanModel selectKecamatanbyID(int id_kecamatan);
	 
	 @Select("select id, nama_kecamatan from kecamatan where kecamatan.id_kota = #{id_kota}")
	 List<KecamatanModel> selectKecamatanList(@Param("id_kota") int id_kota);
	 
	 @Select("select id, nama_kecamatan from kecamatan")
	 List<KecamatanModel> selectAllKecamatanList();
}
