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
			 AlamatModel alamat = sidukDAO.getKodeAlamatbyIdKel(keluarga.getId_kelurahan());
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
			 
			 String nik_sebelum = sidukDAO.getNIKSebelum(digitnik);
			 Long nik = Long.parseLong(digitnik + "0001");
			 if(nik_sebelum != null) {
				 nik = Long.parseLong(nik_sebelum) + 1;
			 }
			 
			 String nik_penduduk = Long.toString(nik);
			 penduduk.setNik(nik_penduduk);
			 
			 pendudukDAO.addPenduduk(penduduk);
			 model.addAttribute ("nik_tambah", nik);
			 return "success";
		 } else {
			 model.addAttribute ("id_keluarga", penduduk.getId_keluarga());
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
		 /*
		  * 
		  * Handle kalo kode alamat null !!
		  * 
		  * 
		  */
		 if (kode_alamat != null) {
			 LocalDate localDate = LocalDate.now();
		     String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
		     
		     String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
		     String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal_rilis;
		     
		     String nkk_sebelum = sidukDAO.getNKKSebelum(digitnkk);
			 Long nkk = Long.parseLong(digitnkk + "0001");
			 if(nkk_sebelum != null) {
				 nkk = Long.parseLong(nkk_sebelum) + 1;
			 }

			 String nkk_keluarga = Long.toString(nkk);
			 keluarga.setNomor_kk(nkk_keluarga);
			 keluarga.setId_kelurahan(kelurahanDAO.getIdbyKodeKelurahan(kode_kelurahan));
			 
			 model.addAttribute ("nkk_tambah", nkk_keluarga);
			 keluargaDAO.addKeluarga(keluarga);
			 return "success";
		 } else {
			 model.addAttribute ("alamat", keluarga.getAlamat());
			 return "not-found.html"; 
		 }
	 }
	 
	 @RequestMapping("/penduduk/ubah/{nik}")
	 public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 if (penduduk != null) {
			 model.addAttribute("penduduk", penduduk);
			 return "form-ubah-penduduk";
		 } else {
			 model.addAttribute("nik", nik);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	 public String ubahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk){
		 String nik = penduduk.getNik();
		 PendudukModel current_penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 int id_penduduk = current_penduduk.getId();
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 String current_nik = nik.substring(0, 12);
		 
		 if (keluarga != null) {
			 AlamatModel alamat = sidukDAO.getKodeAlamatbyIdKel(keluarga.getId_kelurahan());
			 
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
			 String nik_sebelum = sidukDAO.getNIKSebelum(digitnik);
			 
			 
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
				 
			  
			 pendudukDAO.updatePenduduk(penduduk, id_penduduk);
			 model.addAttribute("nik_ubah", nik);
			 return "success";
		 } else {
			 model.addAttribute ("id_keluarga", penduduk.getId_keluarga());
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga/ubah/{nkk}")
	 public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
		 if (keluarga != null) {
			 AlamatModel alamat = sidukDAO.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
			 keluarga.setNama_kelurahan(alamat.getNama_kelurahan());
			 keluarga.setNama_kecamatan(alamat.getNama_kecamatan());
			 keluarga.setNama_kota(alamat.getNama_kota());
			 model.addAttribute("keluarga", keluarga);
			 return "form-ubah-keluarga";
		 } else {
			 model.addAttribute("nkk", nkk);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	 public String ubahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga, @PathVariable(value = "nkk") String nkk){
		 String nama_kelurahan = keluarga.getNama_kelurahan();
		 String nama_kecamatan = keluarga.getNama_kecamatan();
		 String nama_kota = keluarga.getNama_kota();
		 AlamatModel kode_alamat = sidukDAO.getKodeAlamatbyNama(nama_kelurahan, nama_kecamatan, nama_kota);
		 String kode_kecamatan = kode_alamat.getKode_kecamatan();
		 String kode_kelurahan = kode_alamat.getKode_kelurahan();
		 
		 int id = keluargaDAO.selectKeluargabyNKK(nkk).getId();
		 String current_nkk = nkk.substring(0, 6);
		 if (kode_alamat != null) {
			 
			 if (!kode_kecamatan.equals(current_nkk)) {
				 LocalDate localDate = LocalDate.now();
			     String tanggal = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDate);
			     
			     String tanggal_rilis = tanggal.substring(8, 10) + tanggal.substring(5, 7) + tanggal.substring(2,4);
			     String digitnkk = kode_kecamatan.substring(0, kode_kecamatan.length()-1) + tanggal_rilis;
			     
			     String nkk_sebelum = sidukDAO.getNKKSebelum(digitnkk);
				 Long nkk_baru = Long.parseLong(digitnkk + "0001");
				 if(nkk_sebelum != null) {
					 nkk_baru = Long.parseLong(nkk_sebelum) + 1;
				 }
				 keluarga.setNomor_kk(Long.toString(nkk_baru));
			 }
			
			 keluarga.setId_kelurahan(kelurahanDAO.getIdbyKodeKelurahan(kode_kelurahan));
			 
			 model.addAttribute ("nkk_ubah", nkk);
			 keluargaDAO.updateKeluarga(keluarga, id);
			 return "success";
		 } else {
			 model.addAttribute ("alamat", keluarga.getAlamat());
			 return "not-found"; 
		 }
	 }
	 
	 @RequestMapping("/penduduk/{nik}")
	 public String ubahStatusKematian(Model model, @PathVariable(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 if (penduduk != null) {
			 return "form-ubah-status-kematian";
		 } else {
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	 public String ubahStatusKematianSubmit (Model model,
			 @RequestParam ( value = "nik" , required = true ) String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 pendudukDAO.updateStatusKematian(nik);
		 int id_keluarga = penduduk.getId_keluarga();
		 List<PendudukModel> anggota_keluarga = pendudukDAO.selectAnggotaKeluarga(id_keluarga);
		 int wafat = 0;
		 for (PendudukModel p : anggota_keluarga) {
			 if (p.getIs_wafat() == 1) {
				 wafat = wafat + 1;
			 }
		 }
		 
		 if (wafat == anggota_keluarga.size()) {
			 keluargaDAO.updateStatusBerlaku(id_keluarga);
		 }
		 model.addAttribute("nik", nik);
		 return "ubah-status-kematian-success";
	 }
	 
	 @RequestMapping(value = "/penduduk/cari")
	 public String cariPenduduk (Model model){
		 List<KotaModel> kota_list = kotaDAO.selectKotaList();
		 model.addAttribute("kota_list", kota_list);
		 return "cari-penduduk";
	 }
}
