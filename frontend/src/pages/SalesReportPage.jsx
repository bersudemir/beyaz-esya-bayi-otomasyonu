import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import { useSalesReport } from '../viewmodels/useSalesReport'

const saleStatusLabels = {
  Pending: 'Beklemede',
  Completed: 'Tamamlandı',
  Cancelled: 'İptal Edildi',
}

function formatSaleStatus(status) {
  return saleStatusLabels[status] || status
}

function SalesReportTable({ rows, emptyMessage }) {
  if (rows.length === 0) {
    return <p className="muted-text">{emptyMessage}</p>
  }

  return (
    <div className="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Satış ID</th>
            <th>Tarih</th>
            <th>Durum</th>
            <th>Toplam</th>
            <th>Müşteri</th>
            <th>Çalışan</th>
            <th>Ürün</th>
            <th>Marka</th>
            <th>Miktar</th>
            <th>Birim Fiyat</th>
            <th>Satır Toplam</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((row, index) => (
            <tr key={`${row.saleId}-${row.productId}-${index}`}>
              <td>{row.saleId}</td>
              <td>{row.saleDate}</td>
              <td>{formatSaleStatus(row.saleStatus)}</td>
              <td>{row.totalAmount}</td>
              <td>{row.customerName}</td>
              <td>{row.employeeName}</td>
              <td>{row.productName}</td>
              <td>{row.brand}</td>
              <td>{row.quantity}</td>
              <td>{row.unitPrice}</td>
              <td>{row.lineTotal}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

function SalesReportPage() {
  const {
    reportRows,
    customerRows,
    customerId,
    loading,
    customerLoading,
    error,
    validationErrors,
    handleCustomerIdChange,
    handleCustomerSearch,
  } = useSalesReport()

  return (
    <section className="page-section">
      <div className="page-heading">
        <h1>Satış Raporları</h1>
        <p>
          Genel satış raporunu listeleyebilir ve müşteri ID girerek müşteri
          bazlı satış geçmişini sorgulayabilirsiniz.
        </p>
      </div>

      <ErrorMessage message={error} validationErrors={validationErrors} />

      <form className="report-filter" onSubmit={handleCustomerSearch}>
        <label>
          Müşteri ID
          <input
            type="number"
            min="1"
            value={customerId}
            onChange={handleCustomerIdChange}
            placeholder="1"
          />
        </label>
        <button
          className="primary-button"
          disabled={customerLoading || !customerId}
          type="submit"
        >
          {customerLoading ? 'Sorgulanıyor...' : 'Müşteri Satışlarını Getir'}
        </button>
      </form>

      <div className="table-panel separated-panel">
        <h2>Müşteri Satışları</h2>
        {customerLoading && <LoadingMessage message="Müşteri satışları yükleniyor..." />}
        {!customerLoading && (
          <SalesReportTable
            rows={customerRows}
            emptyMessage="Müşteri sorgusu yapılmadı veya kayıt bulunamadı."
          />
        )}
      </div>

      <div className="table-panel separated-panel">
        <h2>Genel Satış Raporu</h2>
        {loading && <LoadingMessage message="Satış raporu yükleniyor..." />}
        {!loading && (
          <SalesReportTable
            rows={reportRows}
            emptyMessage="Satış raporu için kayıt bulunamadı."
          />
        )}
      </div>
    </section>
  )
}

export default SalesReportPage
