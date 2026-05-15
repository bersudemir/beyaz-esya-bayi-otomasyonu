import { Link } from 'react-router-dom'

const quickLinks = [
  {
    path: '/customers',
    title: 'Musteriler',
    description: 'Musteri kayitlarini listele, yeni kayit ekle ve guncelle.',
  },
  {
    path: '/products',
    title: 'Urunler',
    description: 'Urunleri, kategori filtrelerini ve stok durumunu takip et.',
  },
  {
    path: '/sales/create',
    title: 'Satis Olustur',
    description: 'Musteri ve calisan secerek yeni satis sureci baslat.',
  },
  {
    path: '/sales/reports',
    title: 'Satis Raporlari',
    description: 'Genel satis raporunu ve musteri bazli gecmisi incele.',
  },
]

function HomePage() {
  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Beyaz Esya Bayi / Satis Otomasyonu</h1>
        <p>
          Spring Boot backend ile calisan, musteri, urun, stok ve satis
          islemlerini sade bir React arayuzu uzerinden yonetecek ogrenci
          projesi.
        </p>
      </div>

      <div className="quick-link-grid">
        {quickLinks.map((item) => (
          <Link className="quick-link-card" key={item.path} to={item.path}>
            <strong>{item.title}</strong>
            <span>{item.description}</span>
          </Link>
        ))}
      </div>
    </section>
  )
}

export default HomePage
