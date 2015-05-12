/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htw.shark.nowdiscover.userutils;

/**
 *
 * @author Jörn Sattler s0542818
 */
public interface Interest_Interface {

	public Interest getInterest();

	public String getTopicname();

	public String getUserID();

	public String changeRating(String rating);

	public void RemoveRating();

	public String getRating();

}
