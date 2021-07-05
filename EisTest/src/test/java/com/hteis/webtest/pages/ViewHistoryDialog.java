package com.hteis.webtest.pages;

import java.util.ArrayList;
import org.testng.Assert;
import com.hteis.webtest.common.DateUtil;
import com.hteis.webtest.entities.FlowStepData;
import com.hteis.webtest.selenium.*;

public class ViewHistoryDialog extends HtmlPage {

	public HtmlButton getCloseBtn() {
		return this.findElementByCss("div.modal-footer button").toHtmlButton();
	}

	public ArrayList<FlowStepData> getFlowStepData() throws Exception {
		HtmlElement frame = this.findElementByTag("iframe");
		Driver.switchToFrame(frame);
		HtmlPage page = new HtmlPage();

		ArrayList<FlowStepData> stepData = new ArrayList<FlowStepData>();
		if (page.existByTag("table")) {

			for (HtmlRow row : page.findElementByTag("table").toHtmlTable().getRows()) {
				FlowStepData data = new FlowStepData();
				data.SequenceNo = Integer.parseInt(row.getCellValue(1));
				data.Operator = row.getCellValue(2);
				data.CreateDate = DateUtil.getDateTimeFromString(row.getCellValue(3));
				data.Conclusion = row.getCellValue(4);
				data.Comment = row.getCellValue(5);

				stepData.add(data);
			}
		}

		Driver.switchBack();

		return stepData;
	}
	
	public void verifyFlowStepData(FlowStepData[] expectedSteps) throws Exception{
		ArrayList<FlowStepData> actualSteps = this.getFlowStepData();
		Assert.assertEquals(actualSteps.size(), expectedSteps.length, "流转历史的记录数不对");
	
		FlowStepData actualStep, step;
		String prefix;
		for(int i = 0; i < expectedSteps.length; i++){
			actualStep = actualSteps.get(i);
			step = expectedSteps[i];
			prefix = String.format("第%d步", i + 1);
			Assert.assertEquals(actualStep.Operator, step.Operator, prefix + "处理人错误");
			Assert.assertEquals(DateUtil.getCNDateStr(actualStep.CreateDate), DateUtil.getCNDateStr(step.CreateDate), prefix + "处理时间不对");
			Assert.assertEquals(actualStep.Conclusion, step.Conclusion, prefix + "审批结论不对");
			Assert.assertEquals(actualStep.Comment, step.Comment, prefix + "审批意见不对");
		}
	}
}
