-- ============================================================
-- BASİT KÜTÜPHANE YÖNETİM SİSTEMİ
-- ============================================================

-- 0. Veritabanını Oluşturma
CREATE DATABASE kutuphane_otomasyon;
USE kutuphane_otomasyon;

-- ============================================================
-- DDL - TABLOLARIN OLUŞTURULMASI
-- ============================================================

-- 1. Kitaplar Tablosu
CREATE TABLE kitap (
    kitap_id INT PRIMARY KEY AUTO_INCREMENT,
    kitap_ad VARCHAR(100),
    yazar VARCHAR(100),
    sayfa_sayisi INT,
    yayin_evi VARCHAR(100)
);

-- 2. Öğrenciler Tablosu
CREATE TABLE ogrenci (
    ogrenci_id INT PRIMARY KEY AUTO_INCREMENT,
    ogrenci_no VARCHAR(15),
    ad VARCHAR(50),
    soyad VARCHAR(50),
    bolum VARCHAR(100)
);

-- 3. Görevliler Tablosu
CREATE TABLE gorevli (
    gorevli_id INT PRIMARY KEY AUTO_INCREMENT,
    ad VARCHAR(50),
    soyad VARCHAR(50),
    unvan VARCHAR(50)
);

-- 4. Ödünç Alma Tablosu
CREATE TABLE odunc_alma (
    odunc_id INT PRIMARY KEY AUTO_INCREMENT,
    kitap_id INT,
    ogrenci_id INT,
    gorevli_id INT,
    verilis_tarihi DATE,
    FOREIGN KEY (kitap_id) REFERENCES kitap(kitap_id),
    FOREIGN KEY (ogrenci_id) REFERENCES ogrenci(ogrenci_id),
    FOREIGN KEY (gorevli_id) REFERENCES gorevli(gorevli_id)
);

-- 5. İade Tablosu
CREATE TABLE iade (
    iade_id INT PRIMARY KEY AUTO_INCREMENT,
    odunc_id INT,
    iade_tarihi DATE,
    durum_notu VARCHAR(200),
    FOREIGN KEY (odunc_id) REFERENCES odunc_alma(odunc_id)
);

-- 6. Ceza Tablosu
CREATE TABLE ceza (
    ceza_id INT PRIMARY KEY AUTO_INCREMENT,
    ogrenci_id INT,
    tutar DECIMAL(10,2),
    aciklama VARCHAR(200),
    FOREIGN KEY (ogrenci_id) REFERENCES ogrenci(ogrenci_id)
);

-- ============================================================
-- DML - VERİ EKLEME (Her Tablo İçin 5 Kayıt)
-- ============================================================

-- Kitaplar
INSERT INTO kitap (kitap_ad, yazar, sayfa_sayisi, yayin_evi) VALUES 
('Nutuk', 'Mustafa Kemal Atatürk', 600, 'Yapı Kredi'),
('Çalıkuşu', 'Reşat Nuri Güntekin', 400, 'İnkılap'),
('Suç ve Ceza', 'Dostoyevski', 700, 'Can Yayınları'),
('Simyacı', 'Paulo Coelho', 180, 'Can Yayınları'),
('Kürk Mantolu Madonna', 'Sabahattin Ali', 160, 'YKY');

-- Öğrenciler
INSERT INTO ogrenci (ogrenci_no, ad, soyad, bolum) VALUES 
('101', 'Ali', 'Yılmaz', 'Bilgisayar'),
('102', 'Ayşe', 'Demir', 'İşletme'),
('103', 'Mehmet', 'Kaya', 'Hukuk'),
('104', 'Fatma', 'Şahin', 'Tıp'),
('105', 'Can', 'Yıldız', 'Mimarlık');

-- Görevliler
INSERT INTO gorevli (ad, soyad, unvan) VALUES 
('Ahmet', 'Bey', 'Müdür'),
('Selma', 'Hanım', 'Kütüphaneci'),
('Murat', 'Can', 'Memur'),
('Ece', 'Su', 'Stajyer'),
('Hakan', 'Bak', 'Memur');

-- Ödünç Alma
INSERT INTO odunc_alma (kitap_id, ogrenci_id, gorevli_id, verilis_tarihi) VALUES 
(1, 1, 2, '2024-04-01'),
(2, 2, 2, '2024-04-02'),
(3, 3, 3, '2024-04-03'),
(4, 4, 3, '2024-04-04'),
(5, 5, 2, '2024-04-05');

-- İade
INSERT INTO iade (odunc_id, iade_tarihi, durum_notu) VALUES 
(1, '2024-04-15', 'Temiz'),
(2, '2024-04-16', 'Sayfa yırtık'),
(3, '2024-04-17', 'Zamanında geldi'),
(4, '2024-04-18', 'Temiz'),
(5, '2024-04-19', 'Zamanında geldi');

-- Ceza
INSERT INTO ceza (ogrenci_id, tutar, aciklama) VALUES 
(2, 50.00, 'Kitap hasarı'),
(1, 10.00, 'Gecikme'),
(3, 20.00, 'Gecikme'),
(4, 5.00, 'Gecikme'),
(5, 15.00, 'Gecikme');

-- ============================================================
-- ÖRNEK SORGULAR (DML - SELECT)
-- ============================================================

-- 1. Hangi öğrenci hangi kitabı aldı?
SELECT o.ad, k.kitap_ad 
FROM odunc_alma oa
JOIN ogrenci o ON oa.ogrenci_id = o.ogrenci_id
JOIN kitap k ON oa.kitap_id = k.kitap_id;

-- 2. Cezası 20 TL'den fazla olan öğrenciler
SELECT * FROM ceza WHERE tutar > 20;

-- 3. Kitap ismini güncelle (Update örneği)
UPDATE kitap SET kitap_ad = 'Nutuk (Özel Baskı)' WHERE kitap_id = 1;

-- 4. Bir cezayı sil (Delete örneği)
DELETE FROM ceza WHERE ceza_id = 5;

-- 5. Görevli unvanlarını listele
SELECT ad, unvan FROM gorevli;