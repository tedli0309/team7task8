package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import databean.FundBean;
import databean.OwnerFundsBean;
import databean.TransactionHistoryViewBean;
import databean.UserBean;
import model.FundDAO;
import model.FundPositionViewDAO;
import model.Model;
import model.TransactionDAO;
import model.TransactionHistoryViewDAO;
import model.UserDAO;

public class ViewCustomerAction extends Action {
	private UserDAO userDAO;
	private FundPositionViewDAO fundPositionViewDAO;
	private TransactionHistoryViewDAO transactionHistoryViewDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	public ViewCustomerAction(Model model) {
		this.fundPositionViewDAO = model.getFundPostionViewDAO();
		this.userDAO = model.getUserDAO();
		this.transactionHistoryViewDAO = model.getTransactionHistoryViewDAO();
		this.fundDAO = model.getFundDAO();
		this.transactionDAO = model.getTransactionDAO();
	}
	@Override
	public String getName() {
		return "viewCustomer.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
	    request.setAttribute("errors", errors);	
		HttpSession session = request.getSession();
		if(session.getAttribute("customer") != null) {
	       	errors.add("Action is not permitted");
	       	return "error.jsp";
		}
	    try{ 
	    	 UserBean user = userDAO.match(MatchArg.equals("userName",request.getParameter("username")))[0];
	         OwnerFundsBean[] fundsOfOwner2 =  fundPositionViewDAO.getFundPosition();
	         OwnerFundsBean[] fundsOfOwner;
	         List<OwnerFundsBean> ans2= new ArrayList<>();
	         System.out.println(user.getUserId());
	         for(OwnerFundsBean fo: fundsOfOwner2) {
	        	 if(fo.getUserId() == user.getUserId())
	        		 ans2.add(fo);
	         }
	         fundsOfOwner = ans2.toArray(new OwnerFundsBean[ans2.size()]);
	       	 TransactionHistoryViewBean[] transactionList2 = transactionHistoryViewDAO.getTransactionHistory();
	       	List<TransactionHistoryViewBean> ans = new ArrayList<>();
       	 TransactionHistoryViewBean[] transactionList;
       	 for(TransactionHistoryViewBean transaction: transactionList2) {
       		 if(transaction.getUserId() == user.getUserId()) {
       			 ans.add(transaction);
       		 }
       	 }
       	 transactionList =  ans.toArray(new TransactionHistoryViewBean[ans.size()]);
	       	 request.setAttribute("fundList", fundsOfOwner);
	       	 request.setAttribute("customer", user);

	       	 request.setAttribute("transactionList", transactionList);
	       	 //System.out.print(transactionList.length);
	       	
	       	double	availableCash = user.getCash();
        	availableCash = transactionDAO.currentBalace(user.getUserId(), availableCash);
        	//TransactionHistoryViewBean[] transactions = transactionViewDAO.getTransactionHistory(user.getUserId());
        	request.setAttribute("availableCash", availableCash);
        	int count = transactionList.length;
        	//String latestTran = null;
        	if (count == 0)
        		request.setAttribute("latestTransaction", null);
        	else {
        		Date last = null;
        		String lastString = null;
        		for (int i = 0; i < count; i++) {        			
        			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        			//System.out.println(transactionList[i].getExecuteDate());
        			if (transactionList[i].getExecuteDate() != null) {
	        			Date date = df.parse(transactionList[i].getExecuteDate());
	        			if (last == null) {
	        				last = date;
	        				lastString = transactionList[i].getExecuteDate();
	        			} else if(last.before(date)){
	        				last = date;
	        				lastString = transactionList[i].getExecuteDate();
	        			}
        			}
        		}
        		request.setAttribute("latestTransaction", lastString);
        	}
        	
        	

	    }  catch (RollbackException e) {
	    	errors.add(e.toString());
	    	return "viewCustomer.jsp";
	    } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewCustomer.jsp";
	}
	

	public String findFundName(int fundId) {
		try{	
			FundBean[] fb = fundDAO.match(MatchArg.equals("fundId", fundId));
			return fb[0].getName();
		}catch(RollbackException e) {
			
		}
		return "Fund ID";
		
	}
}		
