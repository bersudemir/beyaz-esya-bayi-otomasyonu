import { get, patch, post, put } from './apiClient'

export function createSale(payload) {
  return post('/api/sales', payload)
}

export function addSaleDetail(saleId, payload) {
  return post(`/api/sales/${saleId}/details`, payload)
}

export function updateSaleDetailQuantity(saleId, productId, payload) {
  return put(`/api/sales/${saleId}/details/${productId}`, payload)
}

export function updateSaleStatus(saleId, payload) {
  return patch(`/api/sales/${saleId}/status`, payload)
}

export function getSaleReport() {
  return get('/api/sales/report')
}

export function getCustomerSales(customerId) {
  return get(`/api/sales/customer/${customerId}`)
}
