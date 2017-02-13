package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.EmployeeBean;
import databean.UserBean;
import formbean.ChangePwdForm;

public class EmployeeDAO extends GenericDAO<EmployeeBean> {
	public EmployeeDAO(ConnectionPool connectionPool, String tableName) throws DAOException {
		super(EmployeeBean.class, tableName, connectionPool);
	}
	public EmployeeBean read(String userName) throws RollbackException {
		try	{
			Transaction.begin();
			EmployeeBean[] res =  match(MatchArg.equals("userName",userName));
			if (res.length == 0)  return null;
			Transaction.commit();
			return res[0];
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	public void create(EmployeeBean employee) throws RollbackException {		
		try {
			Transaction.begin();
			EmployeeBean[] emp =	match(MatchArg.equals("userName",employee.getUserName()));
			if (emp.length > 0) throw new RollbackException("this UserName has been used!");
			super.create(employee);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}

	}
	public EmployeeBean setPassword(String userName, ChangePwdForm form) 
			throws RollbackException {
	    try {
	    	EmployeeBean dbUser = read(userName);
	        Transaction.begin();
	        if (dbUser == null) {
	            throw new RollbackException("User " + userName + " no longer exists!");
	        }
	        if (Integer.parseInt(form.getOldPassword()) != dbUser.getHashedPassword() ) {
	        	 throw new RollbackException("The old password is not correct!");
	        }
	        
	        dbUser.setHashedPassword(Integer.parseInt(form.getConfirmPassword()));
	         
	        super.update(dbUser);
	      
	        Transaction.commit();
	    
	        return dbUser;
	    } finally {
	        if (Transaction.isActive()) Transaction.rollback();
	    }
	}
	public EmployeeBean[] getEmployees() throws RollbackException {
		EmployeeBean[] users = match();
        return users;
    }
}
