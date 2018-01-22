package com.iemr.mmu.data.anc;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_childfeedinghistory")
public class ChildFeedingDetails {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ID")
	private Long ID;

	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;

	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	
	@Expose
	@Column(name = "ChildID")
	private Long childID;

	@Expose
	@Column(name = "BenMotherID")
	private Long benMotherID;

	@Expose
	@Column(name = "TypeOfFeed")
	private String typeOfFeed;

	@Expose
	@Column(name = "CompFeedStartAge")
	private String compFeedStartAge;
	
	@Expose
	@Column(name = "NoOfCompFeedPerDay")
	private Character noOfCompFeedPerDay;

	@Expose
	@Column(name = "FoodIntoleranceStatus")
	private Character foodIntoleranceStatus;

	@Expose
	@Column(name = "TypeofFoodIntolerance")
	private String typeofFoodIntolerance;

	@Expose
	@Column(name = "Deleted", insertable = false, updatable = true)
	private Boolean deleted;

	@Expose
	@Column(name = "Processed", insertable = false, updatable = true)
	private String processed;

	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;

	@Expose
	@Column(name = "CreatedDate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;

	@Expose
	@Column(name = "LastModDate", insertable = false, updatable = false)
	private Timestamp lastModDate;

	@Expose
	@Column(name = "VanSerialNo")
	private Long vanSerialNo;
	
	@Expose
	@Column(name = "VehicalNo")
	private String vehicalNo;
	
	@Expose
	@Column(name = "ParkingPlaceID")
	private Integer parkingPlaceID;
	
	@Expose
	@Column(name = "SyncedBy")
	private String syncedBy;
	
	@Expose
	@Column(name = "SyncedDate")
	private Timestamp syncedDate;
	
	@Expose
	@Column(name = "ReservedForChange")
	private String reservedForChange;
	
	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public Long getChildID() {
		return childID;
	}

	public void setChildID(Long childID) {
		this.childID = childID;
	}

	public Long getBenMotherID() {
		return benMotherID;
	}

	public void setBenMotherID(Long benMotherID) {
		this.benMotherID = benMotherID;
	}

	public String getTypeOfFeed() {
		return typeOfFeed;
	}

	public void setTypeOfFeed(String typeOfFeed) {
		this.typeOfFeed = typeOfFeed;
	}

	public String getCompFeedStartAge() {
		return compFeedStartAge;
	}

	public void setCompFeedStartAge(String compFeedStartAge) {
		this.compFeedStartAge = compFeedStartAge;
	}

	public Character getNoOfCompFeedPerDay() {
		return noOfCompFeedPerDay;
	}

	public void setNoOfCompFeedPerDay(Character noOfCompFeedPerDay) {
		this.noOfCompFeedPerDay = noOfCompFeedPerDay;
	}

	public Character getFoodIntoleranceStatus() {
		return foodIntoleranceStatus;
	}

	public void setFoodIntoleranceStatus(Character foodIntoleranceStatus) {
		this.foodIntoleranceStatus = foodIntoleranceStatus;
	}

	public String getTypeofFoodIntolerance() {
		return typeofFoodIntolerance;
	}

	public void setTypeofFoodIntolerance(String typeofFoodIntolerance) {
		this.typeofFoodIntolerance = typeofFoodIntolerance;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

	public Long getID() {
		return ID;
	}

	public Long getVanSerialNo() {
		return vanSerialNo;
	}

	public void setVanSerialNo(Long vanSerialNo) {
		this.vanSerialNo = vanSerialNo;
	}

	public String getVehicalNo() {
		return vehicalNo;
	}

	public void setVehicalNo(String vehicalNo) {
		this.vehicalNo = vehicalNo;
	}

	public Integer getParkingPlaceID() {
		return parkingPlaceID;
	}

	public void setParkingPlaceID(Integer parkingPlaceID) {
		this.parkingPlaceID = parkingPlaceID;
	}

	public String getSyncedBy() {
		return syncedBy;
	}

	public void setSyncedBy(String syncedBy) {
		this.syncedBy = syncedBy;
	}

	public Timestamp getSyncedDate() {
		return syncedDate;
	}

	public void setSyncedDate(Timestamp syncedDate) {
		this.syncedDate = syncedDate;
	}

	public String getReservedForChange() {
		return reservedForChange;
	}

	public void setReservedForChange(String reservedForChange) {
		this.reservedForChange = reservedForChange;
	}
	
}
