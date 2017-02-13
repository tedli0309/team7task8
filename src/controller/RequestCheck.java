package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.UserBean;
import formbean.OrderForm;
import formbean.RequestCheckForm;
import databean.TransactionBean;
import model.FundPositionViewDAO;
import model.Model;
import model.TransactionDAO;
import model.UserDAO;

public class RequestCheck extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private UserDAO userDAO;
	private FundPositionViewDAO fundPositionViewDAO;
	private TransactionDAO transactionDAO;
	
	public RequestCheck(Model model) {	 
		userDAO = model.getUserDAO();
		fundPositionViewDAO = model.getFundPostionViewDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() { return "requestCheck.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("customer");
        if(session.getAttribute("employee") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }      
        try {
        	RequestCheckForm form = formBeanFactory.create(request);
        	user = userDAO.read(user.getUserName());

        	double	availableCash = user.getCash();
        	 
        	availableCash = transactionDAO.currentBalace(user.getUserId(), availableCash);
        	
        	if (!form.isPresent()) {
        		request.setAttribute("availableCash", availableCash);
            	return ("requestCheck.jsp");
        	}
        	errors.addAll(form.getValidationErrors());
        	if(errors.size() > 0){
        		request.setAttribute("availableCash", availableCash);
        		return "requestCheck.jsp";
			}
        	double curAmount = Double.parseDouble(form.getCheckAmount());
        	if (curAmount > availableCash) {
        		errors.add("Shame! You Dont have enough money!");
        		request.setAttribute("availableCash", availableCash);
        		return "requestCheck.jsp";
        	} 
        	transactionDAO.create(new TransactionBean(user.getUserId(), 0, null, 0.0, "request", curAmount));
        	availableCash = transactionDAO.currentBalace(user.getUserId(), user.getCash());
        	
        	//TransactionHistory[]

       		//request.setAttribute("userList", userDAO.getUsers());   
       		//request.setAttribute("customer", user);
        	request.setAttribute("message", "Congraduation! Your Money is on the System!");
	       	request.setAttribute("availableCash", availableCash);
        	return ("requestCheck.jsp");
	    } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "requestCheck.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "requestCheck.jsp";
		}
    }
}
