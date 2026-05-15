import { get, post } from './apiClient'

export function getCategories() {
  return get('/api/categories')
}

export function createCategory(category) {
  return post('/api/categories', category)
}
