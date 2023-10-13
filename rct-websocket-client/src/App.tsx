import { Client } from '@stomp/stompjs';
import { useEffect, useState } from 'react';
import './App.css';
import logo from './logo.svg';

function App() {
  let client: Client | null = null;

  const [chatId, setChatId] = useState("")

  useEffect(() => {
    // Create a WebSocket connection

    client = new Client();

    // Configure the WebSocket endpoint URL
    const websocketUrl = 'ws://localhost:8080/websocket'; // Replace with your WebSocket endpoint URL

    // Connect to the WebSocket server
    client.configure({
      brokerURL: websocketUrl,
      debug: function (str) {
        console.log(str);
      },
      onConnect: () => {
        // Perform actions after successful connection
        const destination = `/topic/chat/${chatId}`; // Specify the destination for the server-side message handler
        client && client.subscribe(destination, (message) => {
          console.log('Received message:', JSON.parse(message.body));
          // Process the received message as needed
        });
      },
      // You can add more event handlers and configuration options as needed
    });

    // Connect to the WebSocket server
    client.activate();


    // Clean up the connection on component unmount
    return () => {
      client && client.deactivate();
    };
  }, [chatId]);


  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          <input type='text' aria-label='ChatId' onChange={(event) => {
            console.log(event)
            setChatId(event.target.value)
          }}></input>
          <button onClick={() => {
            const destination = `/app/chat/${chatId}`; // Specify the destination for the server-side message handler
            const message = 'Hello, server!'; // Specify the message to send
            if (client != null) {
              client.publish({
                destination,
                body: JSON.stringify({
                  data: message,
                  userId: 1
                }),
              });
            }
          }}>Send</button>
        </p>
      </header>
    </div>
  );
}

export default App;
