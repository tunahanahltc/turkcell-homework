# Banking Application - Basit Banka Simülasyonu

Temel Java ile yazılmış, RAM'de veri saklayan basit banka uygulaması.

## 🎯 Özellikler

1. **Müşteri Kaydı** - Yeni müşteri ekleme
2. **Hesap Özeti** - Müşteri bakiyesi görme
3. **Para Yatırma** - Hesaba para ekleme
4. **Para Çekme** - Hesaptan para çıkarma
5. **Para Transferi** - Müşteriler arası para gönderme
6. **İşlem Geçmişi** - Tüm işlemleri listeme
7. **Veritabanı Görüntüleme** - Tüm müşterileri görme

## 🚀 Çalıştırma

```bash
cd src
javac BankingApplication.java
java BankingApplication
```

## 👥 Test Müşterileri

- **C001** - Ahmet Yılmaz (5000 TL)
- **C002** - Fatma Kaya (3000 TL)
- **C003** - Mehmet Demir (7500 TL)

## 📁 Yapı

- `src/BankingApplication.java` - Tek dosya içinde her şey
  - Main menü ve işlemler
  - Customer sınıfı
  - HashMap ile veri saklama (In-Memory)

## 💾 Veri Saklama

Veriler RAM'de (ArrayList ve HashMap) tutulur, program kapatılınca silinir.
