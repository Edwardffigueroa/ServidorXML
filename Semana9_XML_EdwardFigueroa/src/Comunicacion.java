import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Observable;

import contenido.Bolita;

public class Comunicacion extends Observable implements Runnable {

	private MulticastSocket sock;
	private int port = 5000;
	private final String GROUP_ADDRESS = "224.2.2.2";
	private InetAddress group_ia;
	private DatagramPacket buzon;
	
	
	public Comunicacion(){
		
		try {

			port = 5000;
			byte[] buffer = new byte[1024];
			group_ia= InetAddress.getByName(GROUP_ADDRESS); 
			sock = new MulticastSocket(port);
			sock.joinGroup(group_ia);
			buzon = new DatagramPacket(buffer, buffer.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public DatagramPacket receiveMessage() throws IOException {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		sock.receive(packet);
		System.out.println("Data received from " + packet.getAddress() + ":" + packet.getPort());
		return packet;

	}
	
	@Override
	public void run() {

		while (true) {

			try {
				
				
				
				DatagramPacket recif= receiveMessage();
				
				Object recifObject= deserialize(recif.getData());
				
				if (recifObject!=null) {
					if (recifObject instanceof Bolita) {
						Bolita bol= (Bolita)recifObject;
						int posX= bol.getX();
						int posY= bol.getY();
						int tam=bol.getDiam();
						int r= bol.getR();
						int g= bol.getG();
						int b= bol.getB();
						System.out.println(posX + "  " + posY+ "  "+tam+ "  "+r+ "  "+g+ "  "+b);
						
						setChanged();
						notifyObservers(bol);
						clearChanged();
					}
				}
				
				
				
				

			} catch (IOException e) {

				e.printStackTrace();
			}

		}		
	}
	
	private Object deserialize(byte[] bytes) {
		Object data = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			data = ois.readObject();

			// close streams
			ois.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

}
