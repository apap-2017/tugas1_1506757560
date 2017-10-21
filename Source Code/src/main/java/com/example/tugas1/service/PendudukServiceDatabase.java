package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.dao.LokasiMapper;
import com.example.tugas1.dao.PendudukMapper;
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
	private LokasiMapper lokasiMapper;
	
	public PendudukModel selectPendudukbyNIK(String nik) {
		log.info ("select penduduk with nik {}", nik);
		PendudukModel penduduk = pendudukMapper.selectPendudukbyNIK(nik);
		if (penduduk != null) {
			KeluargaModel keluarga = keluargaMapper.selectKeluargabyID(penduduk.getId_keluarga());
			AlamatModel alamat = lokasiMapper.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
			
			penduduk.setNkk(keluarga.getNomor_kk());
			penduduk.setRt(keluarga.getRt());
			penduduk.setRw(keluarga.getRw());
			penduduk.setAlamat(keluarga.getAlamat());
			penduduk.setNama_kelurahan(alamat.getNama_kelurahan());
			penduduk.setNama_kecamatan(alamat.getNama_kecamatan());
			penduduk.setNama_kota(alamat.getNama_kota());
		}
		
		return penduduk;
	}
	
	public PendudukModel addPenduduk(PendudukModel penduduk) {
		log.info ("add penduduk with id keluarga {}", penduduk.getNik());
		
		String nik_penduduk = generateNik(penduduk);
		penduduk.setNik(nik_penduduk);
		pendudukMapper.addPenduduk(penduduk);
		return penduduk;
	}
	
	public boolean updatePenduduk(PendudukModel penduduk) {
		PendudukModel current_penduduk = pendudukMapper.selectPendudukbyId(penduduk.getId());
		if(penduduk.getGolongan_darah() == null) {
			 penduduk.setGolongan_darah(current_penduduk.getGolongan_darah());
		} if(penduduk.getStatus_perkawinan() == null) {
			 penduduk.setStatus_perkawinan(current_penduduk.getStatus_perkawinan());
		} if(penduduk.getStatus_dalam_keluarga() == null) {
			 penduduk.setStatus_dalam_keluarga(current_penduduk.getStatus_dalam_keluarga());
		} if(penduduk.getAgama() == null) {
			 penduduk.setAgama(current_penduduk.getAgama());
		}
		
		int id_penduduk = current_penduduk.getId();
		
		if (!current_penduduk.equals(penduduk)) {
			if (!(current_penduduk.getTanggal_lahir().equals(penduduk.getTanggal_lahir()) && 
					current_penduduk.getId_keluarga() == penduduk.getId_keluarga() && 
					current_penduduk.getJenis_kelamin() == penduduk.getJenis_kelamin())) {
				penduduk.setNik(generateNik(penduduk));
			}
			  
			pendudukMapper.updatePenduduk(penduduk, id_penduduk);
			return true;
		} else {
			return false;
		}
	}
	
	public void updateStatusKematian(PendudukModel penduduk) {
		int id_keluarga = penduduk.getId_keluarga();
		List<PendudukModel> anggota_keluarga = keluargaMapper.selectAnggotaKeluarga(id_keluarga);
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
	
	public PendudukModel getPendudukTertuaSekelurahan(int id_kelurahan) {
		return pendudukMapper.getPendudukTertuaSekelurahan(id_kelurahan);
	}
	
	public String generateNik(PendudukModel penduduk) {
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
		return nik_penduduk;
	}
}
