from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# A simple in-memory storage for the chat messages
chat_messages = []

# Route for adding a new chat message
@app.route('/chat', methods=['POST'])
def add_message():
    message = request.json['message']
    chat_messages.append(message)
    return jsonify({"message": message}), 201

# Route for getting all chat messages
@app.route('/chat', methods=['GET'])
def get_messages():
    return jsonify({"messages": chat_messages}), 200

@app.route('/messages/<user_id>', methods=['GET'])
def get_messages(user_id):
    # code to retrieve all messages for the user
    return jsonify({'messages': messages})
@app.route('/users/online', methods=['GET'])
def get_online_users():
    # code to retrieve all online users
    return jsonify({'users': online_users})

@app.route('/groups/add_user', methods=['POST'])
def add_user_to_group():
    data = request.json
    group_id = data['group_id']
    user_id = data['user_id']
    # code to add user to group
    return jsonify({'message': 'User added to group successfully'})
@app.route('/groups/create', methods=['POST'])
def create_group():
    data = request.json
    group_name = data['group_name']
    members = data['members']
    # code to create new group
    return jsonify({'message': 'Group created successfully'})


if __name__ == '__main__':
    app.run(debug=True)
