package com.iemr.mmu.data.anc;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "t_BenMedHistory")
public class BenMedHistory {
	
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "BenMedHistoryID")
	private Long benMedHistoryID;

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
	@Column(name = "YearofIllness")
	private Integer yearofIllness;
	
	@Expose
	@Column(name = "IllnessTypeID")
	private Integer illnessTypeID;
	
	@Expose
	@Column(name = "IllnessType")
	private Integer illnessType;
	
	@Expose
	@Column(name = "SurgeryID")
	private Integer surgeryID;
	
	@Expose
	@Column(name = "SurgeryType")
	private Integer surgeryType;
	
	@Expose
	@Column(name = "YearofSurgery")
	private Integer yearofSurgery;
	
	@Expose
	@Column(name = "DrugComplianceID")
	private Integer drugComplianceID;
	
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
	
}
