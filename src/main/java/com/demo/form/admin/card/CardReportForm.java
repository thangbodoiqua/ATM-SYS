package com.demo.form.admin.card;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.demo.dto.CardDTO;

public class CardReportForm extends ActionForm {
	private List<CardDTO> cards;

	public List<CardDTO> getCards() {
		return cards;
	}

	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}

}
