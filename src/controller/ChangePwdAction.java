package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.EmployeeBean;
import databean.UserBean;
import formbean.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);

	private UserDAO userDAO;

	private EmployeeDAO employeeDAO;

	public ChangePwdAction(Model model) {
		userDAO = model.getUserDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "change-pwd.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			ChangePwdForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return "change-pwd.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "change-pwd.jsp";
			}			
			HttpSession session = request.getSession();
			UserBean user = (UserBean) session.getAttribute("customer");
			EmployeeBean employee = (EmployeeBean)session.getAttribute("employee");
			if(user != null){
				UserBean updatedUser = userDAO.setPassword(user.getUserName(), form);	
				if(updatedUser == null) {
					errors.add("Database has been changed since you last accessed");
					return "change-pwd.jsp";
				}
				session.setAttribute("user", updatedUser);
				request.setAttribute("message", "Password successfully changed for " 
						+ user.getUserName() + "  (" + user.getLastName() + "," + user.getFirstName()+")");
				return "change-pwd.jsp";
			}else if(employee != null){
				EmployeeBean updatedEmployee = employeeDAO.setPassword(employee.getUserName(), form);				 
				session.setAttribute("user", updatedEmployee);
				request.setAttribute("message", "Password successfully changed for " 
						+ employee.getUserName() + "  (" + employee.getLastName() + "," + employee.getFirstName()+")");
				return "change-pwd.jsp";
			}
			return "change-pwd.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "change-pwd.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "change-pwd.jsp";
		}
	}
}
