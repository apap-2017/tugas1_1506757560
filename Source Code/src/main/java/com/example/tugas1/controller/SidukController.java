package com.example.tugas1.controller;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.AlamatModel;
import com.example.tugas1.service.KotaService;
import com.example.tugas1.service.KecamatanService;
import com.example.tugas1.service.KeluargaService;
import com.example.tugas1.service.KelurahanService;
import com.example.tugas1.service.PendudukService;
import com.example.tugas1.service.SidukService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class SidukController {
	@Autowired
	SidukService sidukDAO;
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	KeluargaService keluargaDAO;
	@Autowired
	KelurahanService kelurahanDAO;
	@Autowired
	KecamatanService kecamatanDAO;
	@Autowired
	KotaService kotaDAO;

	@RequestMapping("/")
	public String index (){
		 return "index";
	 }
	 
	
	 @RequestMapping("/penduduk")
	 public String viewPenduduk (Model model,
			 @RequestParam(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 AlamatModel alamat = sidukDAO.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
		 
		 if (penduduk != null) {
			 model.addAttribute ("penduduk", penduduk);
			 model.addAttribute("keluarga", keluarga);
			 model.addAttribute("alamat", alamat);
			 return "view-penduduk";
		 } else {
			 model.addAttribute ("nik", nik);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga")
	 public String viewKeluarga (Model model,
			 @RequestParam(value = "nkk") String nkk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
		 AlamatModel alamat = sidukDAO.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
		 List<PendudukModel> anggota_keluarga = pendudukDAO.selectAnggotaKeluarga(keluarga.getId());
		 if (keluarga != null) {
			 model.addAttribute ("keluarga", keluarga);
			 model.addAttribute("anggota", anggota_keluarga);
			 model.addAttribute("alamat", alamat);
			 return "view-keluarga";
		 } else {
			 model.addAttribute ("nkk", nkk);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/penduduk/tambah")
	 public String tambahPenduduk (Model model){
		 model.addAttribute("penduduk", new PendudukModel());
		 return "form-tambah-penduduk";
	 }
	 
	 @RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	 public String tambahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 
		 if (keluarga != null) {
			 AlamatModel alamat = sidukDAO.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
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
			 
			 int nomor_urut = sidukDAO.getNomorUrutNIK(digitnik) + 1;
			 digitnik = digitnik + "0000";
			 Long nik = Long.parseLong(digitnik) + nomor_urut;
			 String nik_penduduk = Long.toString(nik);
			 penduduk.setNik(nik_penduduk);
			 
			 pendudukDAO.addPenduduk(penduduk);
			 model.addAttribute ("nik", nik_penduduk);
			 return "tambah-penduduk-success";
		 } else {
			 model.addAttribute ("nkk", penduduk.getId_keluarga());
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga/tambah")
	 public String tambahKeluarga(Model model){
		 model.addAttribute("keluarga", new KeluargaModel());
		 return "form-tambah-keluarga";
	 }
	 
	 @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	 public String tambahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga){
		 String nama_kelurahan = keluarga.getNama_kelurahan();
		 String nama_kecamatan = keluarga.getNama_kecamatan();
		 String nama_kota = keluarga.getNama_kota();
		 AlamatModel kode_alamat = sidukDAO.getKodeAlamatbyNama(nama_kelurahan, nama_kecamatan, nama_kota);
		 String kode_kecamatan = kode_alamat.getKode_kecamatan();
		 String kode_kelurahan = kode_alamat.getKode_kelurahan();
		 
		 if (kode_alamat != null) {
			 LocalDate localDate = LocalDate.now();
		     String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
		     
		     String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		     String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal_rilis;
		     int nomor_urut = sidukDAO.getNomorUrutNKK(digitnkk) + 1;
		     
		     String nomor_kk = digitnkk + "0000";
		     Long nik = Long.parseLong(nomor_kk) + nomor_urut;
			 String nkk_keluarga = Long.toString(nik);
			 keluarga.setNomor_kk(nkk_keluarga);
			 keluarga.setId_kelurahan(kelurahanDAO.getIdbyKodeKelurahan(kode_kelurahan));
			 model.addAttribute ("nkk", nkk_keluarga);
			 keluargaDAO.addKeluarga(keluarga);
			 return "tambah-keluarga-success";
		 } else {
			 model.addAttribute ("nkk", keluarga.getAlamat());
			 return "not-found.html"; 
		 }
	 }
	 
}
