package htw.shark.nowdiscover.errors;

/**
 * constructor of our default exception, can be expanded for further needs
 * 
 * @author Alexander Ihm s0543565
 * 
 */
public class InformationUserInfo extends Exception {

	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = -4177373058722455280L;

	public InformationUserInfo() {
		super("User Infos not declared!");
	}

}
