/*****************************************************************************************
 * // class DataPacket extends Packet
 * // Purpose: This class represents a data packet
 * Linda Crane,   CST8130
 * // data members:
 * //  
 * // methods: 
 *    processFoundPacket (String):boolean - this method prints message that destination
 *         network is in routing table so we are "routing" packet to next hop - which is
 *         parameter info - returns false to indicate not to add to table
 *    processNotFoundPacket (String):boolean - this method prints message that destination
 *         network is not found so we are dropping packet - returns false not to add to table
 ***************************************************************************************/
public class DataPacket extends Packet {
	public boolean processFoundPacket(String port) {
		System.out.println("Sending packet out " + port + "  " + destAddress.getNetwork());
		return false;

	}

	public boolean processNotFoundPacket(String port) {
		System.out.println("dropping packet...." + destAddress.getNetwork());
		return false;
	}

}
