import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.*;
/*****************************************************************************************
 * // class Assignment4 
 * // Purpose: This class represents a router 
 * Linda Crane,   CST8130
 *  method main - This program asks user for name of file and connects to that file,
 *  then reads each line of the file as a packet and processes it(either data packet or routing protocol 
 *  packet based on first char input on the line)  
 ***************************************************************************************/
public class Assignment4 {

	public static void main(String[] args) {
		Scanner inFile = null;
		Packet inPacket = new Packet();
		Router router = new Router();
		char packetCode = 'd';
		boolean dataOK = true;

		inFile = openFile();
		if (inFile != null) {
			try {
				
				while (inFile.hasNext() && packetCode != 'f') {
					packetCode = inFile.next().charAt(0);
					if (packetCode == 'p') {
						inPacket = new RoutingPacket();
					} else {
						inPacket = new DataPacket();
					}
					dataOK = inPacket.readPacket(inFile);
					if (dataOK)
						router.processPacket(inPacket);

				}// end while
				
			} catch (NumberFormatException nfe) {
				System.out.println("Error reading from file in main ");
				packetCode = 'f';
			} catch (NoSuchElementException nsee) {
				packetCode = 'f';
			}
		} else
			System.out.println("No file read....program ending");
		
		// display table before exiting
		router.displayTable();

	}// end of main

	public static Scanner openFile() {

		Scanner keyboard = new Scanner(System.in);
		String fileName = new String();
		Scanner inFile = null;

		System.out.print("Enter name of file to process: ");
		fileName = keyboard.next();

		File file = new File(fileName);
		try {
			if (file.exists()) {
				inFile = new Scanner(file);
			}
			return inFile;
		} catch (IOException e) {
			System.out
					.println("Could not open file...." + fileName + "exiting");
			return null;
		}
	}// end openFile method

}// end class

