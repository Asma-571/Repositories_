const socket = io.connect('http://localhost:5000');

const chatMessages = document.querySelector('#chat-messages');
const chatForm = document.querySelector('#chat-form');
const chatInput = document.querySelector('#chat-input');
const chatSend = document.querySelector('#chat-send');

// Add message to chat
function addMessage(msg) {
    const div = document.createElement('div');
    div.classList.add('message');
    div.innerHTML = `<p><strong>${msg.username}:</strong> ${msg.text}</p>`;
    chatMessages.appendChild(div);
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

// Send message to server
function sendMessage(e) {
    e.preventDefault();
    const message = chatInput.value.trim();
    if (!message) return;
    socket.emit('chat message', message);
    chatInput.value = '';
}

// Receive message from server
socket.on('chat message', function(msg) {
    addMessage(msg);
});

// Send message on form submit
chatForm.addEventListener('submit', sendMessage);

// Send message on send button click
chatSend.addEventListener('click', sendMessage);
