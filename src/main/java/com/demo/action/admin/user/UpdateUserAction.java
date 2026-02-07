package com.demo.action.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.*;

import com.demo.dto.UserDTO;
import com.demo.form.admin.user.UpdateUserForm;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;

public class UpdateUserAction extends Action {
    private final UserService userService = new UserServiceImpl();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {


        if ("GET".equalsIgnoreCase(request.getMethod())) {
        	String username = request.getParameter("username");
        		if(username == null) {
	                ActionErrors errors = new ActionErrors();
	                errors.add("error", new ActionMessage("error.username.missing"));
	                saveErrors(request, errors);
	                return mapping.getInputForward(); 
        		}
        		UserDTO user = userService.findByUsername(username);

        		if (user == null) {
                    ActionErrors errors = new ActionErrors();
                    errors.add("error", new ActionMessage("error.username.not-found"));
                    saveErrors(request, errors);
                    return mapping.getInputForward();
                }
            	UpdateUserForm f = (UpdateUserForm) form;
        		f.setUsername(user.getUsername());
        		f.setEmail(user.getEmail());
        		f.setPhone(user.getPhone());
        		f.setAddress(user.getAddress());
        		
                return mapping.getInputForward();
         }
        	UpdateUserForm f = (UpdateUserForm) form;

           	UserDTO user = userService.findByUsername(f.getUsername());
            if (user == null) {
                ActionErrors errors = new ActionErrors();
                errors.add("error", new ActionMessage("error.username.not-found"));
                saveErrors(request, errors);
                return mapping.getInputForward();
            }
            boolean isChangeEmail = !user.getEmail().equals(f.getEmail());
            boolean isChangePhone = !user.getPhone().equals(f.getPhone());
            boolean isChangeAddress = !user.getAddress().equals(f.getAddress());
            
            if(!isChangeEmail && !isChangePhone && !isChangeAddress) {
            	 ActionErrors errors = new ActionErrors();
                 errors.add("error", new ActionMessage("error.update.nothing-change"));
                 saveErrors(request, errors);
                 return mapping.getInputForward();
            }
            
            if(isChangeEmail) user.setEmail(f.getEmail());
            if(isChangePhone) user.setPhone(f.getPhone());
            if(isChangeAddress) user.setAddress(f.getAddress());
            
            boolean ok = userService.updateUser(user);
            if (!ok) {
                ActionErrors errs = new ActionErrors();
                errs.add("error", new ActionMessage("error.user.update.fail"));
                saveErrors(request, errs);
                return mapping.getInputForward();
            }

            ActionMessages msgs = new ActionMessages();
            msgs.add(Globals.MESSAGE_KEY, new ActionMessage("message.user.update.success"));
            saveMessages(request.getSession(), msgs); 
            
            return mapping.findForward("user-report");
            }
}