import 'package:flutter/material.dart';

class Message extends StatelessWidget {
  final String message;
  final String name;
  final bool isUserMessage;

  const Message(
      {super.key,
      required this.message,
      required this.name,
      required this.isUserMessage});

  CircleAvatar loadAvatar(String name) {
    return CircleAvatar(
      radius: 20.0,
      backgroundColor: Colors.green,
      child: Text(
        name.characters.first,
        style: const TextStyle(
          color: Colors.white,
          fontSize: 20.0,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        alignment: isUserMessage ? Alignment.centerRight : Alignment.centerLeft,
        margin: const EdgeInsets.all(8.0),
        child: Row(children: [
          !isUserMessage ? loadAvatar(name) : Container(),
          Container(
            padding: const EdgeInsets.all(12.0),
            margin: isUserMessage
                ? const EdgeInsets.only(right: 10.0)
                : const EdgeInsets.only(left: 10.0),
            decoration: BoxDecoration(
              color: isUserMessage
                  ? const Color.fromARGB(255, 88, 165, 0)
                  : const Color.fromARGB(255, 35, 88, 0),
              borderRadius: isUserMessage
                  ? const BorderRadius.only(
                      topLeft: Radius.circular(12.0),
                      topRight: Radius.circular(12.0),
                      bottomLeft: Radius.circular(12.0),
                    )
                  : const BorderRadius.only(
                      topLeft: Radius.circular(12.0),
                      topRight: Radius.circular(12.0),
                      bottomRight: Radius.circular(12.0),
                    ),
            ),
            child: Text(
              message,
              style: const TextStyle(
                color: Colors.white,
                fontSize: 20.0,
              ),
            ),
          ),
          isUserMessage ? loadAvatar(name) : Container(),
        ]));
  }
}
