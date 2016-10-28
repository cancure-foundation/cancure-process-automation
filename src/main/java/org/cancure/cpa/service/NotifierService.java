package org.cancure.cpa.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

/**
 * Notifies users when a task is created for them.
 * 
 */
public class NotifierService implements TaskListener {

	private List<Notifier> taskListeners = new ArrayList<>();
	
	public NotifierService(){
		taskListeners.add(new EmailNotifier());
		taskListeners.add(new SMSNotifier());
	}
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		TaskEntity task = (TaskEntity) delegateTask;
		Map vars = task.getVariables();
		String date = "NA";
		String patName = (String)vars.get("patientName");
		Integer prn = (Integer)vars.get("prn");
		StringBuffer message = new StringBuffer("");
		if(task.getDueDate()!=null){
		      Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		      date= formatter.format(task.getDueDate());
		}
		/*message.append("Hi, <br><br>"
		        + "A task has been assigned to you.<br> "
		        + "<b>Patient Name : <b>" + patName + "<br>"
				+ "<b>PRN :</b> " + prn + "<br>"
				+ "<b>Task to do :</b> " + task.getName() + "<br><br>"
				+ "Visit <a href=http://www.cancure.in.net>www.cancure.in.net</a><br><br>"
				+ "Thanks, <br/>Cancure");*/
		message.append("<div style='border : 2px solid #f4961c;'>"
                + "<div style='background-color: #f4961c;color: #fff;padding:8px 15px;font-weight:600;'>"
                + "Cancure Foundation</div>"
                + "<div style='padding:15px;color: #222d32;font-weight:500;'> "
                + "Hi, <br><br>"
                + "<b>The following task been assigned to you.</b> <br> <br>"
                + "<table border=1 style='border-collapse: collapse;'>"
                + "<tr>"
                + "<th style='padding:4px 8px;'> PRN</th>"
                + "<th style='padding:4px 8px;'> Patient Name</th>"
                + "<th style='padding:4px 8px;'> Task Name</th>"
                + "<th style='padding:4px 8px;'> Task Expiry</th>"
                + "</tr>"
                + "<tr>"
                + "<td style='padding:4px 8px;'>"+ prn +"</td>"
                + "<td style='padding:4px 8px;'>"+ patName +"</td>"
                + "<td style='padding:4px 8px;'>"+ task.getName() +"</td>"
                + "<td style='padding:4px 8px;'>"+ date +"</td>"
                + "</tr>"
                + "</table><br>"
                + "Visit <a href='www.cancure.in.net'>www.cancure.in.net</a> <br> <br>"
                + "<b>Thanks,</b> <br>"
                + "Admin"
                + "</div>"
                + "</div>");
		new NotificationComponent().notify(message.toString(), null, task);
		
	}
}
