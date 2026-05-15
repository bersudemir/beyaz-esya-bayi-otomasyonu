function ErrorMessage({ message, validationErrors }) {
  if (!message && !validationErrors) {
    return null
  }

  const entries = validationErrors ? Object.entries(validationErrors) : []

  return (
    <div className="message-box error-message">
      {message && <p>{message}</p>}

      {entries.length > 0 && (
        <ul>
          {entries.map(([field, error]) => (
            <li key={field}>
              <strong>{field}:</strong> {error}
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}

export default ErrorMessage
