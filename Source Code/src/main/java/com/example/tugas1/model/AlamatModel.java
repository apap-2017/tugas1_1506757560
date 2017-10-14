package com.example.tugas1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlamatModel {
	private String kode_kelurahan;
	private String kode_kecamatan;
	private String kode_kota;
	private int id_kelurahan;
	private int id_kecamatan;
	private int id_kota;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
}
