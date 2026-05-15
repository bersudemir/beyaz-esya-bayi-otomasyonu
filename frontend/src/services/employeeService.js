import { get, post, put } from './apiClient'

export function getEmployees() {
  return get('/api/employees')
}

export function createEmployee(employee) {
  return post('/api/employees', employee)
}

export function updateEmployee(employeeId, employee) {
  return put(`/api/employees/${employeeId}`, employee)
}
