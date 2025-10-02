package com.praktikum.whitebox.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.praktikum.whitebox.util.ValidationUtils;

public class KategoriTest {

    @Test
    void testConstructorAndGetters() {
        Kategori kategori = new Kategori("K01", "Elektronik", "Barang-barang elektronik");
        assertEquals("K01", kategori.getKode());
        assertEquals("Elektronik", kategori.getNama());
        assertEquals("Barang-barang elektronik", kategori.getDeskripsi());
        assertTrue(kategori.isAktif()); // default aktif = true
    }

    @Test
    void testSetters() {
        Kategori kategori = new Kategori();
        kategori.setKode("K02");
        kategori.setNama("Fashion");
        kategori.setDeskripsi("Produk pakaian");
        kategori.setAktif(false);

        assertEquals("K02", kategori.getKode());
        assertEquals("Fashion", kategori.getNama());
        assertEquals("Produk pakaian", kategori.getDeskripsi());
        assertFalse(kategori.isAktif());
    }

    @Test
    void testEqualsAndHashCode() {
        Kategori k1 = new Kategori("K03", "Makanan", "Produk makanan");
        Kategori k2 = new Kategori("K03", "Minuman", "Produk minuman");
        Kategori k3 = new Kategori("K04", "Alat", "Peralatan");

        assertEquals(k1, k2); // sama karena kode sama
        assertNotEquals(k1, k3);
        assertEquals(k1.hashCode(), k2.hashCode());
    }

    @Test
    void testToString() {
        Kategori kategori = new Kategori("K05", "Buku", "Produk buku");
        String str = kategori.toString();
        assertNotNull(str);
        assertTrue(str.contains("K05"));
        assertTrue(str.contains("Buku"));
    }

    @Test
    void testValidationUtilsWithKategori() {
        Kategori validKategori = new Kategori("K06", "Gadget", "Smartphone dan aksesoris");
        assertTrue(ValidationUtils.isValidKategori(validKategori));

        Kategori invalidKode = new Kategori("X", "Gadget", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidKode));

        Kategori invalidNama = new Kategori("K07", "A", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidNama));

        Kategori invalidDeskripsi = new Kategori("K08", "Gadget", "a".repeat(600));
        assertFalse(ValidationUtils.isValidKategori(invalidDeskripsi));
    }
}
