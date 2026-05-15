import { NavLink, Outlet } from 'react-router-dom'

const menuItems = [
  { path: '/', label: 'Ana Sayfa' },
  { path: '/customers', label: 'Musteriler' },
  { path: '/employees', label: 'Calisanlar' },
  { path: '/categories', label: 'Kategoriler' },
  { path: '/products', label: 'Urunler' },
  { path: '/sales/create', label: 'Satis Olustur' },
  { path: '/sales/reports', label: 'Satis Raporlari' },
]

function AppLayout() {
  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="brand">
          <span className="brand-mark">BE</span>
          <div>
            <strong>Beyaz Esya Bayi</strong>
            <span>Satis Otomasyonu</span>
          </div>
        </div>

        <nav className="main-nav" aria-label="Ana menu">
          {menuItems.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) => (isActive ? 'nav-link active' : 'nav-link')}
              end={item.path === '/'}
            >
              {item.label}
            </NavLink>
          ))}
        </nav>
      </header>

      <main className="main-content">
        <Outlet />
      </main>
    </div>
  )
}

export default AppLayout
