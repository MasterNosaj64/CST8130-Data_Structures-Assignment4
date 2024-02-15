import java.util.Scanner;
import java.io.IOException;
/*****************************************************************************************
 * class Packet (base class for RoutingPacket and DataPacket)
 * Purpose: This class represents a generic packet
 * Linda Crane,   CST8130
 * data members:
 *     sourceAddress - object of IPAddress representing source
 *     destAddress - object of IPAddress representing destination
 *     packetData - String object - data in packet 
 * methods: 
 *    processFoundPacket (String):boolean - is called when packet destination network is found
 *         in routing table - return value determines if add to table is needed
 *    processNotFoundPacket (String):boolean - is called when packet destination network
 *         is not found in routing table - return value determines if add to table is needed
 ***************************************************************************************/
public class Packet {

	protected IPAddress sourceAddress = new IPAddress();
	protected IPAddress destAddress = new IPAddress();
	protected String packetData = new String();

	public Packet() {

	}

	public boolean readPacket(Scanner inFile) {
		boolean dataOK = true;
		dataOK = destAddress.readAddress(inFile);
		if (dataOK)
			dataOK = sourceAddress.readAddress(inFile);
		if (dataOK)
			packetData = inFile.next();
		return dataOK;
	}

	public IPAddress getDestNetwork() {
		return destAddress.getNetwork();
	}

	public boolean processFoundPacket(String info) {
		return true;
	}

	public boolean processNotFoundPacket(String info) {
		return true;
	}

	public String getPacketData() {
		return packetData;
	}

}
