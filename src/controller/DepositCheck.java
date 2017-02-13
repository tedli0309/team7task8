package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.DepositCheckForm;
import formbean.OrderForm;
import formbean.RequestCheckForm;
import databean.TransactionBean;
import model.FundPositionViewDAO;
import model.Model;
import model.TransactionDAO;
import model.UserDAO;

public class DepositCheck extends Action {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);
	private UserDAO userDAO;
	private TransactionDAO transactionDAO;
	
	public DepositCheck(Model model) {	 
		userDAO = model.getUserDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() { return "depositCheck.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        if(session.getAttribute("customer") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }
        try {
        	DepositCheckForm form = formBeanFactory.create(request);
        	       	
        	if (!form.isPresent()) {
        		return ("depositCheck.jsp");
        	}
        	errors.addAll(form.getValidationErrors());
        	if(errors.size() > 0){      
        		return "depositCheck.jsp";
			}
        	UserBean user = userDAO.read(form.getUserName());
        	if (user == null) {
        		errors.add("user doesn't exist! please check!");
        		return "depositCheck.jsp";
        	}
        	
        	
        	double curAmount = Double.parseDouble(form.getCheckAmount());
        	transactionDAO.create(new TransactionBean(user.getUserId(), 0, null, 0.0, "deposit", curAmount));

        	request.setAttribute("message", "Congraduation! the Customer's Money is on the System!");
	       
        	return ("depositCheck.jsp");
	    } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "depositCheck.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "depositCheck.jsp";
		}
    }
}
