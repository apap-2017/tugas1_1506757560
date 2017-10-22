package com.example.tugas1.controller;
import java.util.List;

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
import com.example.tugas1.service.LokasiService;

@Controller
public class SidukController {
	@Autowired
	LokasiService lokasiDAO;
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
	 
	
	 @RequestMapping(value = "/penduduk", method = RequestMethod.GET)
	 public String viewPenduduk (Model model, 
			 @RequestParam(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 
		 if (penduduk != null) {
			 model.addAttribute ("penduduk", penduduk);
			 return "view-penduduk";
		 } else {
			 model.addAttribute ("nik_salah", nik);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/keluarga", method = RequestMethod.GET)
	 public String viewKeluarga (Model model,
			 @RequestParam(value = "nkk") String nkk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
		 if (keluarga != null) {
			 model.addAttribute ("keluarga", keluarga);
			 return "view-keluarga";
		 } else {
			 model.addAttribute ("nkk_salah", nkk);
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
			 PendudukModel new_penduduk = pendudukDAO.addPenduduk(penduduk);
			 model.addAttribute ("nik_tambah", new_penduduk.getNik());
			 return "success";
		 } else {
			 model.addAttribute ("id_keluarga_salah", penduduk.getId_keluarga());
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga/tambah")
	 public String tambahKeluarga(Model model){
		 List<KotaModel> kota_list = kotaDAO.selectKotaList();
		 List<KecamatanModel> kecamatan_list = kecamatanDAO.selectKecamatanList();
		 List<KelurahanModel> kelurahan_list = kelurahanDAO.selectKelurahanList();
		 model.addAttribute("keluarga", new KeluargaModel());
		 model.addAttribute("kota_list", kota_list);
		 model.addAttribute("kecamatan_list", kecamatan_list);
		 model.addAttribute("kelurahan_list", kelurahan_list);
		 return "form-tambah-keluarga";
	 }
	 
	 @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	 public String tambahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga){
		 String nkk = keluargaDAO.addKeluarga(keluarga);
		 if (!nkk.equals("not ok")) {
			 model.addAttribute ("nkk_tambah", nkk);
			 return "success";
		 } else {
			 model.addAttribute ("alamat", keluarga.getAlamat());
			 return "not-found"; 
		 }
	 }
	 
	 @RequestMapping("/penduduk/ubah/")
	 public String ubahPenduduk(){
		 return "ubah-penduduk";
	 }
	 
	 @RequestMapping("/penduduk/ubah/{nik}")
	 public String ubahPenduduk(Model model, @PathVariable(value = "nik") String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 if (penduduk != null) {
			 model.addAttribute("penduduk", penduduk);
			 return "form-ubah-penduduk";
		 } else {
			 model.addAttribute("nik_salah", nik);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	 public String ubahPendudukSubmit (Model model, @ModelAttribute PendudukModel penduduk, 
			 @PathVariable(value = "nik") String nik){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyID(penduduk.getId_keluarga());
		 
		 if (keluarga != null) {
			 boolean update = pendudukDAO.updatePenduduk(penduduk);
			 if (update){
				 model.addAttribute("nik_lama", nik);
				 String nik_baru = penduduk.getNik();
				 if(nik.equals(nik_baru)) {
					 model.addAttribute("nik_ubah", nik);
				 } else {
					 model.addAttribute("nik_baru", nik_baru); 
				 }
			 } else {
				 model.addAttribute("nik_tidak_ubah", nik);
			 }
			 return "success";
		 } else {
			 model.addAttribute ("id_keluarga_salah", penduduk.getId_keluarga());
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping("/keluarga/ubah/{nkk}")
	 public String ubahKeluarga(Model model, @PathVariable(value = "nkk") String nkk){
		 KeluargaModel keluarga = keluargaDAO.selectKeluargabyNKK(nkk);
		 if (keluarga != null) {
			 AlamatModel alamat = lokasiDAO.getAlamatLengkapbyIdKel(keluarga.getId_kelurahan());
			 keluarga.setNama_kelurahan(alamat.getNama_kelurahan());
			 keluarga.setNama_kecamatan(alamat.getNama_kecamatan());
			 keluarga.setNama_kota(alamat.getNama_kota());
			 keluarga.setId_kota(alamat.getId_kota());
			 keluarga.setId_kecamatan(alamat.getId_kecamatan());
			 
			 model.addAttribute("kota_list", kotaDAO.selectKotaList());
			 model.addAttribute("kecamatan_list", kecamatanDAO.selectKecamatanList());
			 model.addAttribute("kelurahan_list", kelurahanDAO.selectKelurahanList());
			 model.addAttribute("keluarga", keluarga);
			 
			 return "form-ubah-keluarga";
		 } else {
			 model.addAttribute("nkk_salah", nkk);
			 return "not-found";
		 }
	 }
	 
	 @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	 public String ubahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga, 
			 @PathVariable(value = "nkk") String nkk){
		 AlamatModel kode_alamat = lokasiDAO.getKodeAlamatbyId(keluarga.getId_kelurahan(),
				 keluarga.getId_kecamatan(), keluarga.getId_kota());

		 if (kode_alamat != null) {
			 boolean update = keluargaDAO.updateKeluarga(keluarga, kode_alamat);
			 if (update){
				 String nkk_baru = keluarga.getNomor_kk();
				 model.addAttribute("nkk_lama", nkk);
				 if(nkk.equals(nkk_baru)) {
					 model.addAttribute("nkk_ubah", nkk);
				 } else {
					 model.addAttribute("nkk_baru", nkk_baru); 
				 }
			 } else {
				 model.addAttribute("nkk_tidak_ubah", nkk);
			 }
			 return "success";
		 } else {
			 model.addAttribute ("alamat", keluarga.getAlamat());
			 return "not-found"; 
		 }
	 }
	 
	 @RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	 public String ubahStatusKematianSubmit (Model model,
			 @RequestParam ( value = "nik" , required = true ) String nik){
		 PendudukModel penduduk = pendudukDAO.selectPendudukbyNIK(nik);
		 pendudukDAO.updateStatusKematian(penduduk);
		 
		 model.addAttribute("nik_kematian", nik);
		 return "success";
	 }
	 

	 @RequestMapping(value = "/penduduk/cari",  method = RequestMethod.GET)
	 public String cariPendudukKelurahan (Model model,
			 @RequestParam(value = "kt", required = false, defaultValue = "0") int id_kota,
	         @RequestParam(value = "kc", required = false, defaultValue = "0") int id_kecamatan,
	         @RequestParam(value = "kl", required = false, defaultValue = "0") int id_kelurahan){
		 List<KotaModel> kota_list = kotaDAO.selectKotaList();
		 model.addAttribute("kota_list", kota_list);
		 if (id_kelurahan != 0) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("nama_kecamatan", kecamatanDAO.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			 model.addAttribute("id_kecamatan", id_kecamatan);
			 model.addAttribute("nama_kelurahan", kelurahanDAO.selectKelurahanbyID(id_kelurahan).getNama_kelurahan());
			 model.addAttribute("id_kelurahan", id_kelurahan);
			 model.addAttribute("view", "view");
			 model.addAttribute("penduduk_list", pendudukDAO.selectPendudukByIdKelurahan(id_kelurahan));
			 model.addAttribute("penduduk_termuda", pendudukDAO.getPendudukTermudaSekelurahan(id_kelurahan));
			 model.addAttribute("penduduk_tertua", pendudukDAO.getPendudukTertuaSekelurahan(id_kelurahan));
		 } else if (id_kecamatan != 0) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("nama_kecamatan", kecamatanDAO.selectKecamatanbyID(id_kecamatan).getNama_kecamatan());
			 model.addAttribute("id_kecamatan", id_kecamatan);
			 model.addAttribute("kelurahan_list", kelurahanDAO.selectKelurahanList(id_kecamatan));
		 } else if (id_kota != 0) {
			 model.addAttribute("nama_kota", kotaDAO.selectKotabyID(id_kota).getNama_kota());
			 model.addAttribute("id_kota", id_kota);
			 model.addAttribute("kecamatan_list", kecamatanDAO.selectKecamatanList(id_kota));
		 }
		 return "cari-penduduk";
	 }
	 
}
