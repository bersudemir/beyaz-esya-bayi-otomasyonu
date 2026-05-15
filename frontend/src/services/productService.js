import { get, patch } from './apiClient'

export function getProducts() {
  return get('/api/products')
}

export function getProductsByCategory(categoryId) {
  return get(`/api/products/category/${categoryId}`)
}

export function getProductStockView() {
  return get('/api/products/stock-view')
}

export function updateProductStock(productId, newStock) {
  return patch(`/api/products/${productId}/stock`, { newStock })
}
