package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.EmployeeBean;
import databean.UserBean;

import formbean.ResetPwdForm;
import model.EmployeeDAO;
import model.Model;
import model.UserDAO;

public class ResetPasswordAction extends Action{
	private FormBeanFactory<ResetPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ResetPwdForm.class);

	private UserDAO userDAO;
	private EmployeeDAO employeeDAO;

	public ResetPasswordAction(Model model) {
		userDAO = model.getUserDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "reset-pwd.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);		
		HttpSession session = request.getSession();
		if(session.getAttribute("customer") != null) {
	        	errors.add("Action is not permitted");
	        	return "error.jsp";
	    }
		
		try {
			String customerName = (String) request.getParameter("userName");
			//HttpSession session = request.getSession();
			if (customerName == null) {
				customerName = (String) session.getAttribute("curUserName");
				if (customerName == null) {
					errors.add("don't have any valid customer!");
					return "reset-pwd.jsp";
				}
			} else {
				session.setAttribute("curUserName", customerName);
			}

			ResetPwdForm form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return "reset-pwd.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "reset-pwd.jsp";
			}
			EmployeeBean employee = (EmployeeBean)session.getAttribute("employee");
			UserBean updatedUser = userDAO.setPasswordByEmployee(customerName, form);				 
			session.setAttribute("employee", employee);
			request.setAttribute("message", "Password successfully changed for " 
						+ updatedUser.getUserName() + "  (" + updatedUser.getLastName() + "," 
						+ updatedUser.getFirstName()+")");
			 
		
			return "reset-pwd.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "reset-pwd.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "reset-pwd.jsp";
		}
	}
}
