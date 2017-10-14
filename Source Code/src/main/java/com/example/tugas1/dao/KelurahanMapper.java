package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select * from kelurahan where id = #{id_kelurahan}")
    KelurahanModel selectKelurahanbyID(int id_kelurahan);
	
	@Select("select id from kelurahan where kode_kelurahan = #{kode_kelurahan}")
	int getIdbyKodeKelurahan(String kode_kelurahan);
	
	@Select("select id, nama_kelurahan from kelurahan where kelurahan.id_kecamatan = #{id_kecamatan}")
	List<KelurahanModel> selectKelurahanList(@Param("id_kecamatan")int id_kecamatan);
	
	@Select("select id, nama_kelurahan from kelurahan")
	List<KelurahanModel> selectAllKelurahanList();
}
