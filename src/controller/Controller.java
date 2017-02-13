
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import databean.EmployeeBean;
import databean.UserBean;
import model.Model;
import model.UserDAO;

public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new AccountAction(model));
        Action.add(new OrderAction(model));
        Action.add(new FundDetailAction(model));
        Action.add(new TransactionHistoryAction(model));
        Action.add(new ChangePwdAction(model));
        Action.add(new RequestCheck(model));
        Action.add(new ResearchFund(model));
        Action.add(new EmployeeAccountAction(model));
        Action.add(new ViewCustomerAction(model));
        Action.add(new ResetPasswordAction(model));
        Action.add(new CreateCustomerAccountAction(model));
        Action.add(new CreateEmployeeAccountAction(model));
        Action.add(new CreateFund(model));
        Action.add(new DepositCheck(model));
        Action.add(new TransitionAction(model));
        Action.add(new TransitionDayAction(model));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nextPage = performTheAction(request);
     
        sendToNextPage(nextPage, request, response);
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
    	//System.out.println(1);
    	HttpSession session = request.getSession(true);
    	//System.out.println(2);
    	UserBean customer = (UserBean) session.getAttribute("customer");
        //System.out.println(3);
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        //System.out.println(request.getServletPath());
        String action = getActionName(request.getServletPath());
        //System.out.println(action);
        
        if (customer == null && employee == null) {
            return Action.perform("login.do", request);
        }
        if(action.equals("login.do") && customer != null){
        	return Action.perform("account.do", request);
        }
        if(action.equals("login.do") && employee != null){
        	return Action.perform("employeeAccount.do", request);
        }

        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        } else if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        } else if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"+ nextPage);
        	d.forward(request, response);
        	return;          
        } else {
        	if (nextPage.startsWith("https://") || nextPage.startsWith("http://")){
        		response.sendRedirect(nextPage);
        	}else {
        		response.sendRedirect("https://" + nextPage);
        	}
        	return;
        }

//        throw new ServletException(Controller.class.getName()
//                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
    
}
