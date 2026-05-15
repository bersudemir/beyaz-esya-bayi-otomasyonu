import { useEffect, useState } from 'react'
import {
  createCustomer,
  getCustomers,
  updateCustomer,
} from '../services/customerService'

const emptyForm = {
  firstName: '',
  lastName: '',
  phone: '',
  email: '',
}

function toForm(customer) {
  return {
    firstName: customer.firstName || '',
    lastName: customer.lastName || '',
    phone: customer.phone || '',
    email: customer.email || '',
  }
}

export function useCustomers() {
  const [customers, setCustomers] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [editingCustomerId, setEditingCustomerId] = useState(null)
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)
  const [success, setSuccess] = useState('')

  async function loadCustomers() {
    setLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const data = await getCustomers()
      setCustomers(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadCustomers, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  function handleInputChange(event) {
    const { name, value } = event.target
    setForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function startEdit(customer) {
    setEditingCustomerId(customer.customerId)
    setForm(toForm(customer))
    setError('')
    setValidationErrors(null)
    setSuccess('')
  }

  function resetForm() {
    setForm(emptyForm)
    setEditingCustomerId(null)
    setError('')
    setValidationErrors(null)
  }

  async function handleSubmit(event) {
    event.preventDefault()
    setSaving(true)
    setError('')
    setValidationErrors(null)
    setSuccess('')

    try {
      if (editingCustomerId) {
        await updateCustomer(editingCustomerId, form)
        setSuccess('Musteri basariyla guncellendi.')
      } else {
        await createCustomer(form)
        setSuccess('Musteri basariyla eklendi.')
      }

      resetForm()
      await loadCustomers()
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setSaving(false)
    }
  }

  return {
    customers,
    form,
    editingCustomerId,
    loading,
    saving,
    error,
    validationErrors,
    success,
    handleInputChange,
    handleSubmit,
    startEdit,
    resetForm,
  }
}
