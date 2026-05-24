# Beyaz Eşya Bayi / Satış Otomasyonu

## Proje Özeti
- Bu proje, bir beyaz eşya bayisindeki müşteri, çalışan, ürün, kategori, stok ve satış süreçlerini yönetmek amacıyla geliştirilmiştir.
- Projenin temel odağı, SQL Server veritabanı üzerinde ilişkisel yapıların ve veritabanı nesnelerinin doğru şekilde kullanılmasıdır.
- Sistem; satış oluşturma, stok güncelleme, satış raporlama ve müşteri satış geçmişini görüntüleme işlemlerini destekler.

## Geliştirme Ortamı
- **Veritabanı:** Microsoft SQL Server
- **Backend:** Java 17, Spring Boot, Maven
- **Frontend:** React.js, Vite, JavaScript
- **IDE / Araçlar:** VS Code, SQL Server Management Studio, Git, GitHub

## Temel Özellikler
- Müşteri listeleme, ekleme ve güncelleme
- Çalışan listeleme, ekleme ve güncelleme
- Kategori listeleme ve ekleme
- Ürün listeleme ve kategoriye göre filtreleme
- Stok görünümü ve manuel stok güncelleme
- Yeni satış oluşturma
- Satışa ürün ekleme ve miktar güncelleme
- Satışı tamamlama veya iptal etme
- Genel satış raporu ve müşteri bazlı satış geçmişi

## Veritabanı Yapısı
- Projede 6 temel tablo bulunmaktadır:
  - `Customer`
  - `Employee`
  - `Category`
  - `Product`
  - `Sale`
  - `SaleDetail`
- Tablolar arasında müşteri-satış, çalışan-satış, kategori-ürün ve satış-satış detayı ilişkileri kurulmuştur.
- Primary Key, Foreign Key, Unique, Check ve Default constraint yapıları kullanılmıştır.

## Kullanılan Veritabanı Nesneleri
- **Index:** Sorgu performansını artırmak için kullanılmıştır.
- **View:**
  - `vw_ProductStock`
  - `vw_SaleReport`
- **Trigger:**
  - Stok düşme / stok iadesi
  - Satış toplam tutarının otomatik güncellenmesi
  - Satış durumu geçiş kontrolü
- **Stored Procedure:**
  - Satış oluşturma
  - Satışa ürün ekleme
  - Satış miktarı güncelleme
  - Satış durumu güncelleme
  - Müşteri satışlarını getirme
  - Manuel stok güncelleme

## Problem Tanımı
- Satış işlemlerinde stok miktarının doğru yönetilmesi önemlidir.
- İptal edilen satışlarda stokların geri eklenmesi gerekir.
- Satış toplam tutarlarının otomatik hesaplanması hata riskini azaltır.
- Bu proje, bu süreçleri veritabanı iş kurallarıyla güvenli hale getirmek için geliştirilmiştir.

## Yazılım Mimarisi
### Backend
```text
Controller -> Service -> Repository -> Database
```

### Frontend
```text
Page / View -> ViewModel Hook -> Service / API -> Backend
```

## Akış Şeması
Aşağıdaki akış, temel satış işlemini göstermektedir:

```text
Müşteri ve çalışan seçilir
        ↓
Satış oluşturulur
        ↓
Ürün satışa eklenir
        ↓
Stok yeterliyse stok düşer
        ↓
Toplam tutar güncellenir
        ↓
Satış Completed veya Cancelled yapılır
```

## Arayüz Görselleri
> Bu alana geliştirilen arayüzden örnek ekran görüntüleri eklenecektir.

## Projenin Kurulumu ve Çalıştırılması

### 1. Veritabanını Kurma
- `database/45_sql_betikleri.sql` dosyası SQL Server Management Studio üzerinde çalıştırılır.
- Veritabanı, tablolar, view’lar, trigger’lar, stored procedure’ler ve dummy data oluşturulur.

### 2. Backend’i Çalıştırma
```bash
cd backend
mvn spring-boot:run
```

- Backend varsayılan olarak şu adreste çalışır:
```text
http://localhost:8080
```

### 3. Frontend’i Çalıştırma
```bash
cd frontend
npm install
npm run dev
```

- Frontend varsayılan olarak şu adreste çalışır:
```text
http://localhost:5173
```

## Genel Yapı
- Veritabanı, iş kurallarının merkezinde yer almaktadır.
- Backend, SQL Server üzerindeki yapıların güvenli şekilde kullanılmasını sağlar.
- Frontend, kullanıcıların satış ve stok işlemlerini kolayca yönetebilmesi için sade bir arayüz sunar.

## Yapılan Araştırmalar
- SQL Server trigger ve stored procedure kullanımı
- Spring Boot ile SQL Server bağlantısı
- React.js üzerinde component, hook ve service yapısı
- Frontend-backend API iletişimi
- Veritabanı tabanlı stok ve satış kontrolü

## Referanslar
- Microsoft SQL Server Documentation
- Spring Boot Documentation
- React Documentation
- Vite Documentation

## Projeyi Çalıştırma

1. Repo indirilir:
```bash
git clone https://github.com/bersudemir/beyaz-esya-bayi-otomasyonu.git
cd beyaz-esya-bayi-otomasyonu
```

2. `database/SQL_folder.sql` dosyası SSMS üzerinde çalıştırılır.

3. `backend/src/main/resources/application.properties` içindeki veritabanı kullanıcı adı ve şifre bilgileri düzenlenir.

4. Backend çalıştırılır:
```bash
cd backend
mvn spring-boot:run
```

5. Yeni terminalde frontend çalıştırılır:
```bash
cd frontend
npm install
npm run dev
```

6. Uygulama tarayıcıdan açılır:
```text
http://localhost:5173
```