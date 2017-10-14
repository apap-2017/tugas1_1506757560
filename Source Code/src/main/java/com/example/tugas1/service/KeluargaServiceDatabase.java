package com.example.tugas1.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.dao.KelurahanMapper;
import com.example.tugas1.dao.SidukMapper;
import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	@Autowired
	private KelurahanMapper kelurahanMapper;
	@Autowired
	private SidukMapper lokasiMapper;

	public KeluargaModel selectKeluargabyNKK(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluargabyNKK(nkk);
	}
	
	public KeluargaModel selectKeluargabyID(int id_keluarga) {
		log.info("select keluarga with id  keluarga ()", id_keluarga);
		return keluargaMapper.selectKeluargabyID(id_keluarga);
	}
	
	public String addKeluarga(KeluargaModel keluarga) {
		int id_kelurahan = keluarga.getId_kelurahan();
		int id_kecamatan = keluarga.getId_kecamatan();
		int id_kota = keluarga.getId_kota();
		AlamatModel kode_alamat = lokasiMapper.getKodeAlamatbyId(id_kelurahan, id_kecamatan, id_kota);
		if (kode_alamat == null ) {
			return "not ok";
		} else {
			String kode_kecamatan = kode_alamat.getKode_kecamatan();
			String kode_kelurahan = kode_alamat.getKode_kelurahan();
			
			LocalDate localDate = LocalDate.now();
		    String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
		     
		    String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		    String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal_rilis;
		     
		    String nkk_sebelum = keluargaMapper.getNKKSebelum(digitnkk);
			Long nkk = Long.parseLong(digitnkk + "0001");
			if(nkk_sebelum != null) {
				nkk = Long.parseLong(nkk_sebelum) + 1;
			}
	
			String nkk_keluarga = Long.toString(nkk);
			keluarga.setNomor_kk(nkk_keluarga);
			keluarga.setId_kelurahan(kelurahanMapper.getIdbyKodeKelurahan(kode_kelurahan));
			keluargaMapper.addKeluarga(keluarga);
			return nkk_keluarga;
		}
	}
	
	public void updateKeluarga(KeluargaModel keluarga, AlamatModel kode_alamat) {
		String nkk = keluarga.getNomor_kk();
		int id = keluargaMapper.selectKeluargabyNKK(nkk).getId();
		String current_nkk = nkk.substring(0, 6);
		String kode_kecamatan = kode_alamat.getKode_kecamatan();
		String kode_kelurahan = kode_alamat.getKode_kelurahan();
		
		if (!kode_kecamatan.equals(current_nkk)) {
			LocalDate localDate = LocalDate.now();
		    String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
		     
		    String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		    String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal_rilis;
		     
		    String nkk_sebelum = keluargaMapper.getNKKSebelum(digitnkk);
			Long nkk_baru = Long.parseLong(digitnkk + "0001");
			if(nkk_sebelum != null) {
				nkk_baru = Long.parseLong(nkk_sebelum) + 1;
			}
			keluarga.setNomor_kk(Long.toString(nkk_baru));
		}
		
		keluarga.setId_kelurahan(kelurahanMapper.getIdbyKodeKelurahan(kode_kelurahan));
		 
		keluargaMapper.updateKeluarga(keluarga, id);
	}
	
	public void updateStatusBerlaku(int id) {
		keluargaMapper.updateStatusBerlaku(id);
	}
}
