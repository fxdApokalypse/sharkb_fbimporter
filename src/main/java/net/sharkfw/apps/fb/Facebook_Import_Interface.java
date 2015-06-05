package net.sharkfw.apps.fb;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jörn Sattler s0542818,Alexander Ihm s0543565
 */
public interface Facebook_Import_Interface {

	public int getAge();

	public Date getBirthdate();

	public String getResidence();

	public String getGender();

	public String getInterestedIn();

	public String getIsinRelationship();

	public String getUserID();

	public String getProfilelink();

	public List<String> getLikes();
}
