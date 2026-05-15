import { useEffect, useState } from 'react'
import { createCategory, getCategories } from '../services/categoryService'

const emptyForm = {
  categoryName: '',
}

export function useCategories() {
  const [categories, setCategories] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)
  const [success, setSuccess] = useState('')

  async function loadCategories() {
    setLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const data = await getCategories()
      setCategories(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadCategories, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  function handleInputChange(event) {
    const { name, value } = event.target
    setForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  async function handleSubmit(event) {
    event.preventDefault()
    setSaving(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      await createCategory(form)
      setForm(emptyForm)
      setSuccess('Kategori basariyla eklendi.')
      await loadCategories()
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setSaving(false)
    }
  }

  return {
    categories,
    form,
    loading,
    saving,
    error,
    validationErrors,
    success,
    handleInputChange,
    handleSubmit,
  }
}
