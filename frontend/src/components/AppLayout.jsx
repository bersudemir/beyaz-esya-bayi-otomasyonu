import { NavLink, Outlet } from 'react-router-dom'

const menuItems = [
  { path: '/', label: 'Ana Sayfa' },
  { path: '/customers', label: 'Müşteriler' },
  { path: '/employees', label: 'Çalışanlar' },
  { path: '/categories', label: 'Kategoriler' },
  { path: '/products', label: 'Ürünler' },
  { path: '/sales/create', label: 'Satış Oluştur' },
  { path: '/sales/reports', label: 'Satış Raporları' },
]

function AppLayout() {
  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="brand">
          <span className="brand-mark">BE</span>
          <div>
            <strong>Beyaz Eşya Bayi</strong>
            <span>Satış Otomasyonu</span>
          </div>
        </div>

        <nav className="main-nav" aria-label="Ana menü">
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
