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
	
	@Select("select kelurahan.id, nama_kelurahan from kelurahan JOIN "
	 		+ "(select id from kecamatan where nama_kecamatan = #{nama_kecamatan}) AS kecamatan "
	 		+ "ON kecamatan.id = kelurahan.id_kecamatan")
	List<KelurahanModel> selectKelurahanList(@Param("nama_kecamatan")String nama_kecamatan);
	
	@Select("select kel.id "
			+ "FROM (SELECT id FROM kota WHERE nama_kota = #{nama_kota}) AS kot JOIN "
			+ "(SELECT id, id_kota FROM kecamatan WHERE nama_kecamatan = #{nama_kecamatan}) AS kec "
			+ "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id, id_kecamatan FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	Integer getIdKelurahanByAlamat(@Param("nama_kelurahan")String nama_kelurahan, @Param("nama_kecamatan")String nama_kecamatan, @Param("nama_kota")String nama_kota);
	
}
