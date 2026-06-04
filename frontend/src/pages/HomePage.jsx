import { Link } from 'react-router-dom'

const quickLinks = [
  {
    path: '/customers',
    title: 'Müşteriler',
    description: 'Müşteri kayıtlarını listele, yeni kayıt ekle ve güncelle.',
  },
  {
    path: '/products',
    title: 'Ürünler',
    description: 'Ürünleri, kategori filtrelerini ve stok durumunu takip et.',
  },
  {
    path: '/sales/create',
    title: 'Satış Oluştur',
    description: 'Müşteri ve çalışan seçerek yeni satış süreci başlat.',
  },
  {
    path: '/sales/reports',
    title: 'Satış Raporları',
    description: 'Genel satış raporunu ve müşteri bazlı geçmişi incele.',
  },
]

function HomePage() {
  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Beyaz Eşya Bayi / Satış Otomasyonu</h1>
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
