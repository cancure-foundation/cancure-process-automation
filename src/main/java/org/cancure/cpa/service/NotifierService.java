package org.cancure.cpa.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.cancure.cpa.util.Log;

/**
 * Notifies users when a task is created for them.
 * 
 */
public class NotifierService implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		TaskEntity task = (TaskEntity) delegateTask;
		Map vars = task.getVariables();
		String date = "NA";
		String patName = (String)vars.get("patientName");
		Integer prn = (Integer)vars.get("prn");
		
		if(task.getDueDate()!=null){
		      Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		      date= formatter.format(task.getDueDate());
		}
		
		Map<String, Object> values = new HashMap<>();
		values.put("prn", prn);
		values.put("patName", patName);
		values.put("taskName", task.getName());
		values.put("date", date);
		
		
		new NotificationComponent().notify("PatientRegWorkflowNotification", values, null, task);
		
	}
}
