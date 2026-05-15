import { useEffect, useState } from 'react'
import { getCategories } from '../services/categoryService'
import {
  getProducts,
  getProductsByCategory,
  getProductStockView,
  updateProductStock,
} from '../services/productService'

const emptyStockForm = {
  productId: '',
  newStock: '',
}

export function useProducts() {
  const [products, setProducts] = useState([])
  const [stockRows, setStockRows] = useState([])
  const [categories, setCategories] = useState([])
  const [selectedCategoryId, setSelectedCategoryId] = useState('')
  const [stockForm, setStockForm] = useState(emptyStockForm)
  const [loading, setLoading] = useState(false)
  const [stockLoading, setStockLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)
  const [success, setSuccess] = useState('')

  async function loadInitialData() {
    setLoading(true)
    setStockLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const [categoryData, productData, stockData] = await Promise.all([
        getCategories(),
        getProducts(),
        getProductStockView(),
      ])

      setCategories(categoryData || [])
      setProducts(productData || [])
      setStockRows(stockData || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
      setStockLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadInitialData, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  async function handleCategoryChange(event) {
    const categoryId = event.target.value
    setSelectedCategoryId(categoryId)
    setLoading(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      const data = categoryId ? await getProductsByCategory(categoryId) : await getProducts()
      setProducts(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  function handleStockInputChange(event) {
    const { name, value } = event.target
    setStockForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function selectProductForStock(product) {
    setStockForm({
      productId: product.productId,
      newStock: product.stockQuantity ?? '',
    })
    setSuccess('')
    setError('')
    setValidationErrors(null)
  }

  async function handleStockSubmit(event) {
    event.preventDefault()
    setSaving(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      await updateProductStock(stockForm.productId, Number(stockForm.newStock))
      setStockForm(emptyStockForm)
      setSuccess('Stok basariyla guncellendi.')
      await loadInitialData()
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setSaving(false)
    }
  }

  return {
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
  }
}
