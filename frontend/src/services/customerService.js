import { get, post, put } from './apiClient'

export function getCustomers() {
  return get('/api/customers')
}

export function createCustomer(customer) {
  return post('/api/customers', customer)
}

export function updateCustomer(customerId, customer) {
  return put(`/api/customers/${customerId}`, customer)
}
