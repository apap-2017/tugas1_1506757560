package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper{
    @Select("select * from penduduk where nik = #{nik}")
    PendudukModel selectPendudukbyNIK (String nik);
    
    @Select("select * from penduduk where id = #{id}")
    PendudukModel selectPendudukbyId (int id);
	
    @Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, "
    		+ "agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
    		+ "VALUES ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin}, "
    		+ "${is_wni}, '${id_keluarga}', '${agama}', "
    		+ "'${pekerjaan}', '${status_perkawinan}', '${status_dalam_keluarga}', '${golongan_darah}', '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
    
    
    @Update("UPDATE penduduk SET nik = '${penduduk.nik}', nama = '${penduduk.nama}', tempat_lahir = '${penduduk.tempat_lahir}', "
    		+ "tanggal_lahir = '${penduduk.tanggal_lahir}', jenis_kelamin = '${penduduk.jenis_kelamin}', "
    		+ "is_wni = '${penduduk.is_wni}', id_keluarga = '${penduduk.id_keluarga}', agama = '${penduduk.agama}', "
    		+ "pekerjaan = '${penduduk.pekerjaan}', status_perkawinan = '${penduduk.status_perkawinan}', "
    		+ "status_dalam_keluarga = '${penduduk.status_dalam_keluarga}', "
    		+ "golongan_darah = '${penduduk.golongan_darah}' "
    		+ "WHERE id = #{id}")
    void updatePenduduk(@Param("penduduk")PendudukModel penduduk, @Param("id")int id);
    
    @Update("UPDATE penduduk SET  is_wafat = '1' WHERE nik = #{nik}")
    void updateStatusKematian(@Param("nik") String nik);
    
    @Select("select nik, nama, jenis_kelamin from "
    		+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
    		+ "JOIN penduduk "
    		+ "ON keluarga.id = penduduk.id_keluarga")
    List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan);
    
    @Select("select nik, nama, tanggal_lahir from penduduk JOIN "
    		+ "(select id from keluarga where id_kelurahan = #{id_kelurahan}) AS keluarga "
    		+ "ON keluarga.id = penduduk.id_keluarga "
    		+ "ORDER BY tanggal_lahir DESC "
    		+ "LIMIT 1")
    PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan);
    
    @Select("select MAX(nik) from penduduk WHERE nik LIKE CONCAT(#{digitnik},'%')")
    String getNIKSebelum(String digitnik);
}
