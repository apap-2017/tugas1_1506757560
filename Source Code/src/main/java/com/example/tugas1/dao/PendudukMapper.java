package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
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
    
    @Select("select * from penduduk where id_keluarga = #{id_keluarga}")
    List<PendudukModel> selectAnggotaKeluarga(int id_keluarga);
	
    @Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat)"
    		+ "VALUES ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', ${jenis_kelamin}, ${is_wni}, '${id_keluarga}', '${agama}', "
    		+ "'${pekerjaan}', '${status_perkawinan}', '${status_dalam_keluarga}', '${golongan_darah}', '${is_wafat}')")
    void addPenduduk (PendudukModel penduduk);
    
    /*
    @Delete("DELETE FROM student WHERE npm = #{npm}")
    void deleteStudent(String npm);
    
    @Update("UPDATE student SET name = #{name}, gpa = #{gpa} WHERE npm = #{npm}")
    void updateStudent(StudentModel student);
    */
}
