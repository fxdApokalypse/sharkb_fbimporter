package htw.shark.nowdiscover.userutils;

import java.util.Date;
import java.util.List;

import net.sharkfw.knowledgeBase.SharkKBException;

/**
 *
 * @author Jörn Sattler s0542818
 * 
 */
public interface User_Interface {

	public User getUser();

	public String getUserID();

	public void setUserID(String name);

	public void setFacebookProfileURL(String... suis) throws SharkKBException;

	public String[] getUserInformation();

	public String[] getFacebookProfileURL();

	public int getAge();

	public int setAge(int age);

	public void setBirthdate(Date birthdate);

	public String getBirthdate();

	public void setResidence(String residence);

	public String getResidence();

	public void setGender(String gender);

	public String getGender();

	public void setInterestedIn(String preference);

	public String getInterestedIn();

	public void setIsinRelationship(String relationship);

	public String getIsinRelationship();

	public void setLikes(List<String> Likes);

	public List<String> getLikes();

	public void importUserInformation(List<String> userinfo);

}
