package com.praktikum.whitebox.service;
import com.praktikum.whitebox.model.Produk;
import com.praktikum.whitebox.repository.RepositoryProduk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("Test Service Inventaris dengan Mocking")
public class ServiceInventarisTest {
    @Mock
    private RepositoryProduk mockRepositoryProduk;
    private ServiceInventaris serviceInventaris;
    private Produk produkTest;
    @BeforeEach
    void setUp() {
        serviceInventaris = new ServiceInventaris(mockRepositoryProduk);
        produkTest = new Produk("PROD001", "Laptop Gaming", "Elektronik",
                15000000, 10, 5);
    }
    @Test
    @DisplayName("Tambah produk berhasil - semua kondisi valid")
    void testTambahProdukBerhasil() {
// Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        when(mockRepositoryProduk.simpan(produkTest)).thenReturn(true);
// Act
        boolean hasil = serviceInventaris.tambahProduk(produkTest);
// Assert
        assertTrue(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk).simpan(produkTest);
    }
    @Test
    @DisplayName("Tambah produk gagal - produk sudah ada")
    void testTambahProdukGagalSudahAda() {
// Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));

// Act
        boolean hasil = serviceInventaris.tambahProduk(produkTest);
// Assert
        assertFalse(hasil);
        verify(mockRepositoryProduk).cariByKode("PROD001");
        verify(mockRepositoryProduk, never()).simpan(any(Produk.class));
    }
    @Test
    @DisplayName("Keluar stok berhasil - stok mencukupi")
    void testKeluarStokBerhasil() {
// Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001",
                5)).thenReturn(true);
// Act
        boolean hasil = serviceInventaris.keluarStok("PROD001", 5);
