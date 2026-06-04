import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useCategories } from '../viewmodels/useCategories'

function CategoriesPage() {
  const {
    categories,
    form,
    loading,
    saving,
    error,
    validationErrors,
    success,
    handleInputChange,
    handleSubmit,
  } = useCategories()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Kategoriler</h1>
        <p>
          Ürünleri gruplamak için kategori kayıtlarını listeleyebilir ve yeni
          kategori ekleyebilirsiniz.
        </p>
      </div>

      <div className="content-grid">
        <form className="form-panel" onSubmit={handleSubmit}>
          <h2>Yeni Kategori</h2>

          <label>
            Kategori Adı
            <input
              name="categoryName"
              value={form.categoryName}
              onChange={handleInputChange}
              placeholder="Klima"
            />
          </label>

          <ErrorMessage message={error} validationErrors={validationErrors} />
          <SuccessMessage message={success} />

          <div className="button-row">
            <button className="primary-button" disabled={saving} type="submit">
              {saving ? 'Kaydediliyor...' : 'Ekle'}
            </button>
          </div>
        </form>

        <div className="table-panel">
          <h2>Kategori Listesi</h2>

          {loading && <LoadingMessage message="Kategoriler yükleniyor..." />}

          {!loading && categories.length === 0 && (
            <p className="muted-text">Kayıtlı kategori bulunamadı.</p>
          )}

          {categories.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Kategori Adı</th>
                  </tr>
                </thead>
                <tbody>
                  {categories.map((category) => (
                    <tr key={category.categoryId}>
                      <td>{category.categoryId}</td>
                      <td>{category.categoryName}</td>
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

export default CategoriesPage
