import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useSales } from '../viewmodels/useSales'

const saleStatusLabels = {
  Pending: 'Beklemede',
  Completed: 'Tamamlandı',
  Cancelled: 'İptal Edildi',
}

function formatSaleStatus(status) {
  return saleStatusLabels[status] || status
}

function CreateSalePage() {
  const {
    customers,
    employees,
    products,
    saleForm,
    detailForm,
    quantityForm,
    currentSale,
    saleItems,
    loading,
    processing,
    error,
    validationErrors,
    success,
    handleSaleFormChange,
    handleDetailFormChange,
    handleQuantityFormChange,
    handleCreateSale,
    handleAddDetail,
    handleUpdateQuantity,
    handleStatusUpdate,
    selectItemForUpdate,
  } = useSales()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Satış Oluştur</h1>
        <p>
          Önce müşteri ve çalışan seçerek satış kaydı oluşturun. Ardından ürün
          ekleyebilir, miktar güncelleyebilir ve satışı tamamlayabilir ya da
          iptal edebilirsiniz.
        </p>
      </div>

      {loading && <LoadingMessage message="Seçenekler yükleniyor..." />}
      <ErrorMessage message={error} validationErrors={validationErrors} />
      <SuccessMessage message={success} />

      {currentSale && (
        <div className="sale-summary">
          <div>
            <span>Satış ID</span>
            <strong>{currentSale.saleId}</strong>
          </div>
          <div>
            <span>Müşteri</span>
            <strong>{currentSale.customerName}</strong>
          </div>
          <div>
            <span>Çalışan</span>
            <strong>{currentSale.employeeName}</strong>
          </div>
          <div>
            <span>Toplam</span>
            <strong>{currentSale.totalAmount}</strong>
          </div>
          <div>
            <span>Durum</span>
            <strong>{formatSaleStatus(currentSale.saleStatus)}</strong>
          </div>
        </div>
      )}

      <div className="content-grid">
        <div className="form-panel">
          <form onSubmit={handleCreateSale}>
            <h2>Satış Bilgileri</h2>

            <label>
              Müşteri
              <select
                name="customerId"
                value={saleForm.customerId}
                onChange={handleSaleFormChange}
                disabled={Boolean(currentSale)}
              >
                <option value="">Müşteri seçiniz</option>
                {customers.map((customer) => (
                  <option key={customer.customerId} value={customer.customerId}>
                    {customer.firstName} {customer.lastName}
                  </option>
                ))}
              </select>
            </label>

            <label>
              Çalışan
              <select
                name="employeeId"
                value={saleForm.employeeId}
                onChange={handleSaleFormChange}
                disabled={Boolean(currentSale)}
              >
                <option value="">Çalışan seçiniz</option>
                {employees.map((employee) => (
                  <option key={employee.employeeId} value={employee.employeeId}>
                    {employee.firstName} {employee.lastName}
                  </option>
                ))}
              </select>
            </label>

            <div className="button-row">
              <button
                className="primary-button"
                disabled={processing || Boolean(currentSale)}
                type="submit"
              >
                {processing ? 'İşleniyor...' : 'Satış Oluştur'}
              </button>
            </div>
          </form>

          <form className="sub-form" onSubmit={handleAddDetail}>
            <h2>Ürün Ekle</h2>

            <label>
              Ürün
              <select
                name="productId"
                value={detailForm.productId}
                onChange={handleDetailFormChange}
                disabled={!currentSale}
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
              Miktar
              <input
                name="quantity"
                type="number"
                min="1"
                value={detailForm.quantity}
                onChange={handleDetailFormChange}
                disabled={!currentSale}
              />
            </label>

            <div className="button-row">
              <button
                className="primary-button"
                disabled={processing || !currentSale || !detailForm.productId}
                type="submit"
              >
                Ürün Ekle
              </button>
            </div>
          </form>
        </div>

        <div className="table-panel">
          <h2>Satış Ürünleri</h2>

          {saleItems.length === 0 && (
            <p className="muted-text">Bu satış için henüz ürün eklenmedi.</p>
          )}

          {saleItems.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>Ürün ID</th>
                    <th>Ürün</th>
                    <th>Marka</th>
                    <th>Miktar</th>
                    <th>İşlem</th>
                  </tr>
                </thead>
                <tbody>
                  {saleItems.map((item) => (
                    <tr key={item.productId}>
                      <td>{item.productId}</td>
                      <td>{item.productName}</td>
                      <td>{item.brand}</td>
                      <td>{item.quantity}</td>
                      <td>
                        <button
                          className="small-button"
                          type="button"
                          onClick={() => selectItemForUpdate(item)}
                        >
                          Miktar Seç
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          <form className="inline-form" onSubmit={handleUpdateQuantity}>
            <label>
              Güncellenecek Ürün
              <select
                name="productId"
                value={quantityForm.productId}
                onChange={handleQuantityFormChange}
                disabled={!currentSale}
              >
                <option value="">Ürün seçiniz</option>
                {saleItems.map((item) => (
                  <option key={item.productId} value={item.productId}>
                    {item.productName}
                  </option>
                ))}
              </select>
            </label>

            <label>
              Yeni Miktar
              <input
                name="newQuantity"
                type="number"
                min="1"
                value={quantityForm.newQuantity}
                onChange={handleQuantityFormChange}
                disabled={!currentSale}
              />
            </label>

            <button
              className="secondary-button"
              disabled={processing || !currentSale || !quantityForm.productId}
              type="submit"
            >
              Miktar Güncelle
            </button>
          </form>

          <div className="button-row">
            <button
              className="primary-button"
              disabled={processing || !currentSale}
              type="button"
              onClick={() => handleStatusUpdate('Completed')}
            >
              Satışı Tamamla
            </button>
            <button
              className="secondary-button"
              disabled={processing || !currentSale}
              type="button"
              onClick={() => handleStatusUpdate('Cancelled')}
            >
              Satışı İptal Et
            </button>
          </div>
        </div>
      </div>
    </section>
  )
}

export default CreateSalePage
