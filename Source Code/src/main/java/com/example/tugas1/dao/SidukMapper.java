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
    
    @Select("select kel.nama_kelurahan, kec.nama_kecamatan, kot.nama_kota "
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
    
    @Select("select MAX(nik) from penduduk WHERE nik LIKE CONCAT(#{digitnik},'%')")
    String getNIKSebelum(String digitnik);
    
	@Select("select kec.kode_kecamatan AS kode_kecamatan, kel.kode_kelurahan AS kode_kelurahan "
			+ "FROM (SELECT id FROM kota WHERE nama_kota = #{nama_kota}) AS kot JOIN "
			+ "(SELECT id, id_kota, kode_kecamatan FROM kecamatan WHERE nama_kecamatan = #{nama_kecamatan}) AS kec "
			+ "ON kot.id = kec.id_kota JOIN "
			+ "(SELECT id_kecamatan, kode_kelurahan FROM kelurahan WHERE nama_kelurahan = #{nama_kelurahan}) AS kel "
			+ "ON kec.id = kel.id_kecamatan")
	AlamatModel getKodeAlamatbyNama(@Param("nama_kelurahan")String nama_kelurahan, @Param("nama_kecamatan")String nama_kecamatan, @Param("nama_kota")String nama_kota);
	
	@Select("select MAX(nomor_kk) from keluarga WHERE nomor_kk LIKE CONCAT(#{digitnkk},'%')")
    String getNKKSebelum(String digitnkk);
}
