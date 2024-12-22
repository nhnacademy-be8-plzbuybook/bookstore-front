const urlParams = new URLSearchParams(window.location.search);

const codeElement = document.getElementById("code");
const messageElement = document.getElementById("message");

codeElement.textContent = urlParams.get("code");
messageElement.textContent = urlParams.get("message");