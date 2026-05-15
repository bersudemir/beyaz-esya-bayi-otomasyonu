import { useEffect, useState } from 'react'
import {
  createEmployee,
  getEmployees,
  updateEmployee,
} from '../services/employeeService'

const emptyForm = {
  firstName: '',
  lastName: '',
  phone: '',
  position: '',
  salary: '',
}

function toForm(employee) {
  return {
    firstName: employee.firstName || '',
    lastName: employee.lastName || '',
    phone: employee.phone || '',
    position: employee.position || '',
    salary: employee.salary ?? '',
  }
}

function toPayload(form) {
  return {
    ...form,
    salary: Number(form.salary),
  }
}

export function useEmployees() {
  const [employees, setEmployees] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [editingEmployeeId, setEditingEmployeeId] = useState(null)
  const [loading, setLoading] = useState(false)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState('')
  const [validationErrors, setValidationErrors] = useState(null)
  const [success, setSuccess] = useState('')

  async function loadEmployees() {
    setLoading(true)
    setError('')
    setValidationErrors(null)

    try {
      const data = await getEmployees()
      setEmployees(data || [])
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    const timeoutId = setTimeout(loadEmployees, 0)
    return () => clearTimeout(timeoutId)
  }, [])

  function handleInputChange(event) {
    const { name, value } = event.target
    setForm((currentForm) => ({
      ...currentForm,
      [name]: value,
    }))
  }

  function startEdit(employee) {
    setEditingEmployeeId(employee.employeeId)
    setForm(toForm(employee))
    setError('')
    setValidationErrors(null)
    setSuccess('')
  }

  function resetForm() {
    setForm(emptyForm)
    setEditingEmployeeId(null)
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
      const payload = toPayload(form)

      if (editingEmployeeId) {
        await updateEmployee(editingEmployeeId, payload)
        setSuccess('Calisan basariyla guncellendi.')
      } else {
        await createEmployee(payload)
        setSuccess('Calisan basariyla eklendi.')
      }

      resetForm()
      await loadEmployees()
    } catch (err) {
      setError(err.message)
      setValidationErrors(err.validationErrors)
    } finally {
      setSaving(false)
    }
  }

  return {
    employees,
    form,
    editingEmployeeId,
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
