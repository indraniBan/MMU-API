package com.iemr.mmu.service.registrar;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.nurse.BenPersonalCancerHistory;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.registrar.BenGovIdMapping;
import com.iemr.mmu.data.registrar.BeneficiaryData;
import com.iemr.mmu.data.registrar.BeneficiaryDemographicAdditional;
import com.iemr.mmu.data.registrar.BeneficiaryDemographicData;
import com.iemr.mmu.data.registrar.BeneficiaryImage;
import com.iemr.mmu.data.registrar.BeneficiaryPhoneMapping;
import com.iemr.mmu.data.registrar.FetchBeneficiaryDetails;
import com.iemr.mmu.data.registrar.V_BenAdvanceSearch;
import com.iemr.mmu.data.registrar.WrapperRegWorklist;
import com.iemr.mmu.repo.registrar.BeneficiaryDemographicAdditionalRepo;
import com.iemr.mmu.repo.registrar.BeneficiaryImageRepo;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenData;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenDemoData;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenGovIdMapping;
import com.iemr.mmu.repo.registrar.RegistrarRepoBenPhoneMapData;
import com.iemr.mmu.repo.registrar.RegistrarRepoBeneficiaryDetails;
import com.iemr.mmu.repo.registrar.ReistrarRepoBenSearch;

@Service
public class RegistrarServiceImpl implements RegistrarService {
	private RegistrarRepoBenData registrarRepoBenData;
	private RegistrarRepoBenDemoData registrarRepoBenDemoData;
	private RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData;
	private RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping;
	private ReistrarRepoBenSearch reistrarRepoBenSearch;
	private BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo;
	private RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails;
	private BeneficiaryImageRepo beneficiaryImageRepo;

	@Autowired
	public void setBeneficiaryImageRepo(BeneficiaryImageRepo beneficiaryImageRepo) {
		this.beneficiaryImageRepo = beneficiaryImageRepo;
	}

	@Autowired
	public void setBeneficiaryDemographicAdditionalRepo(
			BeneficiaryDemographicAdditionalRepo beneficiaryDemographicAdditionalRepo) {
		this.beneficiaryDemographicAdditionalRepo = beneficiaryDemographicAdditionalRepo;
	}

	@Autowired
	public void setRegistrarRepoBenData(RegistrarRepoBenData registrarRepoBenData) {
		this.registrarRepoBenData = registrarRepoBenData;
	}

	@Autowired
	public void setRegistrarRepoBenDemoData(RegistrarRepoBenDemoData registrarRepoBenDemoData) {
		this.registrarRepoBenDemoData = registrarRepoBenDemoData;
	}

	@Autowired
	public void setRegistrarRepoBenPhoneMapData(RegistrarRepoBenPhoneMapData registrarRepoBenPhoneMapData) {
		this.registrarRepoBenPhoneMapData = registrarRepoBenPhoneMapData;
	}

	@Autowired
	public void setRegistrarRepoBenGovIdMapping(RegistrarRepoBenGovIdMapping registrarRepoBenGovIdMapping) {
		this.registrarRepoBenGovIdMapping = registrarRepoBenGovIdMapping;
	}

	@Autowired
	public void setReistrarRepoAdvanceBenSearch(ReistrarRepoBenSearch reistrarRepoBenSearch) {
		this.reistrarRepoBenSearch = reistrarRepoBenSearch;
	}

	@Autowired
	public void setRegistrarRepoBeneficiaryDetails(RegistrarRepoBeneficiaryDetails registrarRepoBeneficiaryDetails) {
		this.registrarRepoBeneficiaryDetails = registrarRepoBeneficiaryDetails;
	}

	@Override
	public BeneficiaryData createBeneficiary(JsonObject benD) {
		Long benRegID = null;
		// Call repository for saving data in
		// Table: i_beneficairy
		// Persistence Class: BeneficiaryData
		BeneficiaryData benData = registrarRepoBenData.save(getBenOBJ(benD));
		return benData;
	}

