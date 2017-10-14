package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * from keluarga where nomor_kk = #{nkk}")
    KeluargaModel selectKeluargabyNKK(String nkk);
	
	@Select("select * from keluarga where id = #{id_keluarga}")
	KeluargaModel selectKeluargabyID(int id_keluarga);
	
	@Insert("insert into keluarga (nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "values ('${nomor_kk}', '${alamat}', '${rt}', '${rw}', '${id_kelurahan}', '0')")
	void addKeluarga(KeluargaModel keluarga);
	
	@Update("update keluarga SET nomor_kk = '${keluarga.nomor_kk}', alamat ='${keluarga.alamat}', RT = '${keluarga.rt}', "
			+ "RW = '${keluarga.rw}', id_kelurahan = '${keluarga.id_kelurahan}', is_tidak_berlaku = '0' "
			+ "WHERE id = #{id}")
	void updateKeluarga(@Param("keluarga") KeluargaModel keluarga, @Param("id") int id);
	
	@Update("update keluarga SET is_tidak_berlaku = '1' WHERE id = #{id}")
	void updateStatusBerlaku(@Param("id") int id);
	
	@Select("select MAX(nomor_kk) from keluarga WHERE nomor_kk LIKE CONCAT(#{digitnkk},'%')")
    String getNKKSebelum(String digitnkk);
}
