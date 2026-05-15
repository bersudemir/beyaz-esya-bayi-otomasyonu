import ErrorMessage from '../components/ErrorMessage'
import LoadingMessage from '../components/LoadingMessage'
import { useSalesReport } from '../viewmodels/useSalesReport'

function SalesReportTable({ rows, emptyMessage }) {
  if (rows.length === 0) {
    return <p className="muted-text">{emptyMessage}</p>
  }

  return (
    <div className="table-wrapper">
      <table>
        <thead>
          <tr>
            <th>Satis ID</th>
            <th>Tarih</th>
            <th>Durum</th>
            <th>Toplam</th>
            <th>Musteri</th>
            <th>Calisan</th>
            <th>Urun</th>
            <th>Marka</th>
            <th>Miktar</th>
            <th>Birim Fiyat</th>
            <th>Satir Toplam</th>
          </tr>
        </thead>
        <tbody>
          {rows.map((row, index) => (
            <tr key={`${row.saleId}-${row.productId}-${index}`}>
              <td>{row.saleId}</td>
              <td>{row.saleDate}</td>
              <td>{row.saleStatus}</td>
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
        <h1>Satis Raporlari</h1>
        <p>
          Genel satis raporunu listeleyebilir ve musteri ID girerek musteri
          bazli satis gecmisini sorgulayabilirsiniz.
        </p>
      </div>

      <ErrorMessage message={error} validationErrors={validationErrors} />

      <form className="report-filter" onSubmit={handleCustomerSearch}>
        <label>
          Musteri ID
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
          {customerLoading ? 'Sorgulaniyor...' : 'Musteri Satislarini Getir'}
        </button>
      </form>

      <div className="table-panel separated-panel">
        <h2>Musteri Satislari</h2>
        {customerLoading && <LoadingMessage message="Musteri satislari yukleniyor..." />}
        {!customerLoading && (
          <SalesReportTable
            rows={customerRows}
            emptyMessage="Musteri sorgusu yapilmadi veya kayit bulunamadi."
          />
        )}
      </div>

      <div className="table-panel separated-panel">
        <h2>Genel Satis Raporu</h2>
        {loading && <LoadingMessage message="Satis raporu yukleniyor..." />}
        {!loading && (
          <SalesReportTable
            rows={reportRows}
            emptyMessage="Satis raporu icin kayit bulunamadi."
          />
        )}
      </div>
    </section>
  )
}

export default SalesReportPage
