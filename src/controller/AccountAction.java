
package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databean.OwnerFundsBean;
import databean.TransactionHistoryViewBean;
import databean.UserBean;
import model.FundPositionViewDAO;
import model.Model;
import model.TransactionDAO;
import model.TransactionHistoryViewDAO;
import model.UserDAO;

public class AccountAction extends Action {
	 
	private UserDAO userDAO;
	private FundPositionViewDAO fundPositionViewDAO;
	private TransactionDAO transactionDAO;
	private TransactionHistoryViewDAO transactionViewDAO;
	public AccountAction(Model model) {	 
		userDAO = model.getUserDAO();
		fundPositionViewDAO = model.getFundPostionViewDAO();
		transactionDAO = model.getTransactionDAO();
		transactionViewDAO = model.getTransactionHistoryViewDAO();
	}

	public String getName() { return "account.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();
        UserBean user = (UserBean)session.getAttribute("customer");

        try {
        	user = userDAO.read(user.getUserId());
        	OwnerFundsBean[] fundsOfOwner2 =  fundPositionViewDAO.getFundPosition();
	         OwnerFundsBean[] fundsOfOwner;
	         List<OwnerFundsBean> ans2= new ArrayList<>();
	         for(OwnerFundsBean fo: fundsOfOwner2) {
	        	 if(fo.getUserId() == user.getUserId())
	        		 ans2.add(fo);
	         }
	         fundsOfOwner = ans2.toArray(new OwnerFundsBean[ans2.size()]);
        	request.setAttribute("fundList", fundsOfOwner);
        	
        	double	availableCash = user.getCash();
        	availableCash = transactionDAO.currentBalace(user.getUserId(), availableCash);
        	TransactionHistoryViewBean[] transactionList = transactionViewDAO.getTransactionHistory();
//        	int count = transactions.length;
//        	String latestTran = null;
//        	if (count == 0)
//        		request.setAttribute("latestTransaction", null);
//        	else {
//        		for (int i = count - 1; i >= 0; i--) {
//        			latestTran = transactions[i].getExecuteDate();
//        			if (latestTran != null) break;
//        		}
//        		request.setAttribute("latestTransaction", latestTran);
//        	}
        	int count = transactionList.length;
        	//String latestTran = null;
        	if (count == 0) {
        		request.setAttribute("latestTransaction", null);
        	} else {
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
        	request.setAttribute("availableCash", availableCash);
       		request.setAttribute("customer", user);
       		return ("account.jsp");
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "account.jsp";
        } catch (ParseException e) {
        	errors.add(e.getMessage());
        	return "account.jsp";
		}
    }
}
