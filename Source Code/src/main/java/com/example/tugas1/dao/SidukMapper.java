package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;

@Mapper
public interface SidukMapper{
    
    @Select("select kot.id AS id_kota, kec.id AS id_kecamatan, kel.id, kel.nama_kelurahan, kec.nama_kecamatan, kot.nama_kota "
    		+ "FROM (SELECT id, nama_kota FROM kota) AS kot JOIN "
    		+ "(SELECT id, id_kota, nama_kecamatan FROM kecamatan) AS kec "
    		+ "ON kot.id = kec.id_kota JOIN "
    		+ "(SELECT id, id_kecamatan, nama_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
    		+ "ON kec.id = kel.id_kecamatan")
    AlamatModel getAlamatLengkapbyIdKel(@Param("id_kelurahan")int id_kelurahan);
    
    @Select("select kel.kode_kelurahan, kec.kode_kecamatan, kot.kode_kota "
    		+ "FROM (SELECT id, kode_kota FROM kota) AS kot JOIN "
    		+ "(SELECT id, id_kota, kode_kecamatan FROM kecamatan) AS kec "
    		+ "ON kot.id = kec.id_kota JOIN "
    		+ "(SELECT id_kecamatan, kode_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
    		+ "ON kec.id = kel.id_kecamatan")
    AlamatModel getKodeAlamatbyIdKel(@Param("id_kelurahan")int id_kelurahan);
    
	@Select("select kec.kode_kecamatan AS kode_kecamatan, kel.kode_kelurahan AS kode_kelurahan "
			+ "FROM (SELECT id FROM kota WHERE id = #{id_kota}) AS kot JOIN "
			+ "(SELECT id, id_kota, kode_kecamatan FROM kecamatan WHERE id = #{id_kecamatan}) AS kec "
			+ "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id_kecamatan, kode_kelurahan FROM kelurahan WHERE id = #{id_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	AlamatModel getKodeAlamatbyId(@Param("id_kelurahan")int id_kelurahan, @Param("id_kecamatan")int id_kecamatan, @Param("id_kota")int id_kota);
	
}
