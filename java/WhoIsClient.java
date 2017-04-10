import java.io.*;
import java.net.*;
import java.util.regex.*;

public class WhoIsClient {
	
	// Set default whois port and hosts that may be used
	public final static int WHOIS_PORT = 43;
	public final static String IP_HOST = "whois.arin.net";
	//public final static String COM_HOST = "whois.networksolutions.com"; // not working properly
	public final static String EDU_HOST = "whois.educause.edu";
	public final static String DEFAULT_HOST = "whois.internic.net";
	
	// Main class
	public static void main(String[] args) throws Exception {
		
		// Check if user specified arguments, if not prompt for whois query
		String input = "";
		if(args.length == 0) {
			BufferedReader userinput = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("No arguments specified.");
			System.out.println("Please enter your WHOIS query now: ");
			input = userinput.readLine();
			
			// Close everything
			try {
				userinput.close();
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		} else {
			input = args[0];
		}
		
		// Create string object from input
		String query = new String(input);
		
		// Create strings to hold the query, type of query, and the whois host
		String qtype;
		String whoisHost;
		String qstring;
		
		
		// If query is IPv4 Address, query IP_HOST
		if (validIP(query)) {
			qtype = "IPv4 Address";
			whoisHost = IP_HOST;
			qstring = "n " + query;
		}
		// If query is .edu domain, query EDU_HOST
		else if (query.endsWith(".edu")) {
			qtype = ".EDU DOMAIN";
			whoisHost = EDU_HOST;
			qstring = query;
		}
		// If query is .com domain, query COM_HOST
		else if (query.endsWith(".com")) {
			qtype = ".COM DOMAIN";
			// whoisHost = COM_HOST; // not working properly, using default instead
			whoisHost = DEFAULT_HOST;
			qstring = "domain=" + query;
		}
		// If query is .net domain, query NET_HOST
		else if (query.endsWith(".net")) {
			qtype = ".NET DOMAIN";
			whoisHost = DEFAULT_HOST;
			qstring = "domain=" + query;
		}
		// If query is not IPv4 address, or .edu/.com/.net domain, query DEFAULT_HOST
		else {
			qtype = "UNKNOWN";
			whoisHost = DEFAULT_HOST;
			qstring = query;
		}
		
		// Let user know what query type was determined and query details
		System.out.println("Query Type: " + qtype);
		System.out.println("WHOIS Query for '" + query + "' at " + whoisHost + ":" + WHOIS_PORT);
		
		// Query WHOIS Server
		whoisQuery(whoisHost, WHOIS_PORT, qstring);
		
		// Let user know query is complete
		System.out.println("Completed WHOIS Query for '" + query + "' at " + whoisHost + ":" + WHOIS_PORT);
	}
	
	/* This method checks if a string is a valid IPv4 address */
	public static boolean validIP(String ip) throws Exception {
		
		// Format for IPv4 Address
		String ipV4 = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
		
		// Create pattern for IPv4 Address
		Pattern pattern;
		pattern = Pattern.compile(ipV4);
		
		// Create matcher to test IP string
		Matcher matcher;
		matcher = pattern.matcher(ip);
		
		// Return true if string is a valid IPv4 Address
		return matcher.matches();
	}
	
	/* This method queries a WHOIS Server  with a query */
	public static void whoisQuery(String host, int port, String query) throws Exception {
		
		// Connection socket to WHOIS Server
		Socket socket = new Socket(host, port);
		InputStreamReader raw = new InputStreamReader(socket.getInputStream());
		BufferedReader in = new BufferedReader(raw);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		// Ask WHOIS Server about query
		out.println(query);
		
		// Print output from WHOIS Server
		String line = "";
		while ((line=in.readLine()) != null) {
			System.out.println(line);
		}
		
		// Close readers and socket
		try {
			out.flush();
			out.close();
			in.close();
			raw.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Could not close socket.");
			System.out.println("Error: " + e);
		}
	}
}
