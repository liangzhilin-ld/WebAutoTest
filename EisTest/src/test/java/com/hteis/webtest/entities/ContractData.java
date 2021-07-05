package com.hteis.webtest.entities;

import java.util.Date;
import java.util.Random;

import com.hteis.webtest.common.DateUtil;

public class ContractData {

	/*** 客户名称 */
	public String CustName;

	/*** 合同名称 */
	public String ContractName;
	
	/*** 合同类型 */
	public String ContractType;

	/*** 签订地点 */
	public String SignLocation;

	/*** 签订日期 */
	public Date SignDate;

	/*** 供用电合同号 */
	public String PowerSupplyContractNo;

	/*** 供用电服务合同号 */
	public String PowerServiceContrctNo;

	/*** 合同份数 */
	public int CopyCount;

	/*** 仲裁委员会 */
	public String ArbitCommittee;

	/*** 甲方分数 */
	public int PartACopyCount;

	/*** 乙方份数 */
	public int PartBCopyCount;

	/*** 合同开始日期 */
	public Date ContractStartDate;

	/*** 合同结束日期 */
	public Date ContractEndDate;

	/*** 月度分解电量 */
	public float[] MonthlyAmount;
	
	/*** 购电总量 */
	public float TotalAmount;

	/*** 合同模板 */
	public String ContractTemplate;

	/*** 合同条款信息 */
	public String[] TermFactor;

	public static ContractData createData(String companyName, int year, String template) throws Exception {
		ContractData data = new ContractData();
		data.ContractName = String.format("%s%d年用电合同", companyName, year);
		data.CustName = companyName;
		data.SignLocation = "四川成都";
		data.SignDate = DateUtil.getDateFromString(String.format("%s-12-20", year - 1));
		data.PowerSupplyContractNo = DateUtil.getNoFromDateTime("gyd");
		data.PowerServiceContrctNo = DateUtil.getNoFromDateTime("gdfw");
		data.CopyCount = 3;
		data.ArbitCommittee = "企业仲裁委员会";
		data.ContractType = "售电合同";
		data.PartACopyCount = 1;
		data.PartBCopyCount = 2;
		data.ContractStartDate = DateUtil.getDateFromString(String.format("%d-01-01", year));
		data.ContractEndDate = DateUtil.getDateFromString(String.format("%d-12-31", year));
		data.MonthlyAmount = new float[12];
		data.ContractTemplate = template;
		Random r = new Random();
		data.TotalAmount = 0;
		for (int i = 0; i < 12; i++) {
			data.MonthlyAmount[i] = (float)Math.round(r.nextFloat() * 200 * 10000) / 10000;
			data.TotalAmount += data.MonthlyAmount[i];
		}
		
		if(template == "大客户套餐"){
			data.TermFactor = new String[3];
			// 交易开始日期
			data.TermFactor[0] = String.format("%d-01-01 00:00:00", year);
			
			// 交易结束日期
			data.TermFactor[1] = String.format("%d-12-31 23:59:59", year);
			
			//  委托服务费(元) 
			data.TermFactor[2] = "5000";
		}

		return data;
	}
	
	public float[] getMonthAmounts(int startMonth, int monthCount){
		float amount[] = new float[monthCount];
		for(int i = 0; i < monthCount; i++){
			amount[i] = this.MonthlyAmount[startMonth + i - 1];
		}
		
		return amount;
	}
}
