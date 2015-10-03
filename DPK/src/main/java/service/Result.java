package service;

import java.util.Arrays;

public class Result {
	String fakultas;
    String prodi;
    int semester;
    int tahun;
    String kode;
    String mata_kuliah;
    int sks;
    String kelas;
    String dosen;
    int jumlah_peserta;
    Peserta[] peserta;
    		
    public String getFakultas() {
		return fakultas;
	}

	public void setFakultas(String fakultas) {
		this.fakultas = fakultas;
	}

	public String getProdi() {
		return prodi;
	}

	public void setProdi(String prodi) {
		this.prodi = prodi;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getTahun() {
		return tahun;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getMata_kuliah() {
		return mata_kuliah;
	}

	public void setMata_kuliah(String mata_kuliah) {
		this.mata_kuliah = mata_kuliah;
	}

	public int getSks() {
		return sks;
	}

	public void setSks(int sks) {
		this.sks = sks;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public String getDosen() {
		return dosen;
	}

	public void setDosen(String dosen) {
		this.dosen = dosen;
	}

	public int getJumlah_peserta() {
		return jumlah_peserta;
	}

	public void setJumlah_peserta(int jumlah_peserta) {
		this.jumlah_peserta = jumlah_peserta;
		peserta = new Peserta[jumlah_peserta];
	}

	public Peserta[] getPeserta() {
		return peserta;
	}

	public void setPeserta(Peserta[] peserta) {
		this.peserta = peserta;
	}
	
	public void addPeserta(int i, String nim, String nama) {
		peserta[i] = new Peserta(nim, nama);
	}

	@Override
	public String toString() {
		return "Response [fakultas=" + fakultas + ", prodi=" + prodi + ", semester=" + semester + ", tahun=" + tahun
				+ ", kode=" + kode + ", mata_kuliah=" + mata_kuliah + ", sks=" + sks + ", kelas=" + kelas + ", dosen="
				+ dosen + ", jumlah_peserta=" + jumlah_peserta + ", peserta=" + Arrays.toString(peserta) + "]";
	}

	public class Peserta {
    	String nim;
    	String nama;
    	
    	public Peserta(String nim, String nama) {
    		this.nim = nim;
    		this.nama = nama;
    	}
    	
		public String getNim() {
			return nim;
		}

		public void setNim(String nim) {
			this.nim = nim;
		}

		public String getNama() {
			return nama;
		}

		public void setNama(String nama) {
			this.nama = nama;
		}

		@Override
		public String toString() {
			return "Peserta [nim=" + nim + ", nama=" + nama + "]";
		}
    }
}
