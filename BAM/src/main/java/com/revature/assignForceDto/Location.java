package com.revature.assignForceDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="batch_location")
public class Location {

	@Id
	@Column(name="bl_id")
	private Integer Id;
	@Column(name="location_id")
	private Integer locationId;
	@Column(name="location_name")
	private String locationName;
	@Column(name="building_id")
	private Integer buildingId;
	@Column(name="building_name")
	private String buildingName;
	
	public Location() {}
	
	public Location(Integer id, Integer locationId, String locationName, Integer buildingId, String buildingName) {
		super();
		Id = id;
		this.locationId = locationId;
		this.locationName = locationName;
		this.buildingId = buildingId;
		this.buildingName = buildingName;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	@Override
	public String toString() {
		return "Location [Id=" + Id + ", locationId=" + locationId + ", locationName=" + locationName + ", buildingId="
				+ buildingId + ", buildingName=" + buildingName + "]";
	}
	
}
