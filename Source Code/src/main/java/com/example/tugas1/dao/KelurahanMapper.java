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
	
	@Select("select * from kelurahan JOIN "
	 		+ "(select id from kecamatan where nama_kecamatan = #{nama_kecamatan}) AS kecamatan "
	 		+ "ON kecamatan.id = kelurahan.id_kecamatan")
	List<KelurahanModel> selectKelurahanList(@Param("nama_kecamatan")String nama_kecamatan);
}
