package com.example.tugas1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private int id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private int id_kelurahan;
	private int id_kecamatan;
	private int id_kota;
	private boolean is_tidakberlaku;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
	private List<PendudukModel> anggota;
}
