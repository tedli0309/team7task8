package controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.FundBean;
import databean.FundPriceHistoryBean;
import databean.PositionBean;
import databean.TransactionBean;
import databean.UserBean;
import formbean.LoginForm;
import formbean.OrderForm;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import model.UserDAO;

public class OrderAction extends Action {
	private FormBeanFactory<OrderForm> formBeanFactory = FormBeanFactory.getInstance(OrderForm.class);
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private UserDAO userDAO;
	//private FundPriceHistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;
	OrderAction(Model model) {
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
		userDAO = model.getUserDAO();
		//fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		positionDAO = model.getPositionDAO();
	}
	@Override
	public String getName() {
        return "order.do";
    }


	@Override
	public String perform(HttpServletRequest request) {
		try {
			
			OrderForm form = formBeanFactory.create(request);
			
			UserBean user  = (UserBean)request.getSession().getAttribute("customer");
			user = userDAO.read(user.getUserName());
			
			
			List<String> message = new ArrayList<String>();
			request.setAttribute("message", message);
			
			if (!form.isPresent()) {
				double availableCash = transactionDAO.currentBalace(user.getUserId(), user.getCash());
				request.setAttribute("availableCash", availableCash);
                return "order.jsp";
            }
			message.addAll(form.getValidationErrors());
			if(message.size() > 0){
				return "order.jsp";
			}
			if (form.getSelectb().equals("sell")) {
				return sell(request, form, user);
			}
			if (form.getSelectb().equals("buy")) {
				return buy(request, form, user);
			}
			
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "order.jsp";
	}

	private String buy(HttpServletRequest request, OrderForm form , UserBean user) {
		List<String> errors = new ArrayList<String>();
	    request.setAttribute("errors", errors);	 
	    
	    List<String> message = new ArrayList<String>();
	    request.setAttribute("message", message);
	    
		try {			
			//UserBean user  = (UserBean)request.getSession().getAttribute("user");
			user = userDAO.read(user.getUserName());
			double balance = transactionDAO.currentBalace(user.getUserId(), user.getCash());
			double amount = Double.parseDouble(form.getOrderAmount());
			if(balance < amount){
				errors.add("Sorry,your current balance is not enough");
				return "order.jsp";
			}
			System.out.println();
			FundBean fund = fundDAO.getFundBySymbol(form.getOrderSymbol());
			if(fund == null) {
				errors.add("There is no fund with this symbol");
				return "order.jsp";
			}
			//FundPriceHistoryBean history = fundPriceHistoryDAO.getFundPriceHistory(fund.getFundId(), new Date().toString());
//			if(fund.getPrice() == 0) {
//				errors.add("Fund's price can't be 0");
//				return "order.jsp";
//			}
			transactionDAO.create(new TransactionBean(user.getUserId(), fund.getFundId(), null, -1 ,"buy",amount));
			message.add("Success");	
			double availableCash = transactionDAO.currentBalace(user.getUserId(), user.getCash());
			request.setAttribute("availableCash", availableCash);
			return "order.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());			
			return "order.jsp";
		}		
	}

	private String sell(HttpServletRequest request, OrderForm form, UserBean user) {
		List<String> errors = new ArrayList<String>();
	    request.setAttribute("errors", errors);	 
	    HttpSession session = request.getSession();
	    if(session.getAttribute("employee") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }
	    List<String> message = new ArrayList<String>();
	    request.setAttribute("message", message);
	    
		try {			    
			user = userDAO.read(user.getUserName());
			FundBean fund = fundDAO.getFundBySymbol(form.getOrderSymbol());
			if(fund == null) {
				errors.add("There is no fund with this symbol");
				return "order.jsp";
			}
			//System.out.println("sssssssss");
			PositionBean position = positionDAO.getPosition(user.getUserId(), fund.getFundId());
			double share = Double.parseDouble(form.getOrderAmount());
			
			if(share > position.getShares()){
				errors.add("Sorry,you dont have same shares");
				return "order.jsp";
			}
			transactionDAO.create(new TransactionBean(user.getUserId(), fund.getFundId(), null, share ,"sell", -1));
			message.add("Success!");
			double availableCash = transactionDAO.currentBalace(user.getUserId(), user.getCash());
			request.setAttribute("availableCash", availableCash);
			return "order.jsp";
		} catch (RollbackException e) {
			errors.add(e.toString());
			return "order.jsp";
		}	
	}

}
