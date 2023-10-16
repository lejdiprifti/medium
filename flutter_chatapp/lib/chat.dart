import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class Chat extends StatefulWidget {
  final String chatId;
  final String userId;
  const Chat({super.key, required this.chatId, required this.userId});

  @override
  ChatState createState() => ChatState();
}

class ChatState extends State<Chat> {
  final String webSocketUrl = 'ws://192.168.1.7:8080/websocket';
  late StompClient _client;
  final TextEditingController _controller = TextEditingController();
  List<dynamic> messages = List.empty();
  @override
  void initState() {
    super.initState();
    _client = StompClient(
        config: StompConfig(url: webSocketUrl, onConnect: onConnectCallback));
    _client.activate();
  }

  void onConnectCallback(StompFrame connectFrame) {
    _client.subscribe(
        destination: '/topic/chat/${widget.chatId}',
        headers: {},
        callback: (frame) {
          print(frame.body);
          // Received a frame for this subscription
          messages = jsonDecode(frame.body!).reversed.toList();
        });
  }

  void _sendMessage() {
    final message = _controller.text;
    if (message.isNotEmpty) {
      _client.send(
        destination: '/app/chat/${widget.chatId}', // Replace with your chat ID
        body: json.encode({
          'data': message,
          'userId': widget.userId
        }), // Format the message as needed
      );
      _controller.clear();
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenHeight = MediaQuery.of(context).size.height - 250;
    return Scaffold(
      appBar: AppBar(
        title: Text('Chat'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Form(
              child: TextFormField(
                controller: _controller,
                decoration: const InputDecoration(labelText: 'Send a message'),
              ),
            ),
            const SizedBox(height: 24),
            SingleChildScrollView(
              child: Container(
                height: screenHeight, // Set a fixed height here
                child: ListView.builder(
                  itemCount: messages.length,
                  itemBuilder: (context, index) {
                    // Extract the item from the list
                    Map<String, dynamic> item = messages[index];

                    // Create a Text widget for the item
                    return ListTile(
                      title: Text(item['data']),
                      // You can add more widgets here, e.g., icons, buttons, etc.
                    );
                  },
                ),
              ),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _sendMessage,
        tooltip: 'Send message',
        child: const Icon(Icons.send),
      ),
    );
  }

  @override
  void dispose() {
    _client.deactivate();
    _controller.dispose();
    super.dispose();
  }
}
