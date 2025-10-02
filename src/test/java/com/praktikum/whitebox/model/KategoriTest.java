package com.praktikum.whitebox.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KategoriTest {

    @Test
    void testDefaultConstructor() {
        Kategori kategori = new Kategori();
        assertNull(kategori.getKode());
        assertNull(kategori.getNama());
        assertNull(kategori.getDeskripsi());
        assertFalse(kategori.isAktif()); // default boolean = false
    }

    @Test
    void testConstructorWithArgs() {
        Kategori kategori = new Kategori("K001", "Makanan", "Kategori makanan");
        assertEquals("K001", kategori.getKode());
        assertEquals("Makanan", kategori.getNama());
        assertEquals("Kategori makanan", kategori.getDeskripsi());
        assertTrue(kategori.isAktif()); // constructor set aktif = true
    }

    @Test
    void testSettersAndGetters() {
        Kategori kategori = new Kategori();
        kategori.setKode("K002");
        kategori.setNama("Minuman");
        kategori.setDeskripsi("Kategori minuman");
        kategori.setAktif(true);

        assertEquals("K002", kategori.getKode());
        assertEquals("Minuman", kategori.getNama());
        assertEquals("Kategori minuman", kategori.getDeskripsi());
        assertTrue(kategori.isAktif());
    }

    @Test
    void testEqualsAndHashCode() {
        Kategori k1 = new Kategori("K003", "Snack", "Cemilan");
        Kategori k2 = new Kategori("K003", "Camilan", "Makanan Ringan");
        Kategori k3 = new Kategori("K004", "Buah", "Buah Segar");

        // equals berdasarkan kode
        assertEquals(k1, k2);
        assertNotEquals(k1, k3);

        // hashCode juga berdasarkan kode
        assertEquals(k1.hashCode(), k2.hashCode());
        assertNotEquals(k1.hashCode(), k3.hashCode());
    }

    @Test
    void testEqualsWithDifferentObject() {
        Kategori kategori = new Kategori("K005", "Sayur", "Sayuran");
        assertNotEquals(kategori, "string");
        assertNotEquals(kategori, null);
    }

    @Test
    void testToString() {
        Kategori kategori = new Kategori("K006", "Daging", "Produk daging");
        String str = kategori.toString();

        assertTrue(str.contains("K006"));
        assertTrue(str.contains("Daging"));
        assertTrue(str.contains("Produk daging"));
        assertTrue(str.contains("aktif=true"));
    }
    @Test
    void testEqualsSelf() {
        Kategori kategori = new Kategori("K007", "Elektronik", "Barang elektronik");
        assertEquals(kategori, kategori); // harus true saat dibandingkan dengan dirinya sendiri
    }

    @Test
    void testHashCodeConsistency() {
        Kategori kategori = new Kategori("K008", "Perabot", "Barang rumah tangga");
        int hash1 = kategori.hashCode();
        int hash2 = kategori.hashCode();
        assertEquals(hash1, hash2); // konsisten
    }

    @Test
    void testSetAktifFalse() {
        Kategori kategori = new Kategori("K009", "Pakaian", "Kategori pakaian");
        kategori.setAktif(false);
        assertFalse(kategori.isAktif());
    }

    @Test
    void testToStringWhenNotActive() {
        Kategori kategori = new Kategori();
        kategori.setKode("K010");
        kategori.setNama("Kosmetik");
        kategori.setDeskripsi("Produk kecantikan");
        kategori.setAktif(false);

        String str = kategori.toString();
        assertTrue(str.contains("K010"));
        assertTrue(str.contains("Kosmetik"));
        assertTrue(str.contains("Produk kecantikan"));
        assertTrue(str.contains("aktif=false")); // pastikan boolean false ikut tercetak
    }

}