// Assert
        assertTrue(hasil);
        verify(mockRepositoryProduk).updateStok("PROD001", 5);
    }
    @Test
    @DisplayName("Keluar stok gagal - stok tidak mencukupi")
    void testKeluarStokGagalStokTidakMencukupi() {
// Arrange
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
// Act
        boolean hasil = serviceInventaris.keluarStok("PROD001", 15);
// Assert
        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).updateStok(anyString(),
                anyInt());
    }
    @Test
    @DisplayName("Hitung total nilai inventaris")
    void testHitungTotalNilaiInventaris() {
// Arrange
        Produk produk1 = new Produk("PROD001", "Laptop", "Elektronik",
                10000000, 2, 1);
        Produk produk2 = new Produk("PROD002", "Mouse", "Elektronik",
                500000, 5, 2);
        Produk produkNonAktif = new Produk("PROD003", "Keyboard",
                "Elektronik", 300000, 3, 1);
        produkNonAktif.setAktif(false);
        List<Produk> semuaProduk = Arrays.asList(produk1, produk2,
                produkNonAktif);
        when(mockRepositoryProduk.cariSemua()).thenReturn(semuaProduk);

// Act
        double totalNilai =
                serviceInventaris.hitungTotalNilaiInventaris();
// Assert
        double expected = (10000000 * 2) + (500000 * 5); // hanya produk aktif
        assertEquals(expected, totalNilai, 0.001);
        verify(mockRepositoryProduk).cariSemua();
    }
    @Test
    @DisplayName("Get produk stok menipis")
    void testGetProdukStokMenipis() {
// Arrange
        Produk produkStokAman = new Produk("PROD001", "Laptop",
                "Elektronik", 10000000, 10, 5);
        Produk produkStokMenipis = new Produk("PROD002", "Mouse",
                "Elektronik", 500000, 3, 5);
        List<Produk> produkMenipis =
                Collections.singletonList(produkStokMenipis);
        when(mockRepositoryProduk.cariProdukStokMenipis()).thenReturn(produkMenipis);
// Act
        List<Produk> hasil = serviceInventaris.getProdukStokMenipis();
// Assert
        assertEquals(1, hasil.size());
        assertEquals("PROD002", hasil.get(0).getKode());
        verify(mockRepositoryProduk).cariProdukStokMenipis();
    }

    // ================== Tambahan Test Case ==================

    @Test
    @DisplayName("Hapus produk gagal - kode tidak valid")
    void testHapusProdukKodeTidakValid() {
        boolean hasil = serviceInventaris.hapusProduk("");
        assertFalse(hasil);
        verify(mockRepositoryProduk, never()).hapus(anyString());
    }

    @Test
    @DisplayName("Hapus produk gagal - produk tidak ditemukan")
    void testHapusProdukTidakDitemukan() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        boolean hasil = serviceInventaris.hapusProduk("PROD001");
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Hapus produk gagal - stok masih ada")
    void testHapusProdukStokMasihAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        produkTest.setStok(5);
        boolean hasil = serviceInventaris.hapusProduk("PROD001");
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Hapus produk berhasil")
    void testHapusProdukBerhasil() {
        produkTest.setStok(0);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.hapus("PROD001")).thenReturn(true);
        boolean hasil = serviceInventaris.hapusProduk("PROD001");
        assertTrue(hasil);
        verify(mockRepositoryProduk).hapus("PROD001");
    }

    @Test
    @DisplayName("Cari produk by kode gagal - kode tidak valid")
    void testCariProdukByKodeKodeTidakValid() {
        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("");
        assertTrue(hasil.isEmpty());
    }

    @Test
    @DisplayName("Cari produk by kode berhasil")
    void testCariProdukByKodeBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        Optional<Produk> hasil = serviceInventaris.cariProdukByKode("PROD001");
        assertTrue(hasil.isPresent());
    }

    @Test
    @DisplayName("Cari produk by nama")
    void testCariProdukByNama() {
        when(mockRepositoryProduk.cariByNama("Laptop")).thenReturn(Collections.singletonList(produkTest));
        List<Produk> hasil = serviceInventaris.cariProdukByNama("Laptop");
        assertEquals(1, hasil.size());
    }

    @Test
    @DisplayName("Cari produk by kategori")
    void testCariProdukByKategori() {
        when(mockRepositoryProduk.cariByKategori("Elektronik")).thenReturn(Collections.singletonList(produkTest));
        List<Produk> hasil = serviceInventaris.cariProdukByKategori("Elektronik");
        assertEquals(1, hasil.size());
    }

    @Test
    @DisplayName("Update stok gagal - stok baru < 0")
    void testUpdateStokNegatif() {
        boolean hasil = serviceInventaris.updateStok("PROD001", -5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Update stok gagal - produk tidak ditemukan")
    void testUpdateStokProdukTidakAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        boolean hasil = serviceInventaris.updateStok("PROD001", 10);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Update stok berhasil")
    void testUpdateStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 20)).thenReturn(true);
        boolean hasil = serviceInventaris.updateStok("PROD001", 20);
        assertTrue(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - kode tidak valid")
    void testKeluarStokKodeTidakValid() {
        boolean hasil = serviceInventaris.keluarStok("", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - jumlah <= 0")
    void testKeluarStokJumlahNegatif() {
        boolean hasil = serviceInventaris.keluarStok("PROD001", 0);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Keluar stok gagal - produk non-aktif")
    void testKeluarStokProdukNonAktif() {
        produkTest.setAktif(false);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        boolean hasil = serviceInventaris.keluarStok("PROD001", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok gagal - jumlah <= 0")
    void testMasukStokJumlahNegatif() {
        boolean hasil = serviceInventaris.masukStok("PROD001", 0);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok gagal - produk tidak ditemukan")
    void testMasukStokProdukTidakAda() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.empty());
        boolean hasil = serviceInventaris.masukStok("PROD001", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok gagal - produk non-aktif")
    void testMasukStokProdukNonAktif() {
        produkTest.setAktif(false);
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        boolean hasil = serviceInventaris.masukStok("PROD001", 5);
        assertFalse(hasil);
    }

    @Test
    @DisplayName("Masuk stok berhasil")
    void testMasukStokBerhasil() {
        when(mockRepositoryProduk.cariByKode("PROD001")).thenReturn(Optional.of(produkTest));
        when(mockRepositoryProduk.updateStok("PROD001", 15)).thenReturn(true);
        boolean hasil = serviceInventaris.masukStok("PROD001", 5);
        assertTrue(hasil);
    }

    @Test
    @DisplayName("Get produk stok habis")
    void testGetProdukStokHabis() {
        Produk produkHabis = new Produk("PROD002", "Mouse", "Elektronik", 50000, 0, 1);
        when(mockRepositoryProduk.cariProdukStokHabis()).thenReturn(Collections.singletonList(produkHabis));
        List<Produk> hasil = serviceInventaris.getProdukStokHabis();
        assertEquals(1, hasil.size());
    }

    @Test
    @DisplayName("Hitung total stok semua produk")
    void testHitungTotalStok() {
        Produk produk1 = new Produk("PROD001", "Laptop", "Elektronik", 10000000, 2, 1);
        Produk produk2 = new Produk("PROD002", "Mouse", "Elektronik", 500000, 5, 2);
        when(mockRepositoryProduk.cariSemua()).thenReturn(Arrays.asList(produk1, produk2));
        int totalStok = serviceInventaris.hitungTotalStok();
        assertEquals(7, totalStok);
    }

    @Test
    @DisplayName("Tambah produk gagal - produk tidak valid")
    void testTambahProdukTidakValid() {
        Produk produkInvalid = new Produk("", "", "", 0, -1, 0); // data salah/invalid
        boolean hasil = serviceInventaris.tambahProduk(produkInvalid);
        assertFalse(hasil);
    }
    @Test
    @DisplayName("Cari produk by nama - input kosong")
    void testCariProdukByNamaKosong() {
        List<Produk> hasil = serviceInventaris.cariProdukByNama("");
        assertTrue(hasil.isEmpty()); // atau bisa assertNotNull tergantung implementasi mock
        verify(mockRepositoryProduk).cariByNama("");
    }

    @Test
    @DisplayName("Cari produk by kategori - input kosong")
    void testCariProdukByKategoriKosong() {
        List<Produk> hasil = serviceInventaris.cariProdukByKategori("");
        assertTrue(hasil.isEmpty());
        verify(mockRepositoryProduk).cariByKategori("");
    }
}