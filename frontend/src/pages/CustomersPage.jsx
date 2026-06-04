import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useCustomers } from '../viewmodels/useCustomers'

function CustomersPage() {
  const {
    customers,
    form,
    editingCustomerId,
    loading,
    saving,
    error,
    validationErrors,
    success,
    handleInputChange,
    handleSubmit,
    startEdit,
    resetForm,
  } = useCustomers()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Müşteriler</h1>
        <p>
          Müşteri kayıtlarını listeleyebilir, yeni müşteri ekleyebilir ve var
          olan müşterileri güncelleyebilirsiniz.
        </p>
      </div>

      <div className="content-grid">
        <form className="form-panel" onSubmit={handleSubmit}>
          <h2>{editingCustomerId ? 'Müşteri Güncelle' : 'Yeni Müşteri'}</h2>

          <label>
            Ad
            <input
              name="firstName"
              value={form.firstName}
              onChange={handleInputChange}
              placeholder="Ahmet"
            />
          </label>

          <label>
            Soyad
            <input
              name="lastName"
              value={form.lastName}
              onChange={handleInputChange}
              placeholder="Yılmaz"
            />
          </label>

          <label>
            Telefon
            <input
              name="phone"
              value={form.phone}
              onChange={handleInputChange}
              placeholder="05551234567"
            />
          </label>

          <label>
            E-posta
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={handleInputChange}
              placeholder="ahmet@example.com"
            />
          </label>

          <ErrorMessage message={error} validationErrors={validationErrors} />
          <SuccessMessage message={success} />

          <div className="button-row">
            <button className="primary-button" disabled={saving} type="submit">
              {saving ? 'Kaydediliyor...' : editingCustomerId ? 'Güncelle' : 'Ekle'}
            </button>

            {editingCustomerId && (
              <button className="secondary-button" type="button" onClick={resetForm}>
                Vazgeç
              </button>
            )}
          </div>
        </form>

        <div className="table-panel">
          <h2>Müşteri Listesi</h2>

          {loading && <LoadingMessage message="Müşteriler yükleniyor..." />}

          {!loading && customers.length === 0 && (
            <p className="muted-text">Kayıtlı müşteri bulunamadı.</p>
          )}

          {customers.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Ad Soyad</th>
                    <th>Telefon</th>
                    <th>E-posta</th>
                    <th>İşlem</th>
                  </tr>
                </thead>
                <tbody>
                  {customers.map((customer) => (
                    <tr key={customer.customerId}>
                      <td>{customer.customerId}</td>
                      <td>
                        {customer.firstName} {customer.lastName}
                      </td>
                      <td>{customer.phone}</td>
                      <td>{customer.email}</td>
                      <td>
                        <button
                          className="small-button"
                          type="button"
                          onClick={() => startEdit(customer)}
                        >
                          Düzenle
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </section>
  )
}

export default CustomersPage
