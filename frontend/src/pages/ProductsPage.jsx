import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useProducts } from '../viewmodels/useProducts'

function ProductsPage() {
  const {
    products,
    stockRows,
    categories,
    selectedCategoryId,
    stockForm,
    loading,
    stockLoading,
    saving,
    error,
    validationErrors,
    success,
    handleCategoryChange,
    handleStockInputChange,
    selectProductForStock,
    handleStockSubmit,
  } = useProducts()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Ürünler</h1>
        <p>
          Ürünleri listeleyebilir, kategoriye göre filtreleyebilir, stok
          görünümünü inceleyebilir ve manuel stok güncellemesi yapabilirsiniz.
        </p>
      </div>

      <ErrorMessage message={error} validationErrors={validationErrors} />
      <SuccessMessage message={success} />

      <div className="content-grid">
        <form className="form-panel" onSubmit={handleStockSubmit}>
          <h2>Stok Güncelle</h2>

          <label>
            Ürün
            <select
              name="productId"
              value={stockForm.productId}
              onChange={handleStockInputChange}
            >
              <option value="">Ürün seçiniz</option>
              {products.map((product) => (
                <option key={product.productId} value={product.productId}>
                  {product.productName} - {product.brand}
                </option>
              ))}
            </select>
          </label>

          <label>
            Yeni Stok
            <input
              name="newStock"
              type="number"
              min="0"
              value={stockForm.newStock}
              onChange={handleStockInputChange}
              placeholder="20"
            />
          </label>

          <div className="button-row">
            <button
              className="primary-button"
              disabled={saving || !stockForm.productId}
              type="submit"
            >
              {saving ? 'Güncelleniyor...' : 'Stok Güncelle'}
            </button>
          </div>
        </form>

        <div className="table-panel">
          <div className="panel-header">
            <h2>Ürün Listesi</h2>
            <label className="compact-filter">
              Kategori
              <select value={selectedCategoryId} onChange={handleCategoryChange}>
                <option value="">Tüm kategoriler</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryId}>
                    {category.categoryName}
                  </option>
                ))}
              </select>
            </label>
          </div>

          {loading && <LoadingMessage message="Ürünler yükleniyor..." />}

          {!loading && products.length === 0 && (
            <p className="muted-text">Ürün bulunamadı.</p>
          )}

          {products.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Ürün</th>
                    <th>Marka</th>
                    <th>Kategori</th>
                    <th>Fiyat</th>
                    <th>Stok</th>
                    <th>İşlem</th>
                  </tr>
                </thead>
                <tbody>
                  {products.map((product) => (
                    <tr key={product.productId}>
                      <td>{product.productId}</td>
                      <td>{product.productName}</td>
                      <td>{product.brand}</td>
                      <td>{product.categoryName}</td>
                      <td>{product.price}</td>
                      <td>{product.stockQuantity}</td>
                      <td>
                        <button
                          className="small-button"
                          type="button"
                          onClick={() => selectProductForStock(product)}
                        >
                          Stok Seç
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

      <div className="table-panel separated-panel">
        <h2>Stok Görünümü</h2>

        {stockLoading && <LoadingMessage message="Stok görünümü yükleniyor..." />}

        {!stockLoading && stockRows.length === 0 && (
          <p className="muted-text">Stok görünümü için kayıt bulunamadı.</p>
        )}

        {stockRows.length > 0 && (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Ürün</th>
                  <th>Marka</th>
                  <th>Kategori</th>
                  <th>Fiyat</th>
                  <th>Stok</th>
                </tr>
              </thead>
              <tbody>
                {stockRows.map((row) => (
                  <tr key={row.productId}>
                    <td>{row.productId}</td>
                    <td>{row.productName}</td>
                    <td>{row.brand}</td>
                    <td>{row.categoryName}</td>
                    <td>{row.price}</td>
                    <td>{row.stockQuantity}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </section>
  )
}

export default ProductsPage
