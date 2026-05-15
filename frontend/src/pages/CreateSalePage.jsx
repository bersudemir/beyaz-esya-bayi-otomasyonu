import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import SuccessMessage from '../components/SuccessMessage'
import { useSales } from '../viewmodels/useSales'

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
        <h1>Satis Olustur</h1>
        <p>
          Once musteri ve calisan secerek satis kaydi olusturun. Ardindan urun
          ekleyebilir, miktar guncelleyebilir ve satisi tamamlayabilir ya da
          iptal edebilirsiniz.
        </p>
      </div>

      {loading && <LoadingMessage message="Secenekler yukleniyor..." />}
      <ErrorMessage message={error} validationErrors={validationErrors} />
      <SuccessMessage message={success} />

      {currentSale && (
        <div className="sale-summary">
          <div>
            <span>Satis ID</span>
            <strong>{currentSale.saleId}</strong>
          </div>
          <div>
            <span>Musteri</span>
            <strong>{currentSale.customerName}</strong>
          </div>
          <div>
            <span>Calisan</span>
            <strong>{currentSale.employeeName}</strong>
          </div>
          <div>
            <span>Toplam</span>
            <strong>{currentSale.totalAmount}</strong>
          </div>
          <div>
            <span>Durum</span>
            <strong>{currentSale.saleStatus}</strong>
          </div>
        </div>
      )}

      <div className="content-grid">
        <div className="form-panel">
          <form onSubmit={handleCreateSale}>
            <h2>Satis Bilgileri</h2>

            <label>
              Musteri
              <select
                name="customerId"
                value={saleForm.customerId}
                onChange={handleSaleFormChange}
                disabled={Boolean(currentSale)}
              >
                <option value="">Musteri seciniz</option>
                {customers.map((customer) => (
                  <option key={customer.customerId} value={customer.customerId}>
                    {customer.firstName} {customer.lastName}
                  </option>
                ))}
              </select>
            </label>

            <label>
              Calisan
              <select
                name="employeeId"
                value={saleForm.employeeId}
                onChange={handleSaleFormChange}
                disabled={Boolean(currentSale)}
              >
                <option value="">Calisan seciniz</option>
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
                {processing ? 'Isleniyor...' : 'Satis Olustur'}
              </button>
            </div>
          </form>

          <form className="sub-form" onSubmit={handleAddDetail}>
            <h2>Urun Ekle</h2>

            <label>
              Urun
              <select
                name="productId"
                value={detailForm.productId}
                onChange={handleDetailFormChange}
                disabled={!currentSale}
              >
                <option value="">Urun seciniz</option>
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
                Urun Ekle
              </button>
            </div>
          </form>
        </div>

        <div className="table-panel">
          <h2>Satis Urunleri</h2>

          {saleItems.length === 0 && (
            <p className="muted-text">Bu satis icin henuz urun eklenmedi.</p>
          )}

          {saleItems.length > 0 && (
            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>Urun ID</th>
                    <th>Urun</th>
                    <th>Marka</th>
                    <th>Miktar</th>
                    <th>Islem</th>
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
                          Miktar Sec
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
              Guncellenecek Urun
              <select
                name="productId"
                value={quantityForm.productId}
                onChange={handleQuantityFormChange}
                disabled={!currentSale}
              >
                <option value="">Urun seciniz</option>
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
              Miktar Guncelle
            </button>
          </form>

          <div className="button-row">
            <button
              className="primary-button"
              disabled={processing || !currentSale}
              type="button"
              onClick={() => handleStatusUpdate('Completed')}
            >
              Satisi Tamamla
            </button>
            <button
              className="secondary-button"
              disabled={processing || !currentSale}
              type="button"
              onClick={() => handleStatusUpdate('Cancelled')}
            >
              Satisi Iptal Et
            </button>
          </div>
        </div>
      </div>
    </section>
  )
}

export default CreateSalePage
