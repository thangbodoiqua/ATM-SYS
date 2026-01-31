package com.demo.action.admin.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.*;

import com.demo.dto.UserDTO;
import com.demo.form.admin.user.CreateUserForm;
import com.demo.service.UserService;
import com.demo.service.impl.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class CreateUserAction extends Action {

    private final UserService userService = new UserServiceImpl();

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return mapping.findForward("create-user");
        }

        CreateUserForm f = (CreateUserForm) form;

        String username = trim(f.getUsername());
        String password = trim(f.getPassword());
        String email    = trim(f.getEmail());
        String phone    = trim(f.getPhone());
        String address  = trim(f.getAddress());
        String gender   = trim(f.getGender());
        String dobStr   = trim(f.getDob());   

        boolean hasError = false;

        if (isEmpty(username)) {
            request.setAttribute("err_username", "Username is required");
            hasError = true;
        }

        if (isEmpty(password)) {
            request.setAttribute("err_password", "Password is required");
            hasError = true;
        }

        if (!isEmpty(email) && !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            request.setAttribute("err_email", "Invalid email format");
            hasError = true;
        }

        if (!isEmpty(phone) && !phone.matches("^[0-9+\\-()\\s]{8,20}$")) {
            request.setAttribute("err_phone", "Invalid phone");
            hasError = true;
        }

        if (isEmpty(address)) {
            request.setAttribute("err_address", "Address is required");
            hasError = true;
        }

        if (isEmpty(gender)) {
            request.setAttribute("err_gender", "Gender is required");
            hasError = true;
        }

        Date dobSql = null;

        if (isEmpty(dobStr)) {
            request.setAttribute("err_dob", "Date of birth is required");
            hasError = true;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            sdf.setLenient(false);  
            
			try {
				java.util.Date dobUtil = sdf.parse(dobStr);
				java.util.Date today = new java.util.Date();
				if (dobUtil.after(today)) {
					request.setAttribute("err_dob", "DOB cannot be in the future");
					hasError = true;
				} else {
			        dobSql = new java.sql.Date(dobUtil.getTime());
			    }
			
			}catch (ParseException | IllegalArgumentException e) {
			        request.setAttribute("err_dob", "DOB must be valid (yyyy-MM-dd)");
			        hasError = true;
			}
        }

        if (hasError) {
            return mapping.getInputForward();
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setPhone(phone);
        dto.setAddress(address);
        dto.setGender(gender);
        dto.setDob(dobSql);

        boolean created = userService.createUser(dto);
        if (!created) {
            request.setAttribute("error", "Cannot create user. Username may already exist.");
            return mapping.getInputForward();
        }

        f.setUsername(null);
        f.setEmail(null);
        f.setPassword(null);
        f.setGender(null);
        f.setPhone(null);
        f.setAddress(null);
        f.setDob(null);

        request.setAttribute("message", "User created successfully");
        return mapping.findForward("create-user");
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}