	@Override
	public Long createBeneficiaryDemographic(JsonObject benD, Long benRegID) {
		Long tmpBenDemoID = null;
		// Call repository for saving data in
		// Table: i_bendemographics
		// Persistence Class: BeneficiaryDemographicData
		BeneficiaryDemographicData benDemoData = registrarRepoBenDemoData.save(getBenDemoOBJ(benD, benRegID));
		if (benDemoData != null) {
			tmpBenDemoID = benDemoData.getBenDemographicsID();
		}
		return tmpBenDemoID;
	}

	@Override
	public Long createBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		Long tmpBenDemoAddID = null;
		BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = beneficiaryDemographicAdditionalRepo
				.save(getBeneficiaryDemographicAdditional(benD, benRegID));
		if (beneficiaryDemographicAdditional != null) {
			tmpBenDemoAddID = beneficiaryDemographicAdditional.getBenDemoAdditionalID();
		}
		return tmpBenDemoAddID;
	}

	@Override
	public Long createBeneficiaryImage(JsonObject benD, Long benRegID) {
		Long tmpBenImageID = null;
		BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
		beneficiaryImage.setBeneficiaryRegID(benRegID);
		if (!benD.get("image").isJsonNull())
			beneficiaryImage.setBenImage(benD.get("image").getAsString());
		if (!benD.get("createdBy").isJsonNull())
			beneficiaryImage.setCreatedBy(benD.get("createdBy").getAsString());

		BeneficiaryImage benImage = beneficiaryImageRepo.save(beneficiaryImage);
		if (benImage != null) {
			tmpBenImageID = benImage.getBeneficiaryRegID();
		}
		return tmpBenImageID;
	}

	private BeneficiaryDemographicAdditional getBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		BeneficiaryDemographicAdditional benDemoAd = new BeneficiaryDemographicAdditional();
		benDemoAd.setBeneficiaryRegID(benRegID);

		if (benD.has("literacyStatus") && !benD.get("literacyStatus").isJsonNull()) {
			benDemoAd.setLiteracyStatus(benD.get("literacyStatus").getAsString());
		}

		if (benD.has("motherName") && !benD.get("motherName").isJsonNull()) {
			benDemoAd.setMotherName(benD.get("motherName").getAsString());
		}
		if (benD.has("emailID") && !benD.get("emailID").isJsonNull()) {
			benDemoAd.setEmailID(benD.get("emailID").getAsString());
		}
		if (benD.has("bankName") && !benD.get("bankName").isJsonNull()) {
			benDemoAd.setBankName(benD.get("bankName").getAsString());
		}
		if (benD.has("branchName") && !benD.get("branchName").isJsonNull()) {
			benDemoAd.setBranchName(benD.get("branchName").getAsString());
		}
		if (benD.has("IFSCCode") && !benD.get("IFSCCode").isJsonNull()) {
			benDemoAd.setiFSCCode(benD.get("IFSCCode").getAsString());
		}
		if (benD.has("accountNumber") && !benD.get("accountNumber").isJsonNull()) {
			benDemoAd.setAccountNo(benD.get("accountNumber").getAsString());
		}
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benDemoAd.setCreatedBy(benD.get("createdBy").getAsString());

		if (benD.has("ageAtMarriage") && !benD.get("ageAtMarriage").isJsonNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

			java.util.Date parsedDate;
			int ageAtMarriage = benD.get("ageAtMarriage").getAsInt();
			int currentAge = benD.get("age").getAsInt();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -(currentAge - ageAtMarriage));
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_YEAR, 1);

			//System.out.println(cal.getTime());

			Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
			//System.out.println(timestamp);
			benDemoAd.setMarrigeDate(timestamp);

		}

		// Following values will get only in update request
		if (benD.has("benDemoAdditionalID") && !benD.get("benDemoAdditionalID").isJsonNull()) {
			benDemoAd.setBenDemoAdditionalID(benD.get("benDemoAdditionalID").getAsLong());
		}
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull()) {
			benDemoAd.setModifiedBy(benD.get("modifiedBy").getAsString());
		}
		return benDemoAd;
	}

	@Override
	public Long createBeneficiaryPhoneMapping(JsonObject benD, Long benRegID) {
		Long tmpBenPhonMapID = null;
		// Call repository for saving data in
		// Table: m_benphonemap
		// Persistence Class: BeneficiaryPhoneMapping
		BeneficiaryPhoneMapping benPhoneMap = registrarRepoBenPhoneMapData.save(getBenPhoneOBJ(benD, benRegID));
		if (benPhoneMap != null) {
			tmpBenPhonMapID = benPhoneMap.getBenPhMapID();
		}
		return tmpBenPhonMapID;
	}

	public int createBenGovIdMapping(JsonObject benD, Long benRegID) {
		Long tempBenGovMapID = null;
		// Call repository for saving Data to table m_bengovidmap and
		// Persistence Class = BenGovIdMapping
		//System.out.println("hello");
		ArrayList<BenGovIdMapping> benGovIDMap = (ArrayList<BenGovIdMapping>) registrarRepoBenGovIdMapping
				.save(BenGovIdMapping.getBenGovIdMappingOBJList(benD, benRegID));
		//System.out.println("hello");
		return benGovIDMap.size();
	}

	@Override
	public String getRegWorkList(int i) {
		// Call repository for fetching data from
		// Table: i_beneficiary, i_bendemographics, m_benphonemap
		// Persistence Class: BeneficiaryData, BeneficiaryDemographicData,
		// ...................BeneficiaryPhoneMapping
		List<Object[]> resList = registrarRepoBenData.getRegistrarWorkList(i);
		//System.out.println("helloo.....");
		return WrapperRegWorklist.getRegistrarWorkList(resList);
	}

	@Override
	public String getQuickSearchBenData(String benID) {
		// List<Object[]> resList = registrarRepoBenData.getQuickSearch(benID);
		List<Object[]> resList = reistrarRepoBenSearch.getQuickSearch(benID);
		//System.out.println("hello...");
		return WrapperRegWorklist.getRegistrarWorkList(resList);
	}

	public String getAdvanceSearchBenData(V_BenAdvanceSearch v_BenAdvanceSearch) {
		String result = "";
		try {
			String benID = "%%";
			String benFirstName = "";
			String benLastName = "";
			String fatherName = "";
			String phoneNo = "%%";
			String aadharNo = "%%";
			String govIDNo = "%%";
			String stateID = "%%";
			String districtID = "%%";

			if (null != v_BenAdvanceSearch.getBeneficiaryID() && !v_BenAdvanceSearch.getBeneficiaryID().isEmpty()) {
				benID = v_BenAdvanceSearch.getBeneficiaryID();
			}
			if (null != v_BenAdvanceSearch.getFirstName() && !v_BenAdvanceSearch.getFirstName().isEmpty()) {
				benFirstName = v_BenAdvanceSearch.getFirstName();
			}
			if (null != v_BenAdvanceSearch.getLastName() && !v_BenAdvanceSearch.getLastName().isEmpty()) {
				benLastName = v_BenAdvanceSearch.getLastName();
			}
			if (null != v_BenAdvanceSearch.getFatherName() && !v_BenAdvanceSearch.getFatherName().isEmpty()) {
				fatherName = v_BenAdvanceSearch.getFatherName();
			}
			if (null != v_BenAdvanceSearch.getPhoneNo() && !v_BenAdvanceSearch.getPhoneNo().isEmpty()) {
				phoneNo = v_BenAdvanceSearch.getPhoneNo();
			}
			if (null != v_BenAdvanceSearch.getAadharNo() && !v_BenAdvanceSearch.getAadharNo().isEmpty()) {
				aadharNo = v_BenAdvanceSearch.getAadharNo();
			}
			if (null != v_BenAdvanceSearch.getGovtIdentityNo() && !v_BenAdvanceSearch.getGovtIdentityNo().isEmpty()) {
				govIDNo = v_BenAdvanceSearch.getGovtIdentityNo();
			}
			if (null != v_BenAdvanceSearch.getStateID()) {
				stateID = v_BenAdvanceSearch.getStateID() + "";
			}
			if (null != v_BenAdvanceSearch.getDistrictID()) {
				districtID = v_BenAdvanceSearch.getDistrictID() + "";
			}
			/*
			 * reistrarRepoBenSearch.getAdvanceBenSearchList(benID,
			 * benFirstName, benLastName, benGenderID, fatherName, phoneNo,
			 * aadharNo, govIDNo, districtID);
			 */
			ArrayList<Object[]> resList = reistrarRepoBenSearch.getAdvanceBenSearchList(benID, benFirstName,
					benLastName, phoneNo, aadharNo, govIDNo, stateID, districtID);

			result = WrapperRegWorklist.getRegistrarWorkList(resList);
			//System.out.println(resList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public BeneficiaryData getBenOBJ(JsonObject benD) {
		// Initializing BeneficiaryData Class Object...

		BeneficiaryData benData = new BeneficiaryData();
		if (benD.has("firstName") && !benD.get("firstName").isJsonNull())
			benData.setFirstName(benD.get("firstName").getAsString());
		if (benD.has("lastName") && !benD.get("lastName").isJsonNull())
			benData.setLastName(benD.get("lastName").getAsString());
		if (benD.has("gender") && !benD.get("gender").isJsonNull())
			benData.setGenderID(benD.get("gender").getAsShort());
		if (benD.has("dob") && !benD.get("dob").isJsonNull()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
			java.util.Date parsedDate;
			try {
				parsedDate = dateFormat.parse(benD.get("dob").getAsString());
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				benData.setDob(timestamp);
				//System.out.println("hello");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (benD.has("maritalStatus") && !benD.get("maritalStatus").isJsonNull())
			benData.setMaritalStatusID(benD.get("maritalStatus").getAsShort());
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benData.setCreatedBy(benD.get("createdBy").getAsString());
		if (benD.has("fatherName") && !benD.get("fatherName").isJsonNull())
			benData.setFatherName(benD.get("fatherName").getAsString());
		if (benD.has("husbandName")) {
			if (!benD.get("husbandName").isJsonNull())
				benData.setSpouseName(benD.get("husbandName").getAsString());
		}

		if (benD.has("aadharNo") && !benD.get("aadharNo").isJsonNull())
			benData.setAadharNo(benD.get("aadharNo").getAsString());
		//System.out.println(benData);
		// Following values will get only in update request
		if (benD.has("beneficiaryRegID")) {
			if (!benD.get("beneficiaryRegID").isJsonNull()) {
				benData.setBeneficiaryRegID(benD.get("beneficiaryRegID").getAsLong());
			}
		}
		if (benD.has("modifiedBy")) {
			if (!benD.get("modifiedBy").isJsonNull()) {
				benData.setModifiedBy(benD.get("modifiedBy").getAsString());
			}
		}
		//System.out.println(benData);
		return benData;
	}

	public BeneficiaryDemographicData getBenDemoOBJ(JsonObject benD, Long benRegID) {
		// Initializing BeneficiaryDemographicData Class Object...
		BeneficiaryDemographicData benDemoData = new BeneficiaryDemographicData();
		benDemoData.setBeneficiaryRegID(benRegID);
		if (benD.has("countryID") && !benD.get("countryID").isJsonNull())
			benDemoData.setCountryID(benD.get("countryID").getAsInt());
		if (benD.has("stateID") && !benD.get("stateID").isJsonNull())
			benDemoData.setStateID(benD.get("stateID").getAsInt());
		if (benD.has("districtID") && !benD.get("districtID").isJsonNull())
			benDemoData.setDistrictID(benD.get("districtID").getAsInt());
		if (benD.has("blockID") && !benD.get("blockID").isJsonNull())
			benDemoData.setBlockID(benD.get("blockID").getAsInt());
		if (benD.has("servicePointID") && !benD.get("servicePointID").isJsonNull())
			benDemoData.setServicePointID(benD.get("servicePointID").getAsInt());
		if (benD.has("villageID") && !benD.get("villageID").isJsonNull())
			benDemoData.setDistrictBranchID(benD.get("villageID").getAsInt());

		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benDemoData.setCreatedBy(benD.get("createdBy").getAsString());

		if (benD.has("community") && !benD.get("community").isJsonNull())
			benDemoData.setCommunityID(benD.get("community").getAsShort());
		if (benD.has("religion") && !benD.get("religion").isJsonNull())
			benDemoData.setReligionID(benD.get("religion").getAsShort());
		if (benD.has("occupation") && !benD.get("occupation").isJsonNull())
			benDemoData.setOccupationID(benD.get("occupation").getAsShort());
		if (benD.has("educationQualification") && !benD.get("educationQualification").isJsonNull())
			benDemoData.setEducationID(benD.get("educationQualification").getAsShort());
		if (benD.has("income") && !benD.get("income").isJsonNull())
			benDemoData.setIncomeStatusID(benD.get("income").getAsShort());
		//System.out.println(benDemoData);
		// Following values will get only in update request
		if (benD.has("benDemographicsID") && !benD.get("benDemographicsID").isJsonNull())
			benDemoData.setBenDemographicsID(benD.get("benDemographicsID").getAsLong());
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull())
			benDemoData.setModifiedBy(benD.get("modifiedBy").getAsString());
		//System.out.println(benDemoData);
		return benDemoData;
	}

	public BeneficiaryPhoneMapping getBenPhoneOBJ(JsonObject benD, Long benRegID) {
		// Initializing BeneficiaryPhoneMapping Class Object...
		BeneficiaryPhoneMapping benPhoneMap = new BeneficiaryPhoneMapping();
		benPhoneMap.setBenificiaryRegID(benRegID);
		if (benD.has("phoneNo") && !benD.get("phoneNo").isJsonNull())
			benPhoneMap.setPhoneNo(benD.get("phoneNo").getAsString());
		if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
			benPhoneMap.setCreatedBy(benD.get("createdBy").getAsString());
		//System.out.println(benPhoneMap);
		// Following values will get only in update request
		if (benD.has("benPhMapID") && !benD.get("benPhMapID").isJsonNull())
			benPhoneMap.setBenPhMapID(benD.get("benPhMapID").getAsLong());
		if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull())
			benPhoneMap.setModifiedBy(benD.get("modifiedBy").getAsString());
		//System.out.println(benPhoneMap);
		return benPhoneMap;
	}

	@Override
	public String getBeneficiaryDetails(Long beneficiaryRegID) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.serializeNulls();
		Gson gson = gsonBuilder.create();
		List<Object[]> resList = registrarRepoBeneficiaryDetails.getBeneficiaryDetails(beneficiaryRegID);

		//System.out.println("hello");

		if (resList != null && resList.size() > 0) {

			ArrayList<Map<String, Object>> govIdList = new ArrayList<>();
			ArrayList<Map<String, Object>> otherGovIdList = new ArrayList<>();
			Map<String, Object> govIDMap;
			Map<String, Object> otherGovIDMap;
			Object[] objarr = resList.get(0);
			//System.out.println("helooo");
			for (Object[] arrayObj : resList) {
				if (arrayObj[26] != null) {
					if ((Boolean) arrayObj[26] == true) {
						govIDMap = new HashMap<>();

						govIDMap.put("type", arrayObj[24]);
						govIDMap.put("value", arrayObj[25]);
						govIDMap.put("isGovType", arrayObj[26]);
						govIdList.add(govIDMap);
					} else {
						otherGovIDMap = new HashMap<>();

						otherGovIDMap.put("type", arrayObj[24]);
						otherGovIDMap.put("value", arrayObj[25]);
						otherGovIDMap.put("isGovType", arrayObj[26]);
						otherGovIdList.add(otherGovIDMap);
					}
				}

			}
			String s = beneficiaryImageRepo.getBenImage(beneficiaryRegID);
			FetchBeneficiaryDetails fetchBeneficiaryDetailsOBJ = FetchBeneficiaryDetails.getBeneficiaryDetails(objarr,
					govIdList, s, otherGovIdList);
			//System.out.println("helooo");
			return gson.toJson(fetchBeneficiaryDetailsOBJ);
		} else {
			//System.out.println("helooo");
			return null;
		}

		// System.out.println("helooo");

		// ArrayList<FetchBeneficiaryDetails> beneficiaryDetails =
		// FetchBeneficiaryDetails.getBeneficiaryDetails(resList);

	}

	@Override
	public String getBenImage(Long benRegID) {
		Map<String, String> benImageMap = new HashMap<String, String>();
		String s = beneficiaryImageRepo.getBenImage(benRegID);
		if (s != null)
			benImageMap.put("benImage", s);

		return new Gson().toJson(benImageMap);
	}

	@Override
	public int updateBeneficiary(JsonObject benD) {
		Long benRegID = null;
		// Call repository for updating data in
		// Table: i_beneficairy
		// Persistence Class: BeneficiaryData
		BeneficiaryData beneficiaryData = getBenOBJ(benD);
		int response = registrarRepoBenData.updateBeneficiaryData(beneficiaryData.getFirstName(),
				beneficiaryData.getLastName(), beneficiaryData.getGenderID(), beneficiaryData.getDob(),
				beneficiaryData.getMaritalStatusID(), beneficiaryData.getFatherName(), beneficiaryData.getSpouseName(),
				beneficiaryData.getAadharNo(), beneficiaryData.getModifiedBy(), beneficiaryData.getBeneficiaryRegID());
		return response;
	}

	@Override
	public int updateBeneficiaryDemographic(JsonObject benD, Long benRegID) {
		Long tmpBenDemoID = null;
		// Call repository for updating data in
		// Table: i_bendemographics
		// Persistence Class: BeneficiaryDemographicData
		BeneficiaryDemographicData benDemographicData = getBenDemoOBJ(benD, benRegID);
		Integer benDemoData = registrarRepoBenDemoData.updateBendemographicData(benDemographicData.getCountryID(),
				benDemographicData.getStateID(), benDemographicData.getDistrictID(), benDemographicData.getAreaID(),
				benDemographicData.getServicePointID(), benDemographicData.getDistrictBranchID(),
				benDemographicData.getCommunityID(), benDemographicData.getReligionID(),
				benDemographicData.getOccupationID(), benDemographicData.getEducationID(),
				benDemographicData.getIncomeStatusID(), benDemographicData.getModifiedBy(),
				benDemographicData.getBeneficiaryRegID());

		return benDemoData;
	}

	@Override
	public int updateBeneficiaryPhoneMapping(JsonObject benD, Long benRegID) {
		Long tmpBenPhonMapID = null;
		// Call repository for updating data in
		// Table: m_benphonemap
		// Persistence Class: BeneficiaryPhoneMapping
		BeneficiaryPhoneMapping benPhoneMap = getBenPhoneOBJ(benD, benRegID);
		Integer benPhoneMapRes = registrarRepoBenPhoneMapData.updateBenPhoneMap(benPhoneMap.getPhoneNo(),
				benPhoneMap.getModifiedBy(), benPhoneMap.getBenificiaryRegID());

		return benPhoneMapRes;
	}

	@Override
	public int updateBenGovIdMapping(JsonObject benD, Long benRegID) {
		Long tempBenGovMapID = null;
		// Call repository for Delete Data from table m_bengovidmap and save
		// Data to table m_bengovidmap
		// Persistence Class = BenGovIdMapping
		ArrayList<BenGovIdMapping> benGovIDMap = BenGovIdMapping.getBenGovIdMappingOBJList(benD, benRegID);
		// List IDsToDelete = new ArrayList();
		// for (BenGovIdMapping benGovID : benGovIDMap) {
		// if (null != benGovID.getID()) {
		// // delete
		// //registrarRepoBenGovIdMapping.delete(benGovID);
		// IDsToDelete.add(benGovID);
		// benGovIDMap.remove(benGovIDMap.indexOf(benGovID));
		// }
		// }

		int x = registrarRepoBenGovIdMapping.deletePreviousGovMapID(benRegID);
		ArrayList<BenGovIdMapping> benGovIDMaps = (ArrayList<BenGovIdMapping>) registrarRepoBenGovIdMapping
				.save(benGovIDMap);

		return benGovIDMaps.size();
	}

	@Override
	public int updateBeneficiaryDemographicAdditional(JsonObject benD, Long benRegID) {
		Long tmpBenDemoAddID = null;

		BeneficiaryDemographicAdditional beneficiaryDemographicAdditional = getBeneficiaryDemographicAdditional(benD,
				benRegID);
		int res = 0;
		BeneficiaryDemographicAdditional demographicAdditionalData = beneficiaryDemographicAdditionalRepo
				.getBeneficiaryDemographicAdditional(benRegID);
		if (null != demographicAdditionalData) {
			res = beneficiaryDemographicAdditionalRepo.updateBeneficiaryDemographicAdditional(
					beneficiaryDemographicAdditional.getLiteracyStatus(),
					beneficiaryDemographicAdditional.getMotherName(), beneficiaryDemographicAdditional.getEmailID(),
					beneficiaryDemographicAdditional.getBankName(), beneficiaryDemographicAdditional.getBranchName(),
					beneficiaryDemographicAdditional.getiFSCCode(), beneficiaryDemographicAdditional.getAccountNo(),
					beneficiaryDemographicAdditional.getModifiedBy(),
					beneficiaryDemographicAdditional.getBeneficiaryRegID());
		} else {
			BeneficiaryDemographicAdditional data = beneficiaryDemographicAdditionalRepo
					.save(beneficiaryDemographicAdditional);
			if (data.getBenDemoAdditionalID() > 0) {
				res = 1;
			}
		}

		return res;
	}

	@Override
	public int updateBeneficiaryImage(JsonObject benD, Long benRegID) {
		Long tmpBenImageID = null;
		BeneficiaryImage beneficiaryImage = new BeneficiaryImage();
		beneficiaryImage.setBeneficiaryRegID(benRegID);
		Integer response = 0;
		if (benD.has("image") && !benD.get("image").isJsonNull()) {
			beneficiaryImage.setBenImage(benD.get("image").getAsString());
			if (benD.has("createdBy") && !benD.get("createdBy").isJsonNull())
				beneficiaryImage.setCreatedBy(benD.get("createdBy").getAsString());

			// Following values will get only in update request
			if (benD.has("benImageID") && !benD.get("benImageID").isJsonNull()) {
				beneficiaryImage.setBenImageID(benD.get("benImageID").getAsLong());
			}
			if (benD.has("modifiedBy") && !benD.get("modifiedBy").isJsonNull()) {
				beneficiaryImage.setModifiedBy(benD.get("modifiedBy").getAsString());
			}

			Long tempBenRegID = beneficiaryImageRepo.findBenImage(benRegID);
			if (null != tempBenRegID) {
				response = beneficiaryImageRepo.updateBeneficiaryImage(beneficiaryImage.getBenImage(),
						beneficiaryImage.getModifiedBy(), beneficiaryImage.getBeneficiaryRegID());
			} else {
				BeneficiaryImage data = beneficiaryImageRepo.save(beneficiaryImage);
				if (data.getBenImageID() > 0) {
					response = 1;
				}
			}

		} else {
			// Nothing failed, No need to update if their is no image value sent
			response = 1;
		}

		return response;
	}
	
	public BeneficiaryDemographicData getBeneficiaryDemographicData(Long benRegID) {
		List<Objects[]> beneficiaryDemographicData = registrarRepoBenDemoData.getBeneficiaryDemographicData(benRegID);
		BeneficiaryDemographicData beneficiaryDemographics =null;
		if(null != beneficiaryDemographicData){
			for(Object[] obj: beneficiaryDemographicData){
				beneficiaryDemographics = new BeneficiaryDemographicData((Long)obj[0], (Integer)obj[1], (String)obj[2]);
			}
		}
		return beneficiaryDemographics;
	}


}
