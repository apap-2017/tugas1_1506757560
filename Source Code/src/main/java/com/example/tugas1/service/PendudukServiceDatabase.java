package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.dao.SidukMapper;
import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private KeluargaMapper keluargaMapper;
	@Autowired
	private SidukMapper lokasiMapper;
	
	public PendudukModel selectPendudukbyNIK(String nik) {
		log.info ("select penduduk with nik {}", nik);
		PendudukModel penduduk = pendudukMapper.selectPendudukbyNIK(nik);
		penduduk.setNkk(keluargaMapper.selectKeluargabyID(penduduk.getId_keluarga()).getNomor_kk());
		return penduduk;
	}
	
	public List<PendudukModel> selectAnggotaKeluarga(int id_keluarga){
		log.info ("select penduduk with id keluarga {}", id_keluarga);
		return pendudukMapper.selectAnggotaKeluarga(id_keluarga);
	}
	
	public PendudukModel addPenduduk(PendudukModel penduduk) {
		log.info ("add penduduk with id keluarga {}", penduduk.getNik());
		penduduk.setId_keluarga(keluargaMapper.selectKeluargabyNKK(penduduk.getNkk()).getId());
		KeluargaModel keluarga = keluargaMapper.selectKeluargabyID(penduduk.getId_keluarga());
		AlamatModel alamat = lokasiMapper.getKodeAlamatbyIdKel(keluarga.getId_kelurahan());
		
		String tanggal_lahir = penduduk.getTanggal_lahir();
		String tanggal = tanggal_lahir.substring(8, 10);
		if (penduduk.getJenis_kelamin() == 1) {
			int tgl = Integer.parseInt(tanggal);
			tgl = tgl + 40;
			tanggal = Integer.toString(tgl);
		}
		
		String kelahiran = tanggal + tanggal_lahir.substring(5, 7) + tanggal_lahir.substring(2,4);
		String kode_kecamatan = alamat.getKode_kecamatan();
		String digitnik = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + kelahiran;
		 
		String nik_sebelum = pendudukMapper.getNIKSebelum(digitnik);
		Long nik = Long.parseLong(digitnik + "0001");
		if(nik_sebelum != null) {
			 nik = Long.parseLong(nik_sebelum) + 1;
		}
		 
		String nik_penduduk = Long.toString(nik);
		penduduk.setNik(nik_penduduk);
		pendudukMapper.addPenduduk(penduduk);
		return penduduk;
	}
	
	public void updatePenduduk(PendudukModel penduduk) {
		log.info ("update penduduk with id {}", penduduk.getId());
		PendudukModel current_penduduk = pendudukMapper.selectPendudukbyNIK(penduduk.getNik());
		int id_penduduk = current_penduduk.getId();
		KeluargaModel keluarga = keluargaMapper.selectKeluargabyID(penduduk.getId_keluarga());
		String current_nik = penduduk.getNik().substring(0, 12);
		AlamatModel alamat = lokasiMapper.getKodeAlamatbyIdKel(keluarga.getId_kelurahan());
		String tanggal_lahir = penduduk.getTanggal_lahir();
		String tanggal = tanggal_lahir.substring(8, 10);
		
		if (penduduk.getJenis_kelamin() == 1) {
			int tgl = Integer.parseInt(tanggal);
			tgl = tgl + 40;
			tanggal = Integer.toString(tgl);
		}
		 
		String kelahiran = tanggal + tanggal_lahir.substring(5, 7) + tanggal_lahir.substring(2,4);
		String kode_kecamatan = alamat.getKode_kecamatan();
		String digitnik = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + kelahiran;
		String nik_sebelum = pendudukMapper.getNIKSebelum(digitnik);
		 
		if (!digitnik.equals(current_nik)) {
			Long new_nik = Long.parseLong(digitnik + "0001");
			if(nik_sebelum != null) {
				new_nik = Long.parseLong(nik_sebelum) + 1;
			}
			String nik_penduduk = Long.toString(new_nik);
			penduduk.setNik(nik_penduduk);
		}
		 
		if(penduduk.getGolongan_darah() == null) {
			 penduduk.setGolongan_darah(current_penduduk.getGolongan_darah());
		} if(penduduk.getStatus_perkawinan() == null) {
			 penduduk.setStatus_perkawinan(current_penduduk.getStatus_perkawinan());
		} if(penduduk.getStatus_dalam_keluarga() == null) {
			 penduduk.setStatus_dalam_keluarga(current_penduduk.getStatus_dalam_keluarga());
		} 
		pendudukMapper.updatePenduduk(penduduk, id_penduduk);
	}
	
	public void updateStatusKematian(PendudukModel penduduk) {
		int id_keluarga = penduduk.getId_keluarga();
		List<PendudukModel> anggota_keluarga = pendudukMapper.selectAnggotaKeluarga(id_keluarga);
		int wafat = 0;
		for (PendudukModel p : anggota_keluarga) {
			if (p.getIs_wafat() == 1) {
				wafat = wafat + 1;
			}
		}
		 
		if (wafat == anggota_keluarga.size()) {
			 keluargaMapper.updateStatusBerlaku(id_keluarga);
		}
		pendudukMapper.updateStatusKematian(penduduk.getNik());
	}
	
	public List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan){
		return pendudukMapper.selectPendudukByIdKelurahan(id_kelurahan);
	}
	
	public PendudukModel getPendudukTermudaSekelurahan(int id_kelurahan) {
		return pendudukMapper.getPendudukTermudaSekelurahan(id_kelurahan);
	}
}
