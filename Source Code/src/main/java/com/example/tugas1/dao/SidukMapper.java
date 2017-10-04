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
    
    @Select("select kec.kode_kecamatan AS kode_kecamatan, kot.kode_kota AS kode_kota, nama_kelurahan, nama_kecamatan, nama_kota "
    		+ "FROM kelurahan kel, kecamatan kec, kota kot "
    		+ "WHERE kel.id_kecamatan = kec.id AND kec.id_kota = kot.id AND kel.id = #{id_kelurahan}")
    AlamatModel getAlamatLengkapbyIdKel(int id_kelurahan);

    @Select("select count(*) from penduduk WHERE nik LIKE CONCAT(#{digitnik},'%')")
    int getNomorUrutNIK(String digitnik);
    
	@Select("select kec.kode_kecamatan AS kode_kecamatan, kel.kode_kelurahan AS kode_kelurahan "
			+ "FROM kelurahan kel, kecamatan kec, kota kot "
			+ "WHERE kot.nama_kota = #{nama_kota} AND kot.id = kec.id_kota AND "
			+ "kec.nama_kecamatan = #{nama_kecamatan} AND kec.id = kel.id_kecamatan AND "
			+ "kel.nama_kelurahan = #{nama_kelurahan}")
	AlamatModel getKodeAlamatbyNama(@Param("nama_kelurahan")String nama_kelurahan, @Param("nama_kecamatan")String nama_kecamatan, @Param("nama_kota")String nama_kota);
	
	@Select("select count(*) from keluarga WHERE nomor_kk LIKE CONCAT(#{digitnkk},'%')")
    int getNomorUrutNKK(String digitnkk);
	/*
    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);
    
    @Delete("DELETE FROM student WHERE npm = #{npm}")
    void deleteStudent(String npm);
    
    @Update("UPDATE student SET name = #{name}, gpa = #{gpa} WHERE npm = #{npm}")
    void updateStudent(StudentModel student);
    */
}
