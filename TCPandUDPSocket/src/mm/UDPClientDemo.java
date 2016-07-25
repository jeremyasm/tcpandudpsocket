package mm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import util.HexStringUtil;

public class UDPClientDemo {
	
	String deviceAddr; //input para
	int devicePort; //input para
	String msgToDevice;
	
	DatagramSocket clientSocket;
	InetAddress deviceIP;
	byte[] sendData = new byte[1024];
	byte[] receiveData = new byte[1024];
	
	public UDPClientDemo(String deviceAddr, int devicePort, String msgToDevice){
		this.deviceAddr = deviceAddr;
		this.devicePort = devicePort;
		this.msgToDevice = msgToDevice;
		try {
			this.clientSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.deviceIP = InetAddress.getByName(deviceAddr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	
	public String sendAndReceive() throws IOException{
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, deviceIP, devicePort);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.send(sendPacket);
		clientSocket.receive(receivePacket);
		String received = new String(receivePacket.getData());
		System.out.println("what we receive: " + received);
		return received;
	}
	
	
	public void sendUDPPacket() throws IOException{
	
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		z:
		while(true){
		
			sendData = msgToDevice.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, deviceIP, devicePort);
			clientSocket.send(sendPacket);
			clientSocket.receive(receivePacket);
			
			if(receivePacket.getAddress().getHostAddress().equals(deviceAddr)){		
				String recMsgHex = new String(receivePacket.getData());
				String eventCode = recMsgHex.substring(12, 14);
				String crc = recMsgHex.substring(recMsgHex.length() - 4, recMsgHex.length());
				
				System.out.println("eventCode: " + eventCode);
				if(eventCode.equals("F0")){
					System.out.println("get the self test reply from T-Box");
					break z;
				}
			}
			String modifiedSentence = new String(receivePacket.getData());
			System.out.println("FROM SERVER: " + modifiedSentence );
		}//end while
		
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER: " + modifiedSentence );
		clientSocket.close();
	
		
	}
	
	
	
	public static void main(String[] args) throws IOException{
		
		UDPClientDemo udpClient = new UDPClientDemo("9.123.145.59", 9876, "it's a test message");
		udpClient.sendUDPPacket();
		
//		int counter = 0;
//		
//		String deviceAddr = "9.123.145.59"; //input para
//		int devicePort = 9876; //input para
//		String msgToDevice = "it's a test message ";
//		
//		DatagramSocket clientSocket = new DatagramSocket();
//		InetAddress deviceIP = InetAddress.getByName(deviceAddr);
//		byte[] sendData = new byte[1024];
//		byte[] receiveData = new byte[1024];
//		
//		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//		z:
//		while(true){
//			counter ++;
//			System.out.println("counter: " + counter);
//			clientSocket.setSoTimeout(5000);
//			sendData = (msgToDevice + counter).getBytes();
//			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, deviceIP, devicePort);
//			clientSocket.send(sendPacket);
//			clientSocket.receive(receivePacket);
//			
//			if(receivePacket.getAddress().getHostAddress().equals(deviceAddr)){		
//				String recMsgHex = new String(receivePacket.getData());
//				String eventCode = recMsgHex.substring(22, 25);
//				System.out.println("eventCode: " + eventCode);
//				if(eventCode.trim().equals("21")){
//					System.out.println("21!!!!");
//					break z;
//				}
//			}
//			String modifiedSentence = new String(receivePacket.getData());
//			System.out.println("FROM SERVER: " + modifiedSentence );
//		}//end while
//		
//		String modifiedSentence = new String(receivePacket.getData());
//		System.out.println("FROM SERVER: " + modifiedSentence );
//		clientSocket.close();
//	}
	}
	
}
