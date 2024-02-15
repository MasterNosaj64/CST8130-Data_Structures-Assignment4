import java.util.*;

/*****************************************************************************************
 * // class Router // Purpose: This class represents a router Linda Crane,
 * Modified by Jason Waid // CST8130 303 // April 8th 2019 // data members: //
 * routingTable - to hold up to 100 routing table entries methods: constructor
 * // displayTable - displays results of "show ip route" command on device-ie
 * entries in table // processPacket (Packet) - uses the parameter "packet" -
 * processes it//AddHashIndex - returns hash index to be used for adding using
 * Hash algorithms// searchHashIndex - uses hash algorithm for finding routing
 * table entry
 ***************************************************************************************/
class Router {
	private ArrayList<RoutingTableEntry> routingTable;

	// Modified size to 100
	public Router() {

		routingTable = new ArrayList<RoutingTableEntry>(100);
		for (int i = 0; i < 100; i++)
			routingTable.add(null);
	}

	// Modified proccessPacket method to implement HashIndex Algorithm for insert
	// and search
	public void processPacket(Packet inPacket) {

		boolean found = false;
		String port = null;

		RoutingTableEntry routeToFind = new RoutingTableEntry(inPacket.getDestNetwork(), "");

		/*
		 * Assignment 4 Requirement Use Hash Algorithm for searching
		 */
		int indexWhereExists = searchHashIndex(routeToFind, inPacket.getDestNetwork());
		// -1 is returned by searchHashIndex if destination network is not found
		if (indexWhereExists != -1) {

			if (indexWhereExists < routingTable.size()) {
				port = routingTable.get(indexWhereExists).searchForPort(inPacket.getDestNetwork());
				if (port != null) {
					found = true;
				}
			}

		}
		boolean addToTable = false;
		if (found)
			addToTable = inPacket.processFoundPacket(port);
		else
			addToTable = inPacket.processNotFoundPacket(port);

		if (addToTable) {
			RoutingTableEntry routeToAdd = new RoutingTableEntry(inPacket.getDestNetwork(), inPacket.getPacketData());

			/*
			 * New code Assign 4 requirement create hashIndex for adding
			 */

			int indexToInsert = addHashIndex(routeToAdd, inPacket.getDestNetwork());

			// addHashIndex returns -1 if the entry cannot be added
			if (indexToInsert == -1)
				System.err.print("\nReached end of Table, cannot add " + (routeToAdd.toString().length() - 9) + "\n");
			else {
				// Prints the index the entry is inserted at, removes the port number info from
				// string
				System.out.println("Inserting at index " + indexToInsert + " "
						+ routeToAdd.toString().substring(0, routeToAdd.toString().length() - 9));
				routingTable.set(indexToInsert, routeToAdd);

			}

		}
	}

	// New code for adding hashIndex
	public int addHashIndex(RoutingTableEntry tableEntry, IPAddress address) {
		/*
		 * hash algorithm to add index combines first three Octets then applies % 100 to
		 * the sum
		 */

		// Calculated initial hashIndex
		int hashIndex = address.addOctets(address.getNetwork()) % 100;

		// run algorithm as long as hashIndex doesn't pass 99
		while (hashIndex < 100) {

			// check if index has data
			if (routingTable.get(hashIndex) != null) {
				// increment hashIndex to continue search for free space
				hashIndex++;
			} else
				// return current hashIndex if no data is stored at this location
				return hashIndex;
		}
		// return -1 if hashIndex has surpassed 99
		return -1;
	}

	// new code for searching hashIndex
	public int searchHashIndex(RoutingTableEntry tableEntry, IPAddress address) {
		/*
		 * hash algorithm to find index combines first three Octets then applies % 100
		 * to the sum
		 */

		// Calculated initial hashIndex
		int hashIndex = address.addOctets(address.getNetwork()) % 100;

		// run algorithm as long as hashIndex doesn't pass 99
		while (hashIndex < 100) {

			// check if index has data, if no data is found, IPAddress cannot be in list
			if (routingTable.get(hashIndex) != null) {

				// check if data is same at given index
				if (routingTable.get(hashIndex).isEqual(tableEntry)) {

					// return hashIndex if data is found
					return hashIndex;

				} else
					// increment hashIndex to continue search for free space
					hashIndex++;
			} else
				// If index is null, IPAddress is not in list
				return -1;
		}
		// return -1 if hashIndex has surpassed 99, this also means its not found
		return -1;
	}

	// Modified to include the index values in table print
	public void displayTable() {
		System.out.println("\nRouting table...\n");
		for (int i = 0; i < routingTable.size(); i++)
			System.out.println("[" + i + "] " + routingTable.get(i));
	}
}
