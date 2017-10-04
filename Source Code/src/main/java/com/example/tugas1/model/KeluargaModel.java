package com.example.tugas1.model;

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
	private boolean is_tidakberlaku;
	private String nama_kelurahan;
	private String nama_kecamatan;
	private String nama_kota;
}
