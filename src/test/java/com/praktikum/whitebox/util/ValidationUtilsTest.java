package com.praktikum.whitebox.util;

import com.praktikum.whitebox.model.Kategori;
import com.praktikum.whitebox.model.Produk;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilsTest {

    @Test
    void testIsValidKodeProduk() {
        assertFalse(ValidationUtils.isValidKodeProduk(null));
        assertFalse(ValidationUtils.isValidKodeProduk(" "));
        assertFalse(ValidationUtils.isValidKodeProduk("ab")); // kurang dari 3
        assertTrue(ValidationUtils.isValidKodeProduk("ABC123"));
        assertFalse(ValidationUtils.isValidKodeProduk("ABCDEFGHIJK")); // lebih dari 10
    }

    @Test
    void testIsValidNama() {
        assertFalse(ValidationUtils.isValidNama(null));
        assertFalse(ValidationUtils.isValidNama("  "));
        assertFalse(ValidationUtils.isValidNama("A")); // < 3
        assertTrue(ValidationUtils.isValidNama("Laptop Bag"));
        assertFalse(ValidationUtils.isValidNama("A".repeat(101))); // > 100
    }

    @Test
    void testIsValidHarga() {
        assertFalse(ValidationUtils.isValidHarga(0));
        assertFalse(ValidationUtils.isValidHarga(-10));
        assertTrue(ValidationUtils.isValidHarga(100));
    }

    @Test
    void testIsValidStok() {
        assertTrue(ValidationUtils.isValidStok(0));
        assertTrue(ValidationUtils.isValidStok(10));
        assertFalse(ValidationUtils.isValidStok(-1));
    }

    @Test
    void testIsValidStokMinimum() {
        assertTrue(ValidationUtils.isValidStokMinimum(0));
        assertTrue(ValidationUtils.isValidStokMinimum(5));
        assertFalse(ValidationUtils.isValidStokMinimum(-3));
    }

    @Test
    void testIsValidProduk() {
        Produk produk = new Produk("P01", "Laptop", "Elektronik", 5000.0, 10, 2);
        assertTrue(ValidationUtils.isValidProduk(produk));

        Produk produkNull = null;
        assertFalse(ValidationUtils.isValidProduk(produkNull));

        Produk produkInvalid = new Produk("X", "A", "Kategori", -100.0, -1, -5);
        assertFalse(ValidationUtils.isValidProduk(produkInvalid));
    }

    @Test
    void testIsValidKategori() {
        Kategori kategori = new Kategori("K01", "Elektronik", "Barang-barang elektronik");
        assertTrue(ValidationUtils.isValidKategori(kategori));

        assertFalse(ValidationUtils.isValidKategori(null));

        Kategori invalidKode = new Kategori("X", "Elektronik", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidKode));

        Kategori invalidNama = new Kategori("K02", "A", "Deskripsi");
        assertFalse(ValidationUtils.isValidKategori(invalidNama));

        Kategori invalidDeskripsi = new Kategori("K03", "Elektronik", "a".repeat(600));
        assertFalse(ValidationUtils.isValidKategori(invalidDeskripsi));
    }

    @Test
    void testIsValidPersentase() {
        assertTrue(ValidationUtils.isValidPersentase(0));
        assertTrue(ValidationUtils.isValidPersentase(50));
        assertTrue(ValidationUtils.isValidPersentase(100));
        assertFalse(ValidationUtils.isValidPersentase(-1));
        assertFalse(ValidationUtils.isValidPersentase(120));
    }

    @Test
    void testIsValidKuantitas() {
        assertTrue(ValidationUtils.isValidKuantitas(1));
        assertTrue(ValidationUtils.isValidKuantitas(10));
        assertFalse(ValidationUtils.isValidKuantitas(0));
        assertFalse(ValidationUtils.isValidKuantitas(-5));
    }
}
