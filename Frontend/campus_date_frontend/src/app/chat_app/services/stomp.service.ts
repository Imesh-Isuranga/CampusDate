import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import Stomp, { Frame, Subscription as StompSubscription } from 'stompjs';


@Injectable({
  providedIn: 'root',
})
export class StompService {
  constructor() {
    // Initialize StompClient and configure debugging to null
    this.stompClient.debug = () => {};
    // Connect to the WebSocket server
    this.stompClient.connect({}, (): any => {
      // Successful connection callback
    });
  }

  // Create a WebSocket connection using SockJS
  socket = new SockJS('http://localhost:8085/stomp-endpoint');
  // Initialize the Stomp client using the WebSocket connection
  stompClient = Stomp.over(this.socket);

  // Subscribe to a specific topic and provide a callback function to handle incoming messages
  subscribe(topic: string, callback: any): StompSubscription  {
    return this.stompClient.subscribe('/topic/' + topic, (frame: any): any => {
      // Parse the message body and pass it to the callback function
      callback(JSON.parse(frame.body));
    });
  }

  // Send a message to a specific application using Stomp
  send(app: string, data: any) {
    // Send the data as a JSON string to the specified application
    this.stompClient.send('/app/' + app, {}, JSON.stringify(data));
  }
}
