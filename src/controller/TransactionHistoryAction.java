package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.OwnerFundsBean;
import databean.TransactionHistoryViewBean;
import databean.UserBean;
import model.FundPositionViewDAO;
import model.Model;
import model.TransactionHistoryViewDAO;
import model.UserDAO;

public class TransactionHistoryAction extends Action{
	private UserDAO userDAO;
	//private FundPositionViewDAO fundPositionViewDAO;
	TransactionHistoryViewDAO transactionHistoryViewDAO;
	public TransactionHistoryAction(Model model) {	 
		userDAO = model.getUserDAO();
		transactionHistoryViewDAO = model.getTransactionHistoryViewDAO();
		//fundPositionViewDAO = model.getFundPostionViewDAO();
	}

	public String getName() { return "transactionHistory.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        if(session.getAttribute("employee") != null) {
        	errors.add("Action is not permitted");
        	return "error.jsp";
        }
        UserBean user = (UserBean)session.getAttribute("customer");
        
        try {
        	 System.out.println(123);
        	 user = userDAO.read(user.getUserName());
        	 
        	 session.setAttribute("customer", user);
        	 //System.out.println(456);
        	 TransactionHistoryViewBean[] transactionList2 = 
        			 transactionHistoryViewDAO.getTransactionHistory();
        	 List<TransactionHistoryViewBean> ans = new ArrayList<>();
        	 TransactionHistoryViewBean[] transactionList;
        	 for(TransactionHistoryViewBean transaction: transactionList2) {
        		 if(transaction.getUserId() == user.getUserId()) {
        			 ans.add(transaction);
        		 }
        	 }
        	 transactionList =  ans.toArray(new TransactionHistoryViewBean[ans.size()]);
        	// System.out.println(transactionList.length);
        	 request.setAttribute("transactionList", transactionList);
        	 
        	//TransactionHistory[]
       		//request.setAttribute("userList", userDAO.getUsers());   
       		//request.setAttribute("customer", user);
       		return ("transaction-hist.jsp");
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	
        	return "transaction-hist.jsp";
        }
    }
}
