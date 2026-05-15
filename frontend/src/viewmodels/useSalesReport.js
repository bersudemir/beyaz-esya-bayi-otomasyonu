import { useEffect, useState } from 'react'
import { getCustomerSales, getSaleReport } from '../services/saleService'

export function useSalesReport() {
  const [reportRows, setReportRows] = useState([])
  const [customerRows, setCustomerRows] = useState([])
  const [customerId, setCustomerId] = useState('')
  const [loading, setLoading] = useState(false)
  const [customerLoading, setCustomerLoading] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)

  async function loadSaleReport() {
    setLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const data = await getSaleReport()
      setReportRows(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadSaleReport, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  function handleCustomerIdChange(event) {
    setCustomerId(event.target.value)
  }

  async function handleCustomerSearch(event) {
    event.preventDefault()
    setCustomerLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const data = await getCustomerSales(customerId)
      setCustomerRows(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setCustomerLoading(false)
    }
  }

  return {
    reportRows,
    customerRows,
    customerId,
    loading,
    customerLoading,
    error,
    validationErrors,
    handleCustomerIdChange,
    handleCustomerSearch,
    loadSaleReport,
  }
}
