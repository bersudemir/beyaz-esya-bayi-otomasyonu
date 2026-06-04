function ErrorMessage({ message, validationErrors }) {
  if (!message && !validationErrors) {
    return null
  }

  const entries = validationErrors ? Object.entries(validationErrors) : []
  const fieldLabels = {
    firstName: 'Ad',
    lastName: 'Soyad',
    phone: 'Telefon',
    email: 'E-posta',
    position: 'Pozisyon',
    salary: 'Maaş',
    categoryName: 'Kategori Adı',
    productId: 'Ürün',
    customerId: 'Müşteri',
    employeeId: 'Çalışan',
    quantity: 'Miktar',
    newQuantity: 'Yeni Miktar',
    newStock: 'Yeni Stok',
    saleStatus: 'Satış Durumu',
  }

  return (
    <div className="message-box error-message">
      {message && <p>{message}</p>}

      {entries.length > 0 && (
        <ul>
          {entries.map(([field, error]) => (
            <li key={field}>
              <strong>{fieldLabels[field] || field}:</strong> {error}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default ErrorMessage
