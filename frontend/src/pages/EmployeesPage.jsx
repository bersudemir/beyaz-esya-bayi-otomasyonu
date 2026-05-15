import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useEmployees } from '../viewmodels/useEmployees'

function EmployeesPage() {
  const {
    employees,
    form,
    editingEmployeeId,
    loading,
    saving,
    error,
    validationErrors,
    success,
    handleInputChange,
    handleSubmit,
    startEdit,
    resetForm,
  } = useEmployees()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Calisanlar</h1>
        <p>
          Satis surecinde gorev alan calisanlari listeleyebilir, yeni calisan
          ekleyebilir ve mevcut kayitlari guncelleyebilirsiniz.
        </p>
      </div>

      <div className="content-grid">
        <form className="form-panel" onSubmit={handleSubmit}>
          <h2>{editingEmployeeId ? 'Calisan Guncelle' : 'Yeni Calisan'}</h2>

          <label>
            Ad
            <input
              name="firstName"
              value={form.firstName}
              onChange={handleInputChange}
              placeholder="Ayse"
            />
          </label>

          <label>
            Soyad
            <input
              name="lastName"
              value={form.lastName}
              onChange={handleInputChange}
              placeholder="Kaya"
            />
          </label>

          <label>
            Telefon
            <input
              name="phone"
              value={form.phone}
              onChange={handleInputChange}
              placeholder="05559876543"
            />
          </label>

          <label>
            Pozisyon
            <input
              name="position"
              value={form.position}
              onChange={handleInputChange}
              placeholder="Sales Representative"
            />
          </label>

          <label>
            Maas
            <input
              name="salary"
              type="number"
              min="0"
              step="0.01"
              value={form.salary}
              onChange={handleInputChange}
              placeholder="25000"
            />
          </label>

          <ErrorMessage message={error} validationErrors={validationErrors} />
          <SuccessMessage message={success} />

          <div className="button-row">
            <button className="primary-button" disabled={saving} type="submit">
              {saving ? 'Kaydediliyor...' : editingEmployeeId ? 'Guncelle' : 'Ekle'}
            </button>

            {editingEmployeeId && (
              <button className="secondary-button" type="button" onClick={resetForm}>
                Vazgec
              </button>
            )}
          </div>
        </form>

        <div className="table-panel">
          <h2>Calisan Listesi</h2>

          {loading && <LoadingMessage message="Calisanlar yukleniyor..." />}

          {!loading && employees.length === 0 && (
            <p className="muted-text">Kayitli calisan bulunamadi.</p>
          )}

          {employees.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Ad Soyad</th>
                    <th>Telefon</th>
                    <th>Pozisyon</th>
                    <th>Maas</th>
                    <th>Islem</th>
                  </tr>
                </thead>
                <tbody>
                  {employees.map((employee) => (
                    <tr key={employee.employeeId}>
                      <td>{employee.employeeId}</td>
                      <td>
                        {employee.firstName} {employee.lastName}
                      </td>
                      <td>{employee.phone}</td>
                      <td>{employee.position}</td>
                      <td>{employee.salary}</td>
                      <td>
                        <button
                          className="small-button"
                          type="button"
                          onClick={() => startEdit(employee)}
                        >
                          Duzenle
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

export default EmployeesPage
