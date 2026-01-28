package com.demo.action.auth;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.demo.form.auth.*;

public class EnterCardAction extends Action {

    private String mask(String cardNumber) {
        if (cardNumber == null) return null;
        String digits = cardNumber.replaceAll("\\s+", "");
        if (digits.length() < 4) return "****";
        String last4 = digits.substring(digits.length() - 4);
        return "**** **** **** " + last4;
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        EnterCardForm f = (EnterCardForm) form;
        String cardNumber = f.getCardNumber();

        // Validate cơ bản
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập số thẻ");
            return mapping.getInputForward(); // -> enter-card.jsp
        }
        String digits = cardNumber.replaceAll("\\s+", "");
        if (!digits.matches("\\d{12,19}")) { // tuỳ chính sách, thường 16–19
            request.setAttribute("error", "Số thẻ không hợp lệ (12–19 chữ số)");
            return mapping.getInputForward();
        }

        // TODO: Tra cứu DB xem thẻ có tồn tại/active không. Nếu không, set error & inputForward.
        // CardDTO card = cardDAO.findByNumber(digits);
        // if (card == null || !card.isActive()) { ... }

        // Lưu thông tin thẻ vào session (chỉ mask để hiển thị)
        HttpSession session = request.getSession(true);
        session.setAttribute("ATM_CARD_NUMBER_RAW", digits);     // nếu không muốn giữ raw, bỏ dòng này
        session.setAttribute("ATM_CARD_NUMBER_MASKED", mask(cardNumber));

        // Truyền mask qua request để enter-pin.jsp hiển thị
        request.setAttribute("maskedCard", mask(cardNumber));

        // Chuyển sang trang nhập PIN
        return mapping.findForward("enterPin");
    }
}
