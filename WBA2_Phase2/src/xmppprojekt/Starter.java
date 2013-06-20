package xmppprojekt;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Starter {

	public static void main(String[] args) throws XMPPException {
		XMPPConnection.DEBUG_ENABLED = true;
		new Login();

	}
}
