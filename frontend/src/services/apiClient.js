const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

async function parseResponse(response) {
  const contentType = response.headers.get('content-type')

  if (!contentType?.includes('application/json')) {
    return null
  }

  return response.json()
}

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  })

  const data = await parseResponse(response)

  if (!response.ok) {
    const error = new Error(data?.message || 'Islem sirasinda bir hata olustu.')
    error.status = response.status
    error.validationErrors = data?.validationErrors || null
    error.details = data
    throw error
  }

  return data
}

export function get(path) {
  return request(path)
}

export function post(path, body) {
  return request(path, {
    method: 'POST',
    body: JSON.stringify(body),
  })
}

export function put(path, body) {
  return request(path, {
    method: 'PUT',
    body: JSON.stringify(body),
  })
}

export function patch(path, body) {
  return request(path, {
    method: 'PATCH',
    body: JSON.stringify(body),
  })
}
