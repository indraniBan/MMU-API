package com.iemr.mmu.service.common.master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonMasterServiceImpl implements CommonMaterService{

	private ANCMasterDataServiceImpl ancMasterDataServiceImpl;
	private NurseMasterDataServiceImpl nurseMasterDataServiceImpl;
	private DoctorMasterDataServiceImpl doctorMasterDataServiceImpl;
	private RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl;
	private NCDScreeningMasterServiceImpl ncdScreeningServiceImpl;
	private QCMasterDataServiceImpl qCMasterDataServiceImpl;
	private NCDCareMasterDataServiceImpl ncdCareMasterDataServiceImpl;

	@Autowired
	public void setNcdCareMasterDataServiceImpl(NCDCareMasterDataServiceImpl ncdCareMasterDataServiceImpl)
	{
		this.ncdCareMasterDataServiceImpl = ncdCareMasterDataServiceImpl;
	}
	
	@Autowired
	public void setqCMasterDataServiceImpl(QCMasterDataServiceImpl qCMasterDataServiceImpl)
	{
		this.qCMasterDataServiceImpl = qCMasterDataServiceImpl;
	}
	
	@Autowired
	public void setRegistrarServiceMasterDataImpl(RegistrarServiceMasterDataImpl registrarServiceMasterDataImpl) {
		this.registrarServiceMasterDataImpl = registrarServiceMasterDataImpl;
	}
	
	@Autowired
	public void setAncMasterDataServiceImpl(ANCMasterDataServiceImpl ancMasterDataServiceImpl) {
		this.ancMasterDataServiceImpl = ancMasterDataServiceImpl;
	}
	
	@Autowired
	public void setNurseMasterDataServiceImpl(NurseMasterDataServiceImpl nurseMasterDataServiceImpl) {
		this.nurseMasterDataServiceImpl = nurseMasterDataServiceImpl;
	}
	
	
	@Autowired
	public void setDoctorMasterDataServiceImpl(DoctorMasterDataServiceImpl doctorMasterDataServiceImpl) {
		this.doctorMasterDataServiceImpl = doctorMasterDataServiceImpl;
	}

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningMasterServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}

	@Override
	public String getVisitReasonAndCategories() {
		String visitReasonAndCategories = nurseMasterDataServiceImpl.GetVisitReasonAndCategories();
		return visitReasonAndCategories;
	}
	
	@Override
	public String getMasterDataForNurse(Integer visitCategoryID) {
		String nurseMasterData = null;
		if(null!=visitCategoryID){
			switch(visitCategoryID){
				case 1 :{
					// 1 : Cancer Screening
					nurseMasterData = nurseMasterDataServiceImpl.getCancerScreeningMasterDataForNurse();
				}
				break;
				case 2 :{
					// 2 : NCD screening
					nurseMasterData = ncdScreeningServiceImpl.getNCDScreeningMasterData();
				}
				break;
				case 3 :{
					// 3 : NCD care
					//TODO: NCD Care Master Data call - tmprlly calling ANC master Data
					nurseMasterData = ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc();
				}
				break;
				case 4 :{
					// 4 : ANC
					nurseMasterData = ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc();
				}
				break;
				case 5 :{
					// 5 : PNC
					//TODO: PNC Master Data call - tmprlly calling ANC master Data
					nurseMasterData = ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc();
				}
				break;
				case 6 :{
					// 6 : General OPD
					//TODO: General OPD Master Data call - tmprlly calling ANC master Data
					nurseMasterData = ancMasterDataServiceImpl.getCommonNurseMasterDataForGenopdAncNcdcarePnc();
				}
				break;
				case 7 :{
					// 7 : General OPD (QC)
					nurseMasterData = "No Master Data found for QuickConsultation";
				}
				break;
				default :{
					nurseMasterData = "Invalid VisitCategoryID";
				}
			}
		}else{
			nurseMasterData = "Invalid VisitCategoryID";
		}
		return nurseMasterData;
	}
	@Override
	public String getMasterDataForDoctor(Integer visitCategoryID, Integer providerServiceMapID) {
		String doctorMasterData = null;
		if(null!=visitCategoryID){
			switch(visitCategoryID){
				case 1 :{
					// 1 : Cancer Screening
					// neeraj passed one parameter  for tem reason
					doctorMasterData = doctorMasterDataServiceImpl.getCancerScreeningMasterDataForDoctor(providerServiceMapID);
				}
				break;
				case 2 :{
					// 2 : NCD screening
					//TODO: NCD SCreening Master Data call
					doctorMasterData = "No Master Data found for NCD SCreening";
				}
				break;
				case 3 :{
					// 3 : NCD care
					//TODO: NCD Care Master Data call
					doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(providerServiceMapID);
				}
				break;
				case 4 :{
					// 4 : ANC
					doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(providerServiceMapID);
				}
				break;
				case 5 :{
					// 5 : PNC
					//TODO:  PNC Master Data call - tmprlly calling ANC master Data
					doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(providerServiceMapID);
				}
				break;
				case 6 :{
					// 6 : General OPD
					//TODO: General OPD Master Data call - tmprlly calling ANC master Data
					doctorMasterData = ancMasterDataServiceImpl.getCommonDoctorMasterDataForGenopdAncNcdcarePnc(providerServiceMapID);
				}
				break;
				case 7 :{
					// 7 : General OPD (QC)
					doctorMasterData =  qCMasterDataServiceImpl.getQuickConsultMasterData();
				}
				break;
				default :{
					doctorMasterData = "Invalid VisitCategoryID";
				}
			}
		}else{
			doctorMasterData = "Invalid VisitCategoryID";
		}
		return doctorMasterData;
	}

}
