package com.demo.action.auth;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.demo.form.auth.*;

public class EnterPinAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EnterPinForm f = (EnterPinForm) form;
        String pin = f.getPin();

        if (pin == null || !pin.matches("\\d{4,6}")) {
            request.setAttribute("error", "PIN không hợp lệ (4–6 chữ số)");
            return mapping.getInputForward(); // -> enter-pin.jsp
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("ATM_CARD_NUMBER_RAW") == null) {
            // Chưa có thẻ trong flow → quay lại Enter Card
            response.sendRedirect(request.getContextPath() + "/atm/enter-card.do");
            return null;
        }

        // TODO: Verify PIN thật sự
        // String pepper = System.getenv("PIN_PEPPER");
        // boolean ok = PasswordHasher.verify(pin + pepper, storedPinHash);
        boolean ok = true; // DEMO ONLY: luôn coi là đúng
        if (!ok) {
            request.setAttribute("error", "PIN không đúng");
            return mapping.getInputForward();
        }

        // (Tuỳ bạn) Đặt trạng thái authenticated cho flow ATM ở session
        session.setAttribute("ATM_AUTHENTICATED", Boolean.TRUE);

        // Đi tiếp: chuyển sang trang chọn giao dịch (bạn đã có /atm/transaction.do)
        return mapping.findForward("next");
    }
}
