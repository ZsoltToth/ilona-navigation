package uni.miskolc.ips.ilona.navigation.controller;

import java.util.Set;
import java.util.UUID;

import uni.miskolc.ips.ilona.navigation.service.WayfindingService.Restriction;

public class NavigationPersonRequest {

	private UUID startID;
	private String personName;
	private String startName;
	private Set<Restriction> restriction;

	public NavigationPersonRequest(UUID destinationID, String personName, String destinationName,
			Set<Restriction> restriction) {
		super();
		this.startID = destinationID;
		this.personName = personName;
		this.startName = destinationName;
		this.restriction = restriction;
	}

	public UUID getStartID() {
		return startID;
	}

	public void setStartID(UUID destinationID) {
		this.startID = destinationID;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String destinationName) {
		this.startName = destinationName;
	}

	public Set<Restriction> getRestriction() {
		return restriction;
	}

	public void setRestriction(Set<Restriction> restriction) {
		this.restriction = restriction;
	}

}
