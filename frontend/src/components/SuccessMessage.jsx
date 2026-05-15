function SuccessMessage({ message }) {
  if (!message) {
    return null
  }

  return <div className="message-box success-message">{message}</div>
}

export default SuccessMessage
