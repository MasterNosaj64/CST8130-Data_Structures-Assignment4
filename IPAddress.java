import java.util.*;

/*****************************************************************************************
 * class IPAddress 
 * Purpose: This class represents the four octets and subnet mask of an IP address 
 * Author: Linda Crane //Modified by Jason Waid CST8130 303 April 8th 2019
 * data members: 
 *   address - array of four ints for each octet 
 *   subnet - number of network bits (CIDR notation) 
 * methods: 
 *   constructor 
 *   initialize - with int array [4] and subnet parameters 
 *   initialize - with object of type IPAddress
 *   readFile - reads address info from parameter Scanner object - returns
 *                    boolean whether valid IP was read 
 *   readKeyboard - reads valid info from keyboard 
 *   isValid - returns true/false if IP address is valid 
 *   toString - displays contents to a String (return) 
 *   getNetwork - returns appropriate network address in IPAddress parameter 
 *   isEqual bases decision if two objects are on same network (have same network number) 
 *   addOctets - Returns the sum of the first three Octets for a given IPAddress
 ***************************************************************************************/
class IPAddress {
	private int[] address = new int[4]; // each of four octets
	private int subnet; // number of network bits

	public IPAddress() {
		for (int i = 0; i < 4; i++)
			address[i] = 0;
		subnet = 0;
	}

	public boolean initialize(int[] add, int sub) {
		for (int i = 0; i < 4; i++)
			address[i] = add[i];
		subnet = sub;
		return (isValid());
	}

	public boolean initialize(IPAddress newOne) {
		for (int i = 0; i < 4; i++)
			address[i] = newOne.address[i];
		subnet = newOne.subnet;
		return (isValid());
	}

	public boolean readAddress(Scanner inFile) {
		try {
			for (int i = 0; i < 4; i++)
				address[i] = inFile.nextInt();
			subnet = inFile.nextInt();
			if (!isValid())
				return false;
			else
				return true;
		} catch (NumberFormatException nfe) {
			System.out
					.println("Invalid data....in readAddress of IPAddress class");
			return false;
		}
	}

	public boolean readKeyboard() {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter four numbers for the four octets: ");
		for (int i = 0; i < 4; i++)
			address[i] = keyboard.nextInt();
		System.out.print("\nEnter subnet number: ");
		subnet = keyboard.nextInt();
		while (!isValid()) {
			System.out.print("Enter four numbers for the four octets: ");
			for (int i = 0; i < 4; i++)
				address[i] = keyboard.nextInt();
			System.out.print("\nEnter subnet number: ");
			subnet = keyboard.nextInt();
		}
		return true;
	}

	public boolean isValid() {
		if (address[0] < 0 || address[0] > 255 || address[1] < 0
				|| address[1] > 255 || address[2] < 0 || address[2] > 255
				|| address[3] < 0 || address[3] > 255 || subnet > 32
				|| subnet < 0)
			return false;
		else if (subnet <= 8 && address[0] > 127)
			return false;
		else if (subnet <= 16 && address[0] < 128 && address[0] > 191)
			return false;
		else if (subnet <= 24 && address[0] < 192 && address[0] > 223)
			return false;
		else
			return true;
	}

	public String toString() {
		String out = new String();
		for (int i = 0; i < 3; i++)
			out += address[i] + ".";
		out += address[3];
		out += "\\" + subnet;
		return out;
	}

	public IPAddress getNetwork() {
		IPAddress networkIP = new IPAddress();
		int maxOctetsToCopy = 0;
		// figure out how much is network...how much is host I could also do
		// NaxOctetsToCopy = subnet / 8;
		if (subnet == 24)
			maxOctetsToCopy = 3;
		if (subnet == 16)
			maxOctetsToCopy = 2;
		if (subnet == 8)
			maxOctetsToCopy = 1;
		// copy Network address

		for (int i = 0; i < maxOctetsToCopy; i++)
			networkIP.address[i] = address[i];
		for (int i = maxOctetsToCopy; i < 4; i++)
			networkIP.address[i] = 0;
		networkIP.subnet = subnet;
		return networkIP;
	}

	public boolean isEqual(IPAddress rhs) {
		boolean result = true;
		IPAddress rhsNetwork;
		rhsNetwork = rhs.getNetwork();
		IPAddress lhsNetwork;
		lhsNetwork = getNetwork();
		for (int i = 0; i < 4; i++)
			if (rhsNetwork.address[i] != lhsNetwork.address[i])
				result = false;
		if (rhsNetwork.subnet != lhsNetwork.subnet)
			result = false;
		return result;
	}
	
	public boolean isGreaterThan (IPAddress rhs){
		for (int i=0; i< 4; i++) {
			if (this.address[i] < rhs.address[i])
				return false;
			else if (this.address[i] > rhs.address[i])
				return true;
		}
		return false;
	}
	//New Method for Assign 4
	//Adds first three octets together
	public int addOctets(IPAddress rhs) {
		return rhs.address[0] + rhs.address[1] + rhs.address[2];
	}
	
}
