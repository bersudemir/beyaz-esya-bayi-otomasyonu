import { useEffect, useState } from 'react'
import { getCustomers } from '../services/customerService'
import { getEmployees } from '../services/employeeService'
import { getProducts } from '../services/productService'
import {
  addSaleDetail,
  createSale,
  updateSaleDetailQuantity,
  updateSaleStatus,
} from '../services/saleService'

const emptySaleForm = {
  customerId: '',
  employeeId: '',
}

const emptyDetailForm = {
  productId: '',
  quantity: 1,
}

const emptyQuantityForm = {
  productId: '',
  newQuantity: 1,
}

export function useSales() {
  const [customers, setCustomers] = useState([])
  const [employees, setEmployees] = useState([])
  const [products, setProducts] = useState([])
  const [saleForm, setSaleForm] = useState(emptySaleForm)
  const [detailForm, setDetailForm] = useState(emptyDetailForm)
  const [quantityForm, setQuantityForm] = useState(emptyQuantityForm)
  const [currentSale, setCurrentSale] = useState(null)
  const [saleItems, setSaleItems] = useState([])
  const [loading, setLoading] = useState(false)
  const [processing, setProcessing] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)
  const [success, setSuccess] = useState('')

  async function loadOptions() {
    setLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const [customerData, employeeData, productData] = await Promise.all([
        getCustomers(),
        getEmployees(),
        getProducts(),
      ])

      setCustomers(customerData || [])
      setEmployees(employeeData || [])
      setProducts(productData || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadOptions, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  function handleSaleFormChange(event) {
    const { name, value } = event.target
    setSaleForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function handleDetailFormChange(event) {
    const { name, value } = event.target
    setDetailForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function handleQuantityFormChange(event) {
    const { name, value } = event.target
    setQuantityForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function selectItemForUpdate(item) {
    setQuantityForm({
      productId: item.productId,
      newQuantity: item.quantity,
    })
    setError('')
    setValidationErrors(null)
    setSuccess('')
  }

  async function handleCreateSale(event) {
    event.preventDefault()
    setProcessing(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      const sale = await createSale({
        customerId: Number(saleForm.customerId),
        employeeId: Number(saleForm.employeeId),
      })

      setCurrentSale(sale)
      setSaleItems([])
      setSuccess(`Satis olusturuldu. Satis ID: ${sale.saleId}`)
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setProcessing(false)
    }
  }

  async function handleAddDetail(event) {
    event.preventDefault()

    if (!currentSale) {
      setError('Once satis olusturmalisiniz.')
      return
    }

    setProcessing(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      const payload = {
        productId: Number(detailForm.productId),
        quantity: Number(detailForm.quantity),
      }
      const sale = await addSaleDetail(currentSale.saleId, payload)
      const product = products.find((item) => item.productId === payload.productId)

      setCurrentSale(sale)
      setSaleItems((currentItems) => [
        ...currentItems.filter((item) => item.productId !== payload.productId),
        {
          productId: payload.productId,
          productName: product?.productName || `Urun #${payload.productId}`,
          brand: product?.brand || '',
          quantity: payload.quantity,
        },
      ])
      setDetailForm(emptyDetailForm)
      setSuccess('Urun satisa eklendi.')
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setProcessing(false)
    }
  }

  async function handleUpdateQuantity(event) {
    event.preventDefault()

    if (!currentSale) {
      setError('Once satis olusturmalisiniz.')
      return
    }

    setProcessing(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      const productId = Number(quantityForm.productId)
      const newQuantity = Number(quantityForm.newQuantity)
      const sale = await updateSaleDetailQuantity(currentSale.saleId, productId, {
        newQuantity,
      })

      setCurrentSale(sale)
      setSaleItems((currentItems) =>
        currentItems.map((item) =>
          item.productId === productId ? { ...item, quantity: newQuantity } : item,
        ),
      )
      setQuantityForm(emptyQuantityForm)
      setSuccess('Urun miktari guncellendi.')
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setProcessing(false)
    }
  }

  async function handleStatusUpdate(saleStatus) {
    if (!currentSale) {
      setError('Once satis olusturmalisiniz.')
      return
    }

    setProcessing(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      const sale = await updateSaleStatus(currentSale.saleId, { saleStatus })
      setCurrentSale(sale)
      setSuccess(`Satis durumu ${saleStatus} olarak guncellendi.`)
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setProcessing(false)
    }
  }

  return {
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
  }
}
