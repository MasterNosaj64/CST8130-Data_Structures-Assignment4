/*****************************************************************************************
 * // class RoutingPacket extends Packet
 * // Purpose: This class represents a routing protocol packet
 * Linda Crane,   CST8130
 * // data members:
 * //  
 * // methods: 
 *    processFoundPacket (String):boolean - this method prints message that destination
 *         network is already in routing table - returns false to indicate not to add to table
 *    processNotFoundPacket (String):boolean - this method prints message that it is adding
 *         destination network to table with parameter port info - returns true to add to table
 ***************************************************************************************/
public class RoutingPacket extends Packet {
	
	public boolean processFoundPacket(String port) {
		System.out.println("Entry is already in the routing table" + destAddress.getNetwork());
		return false;

	}

	public boolean processNotFoundPacket(String port) {
		System.out.println("Adding entry to routing table" + destAddress.getNetwork());
		return true;
	}

}
