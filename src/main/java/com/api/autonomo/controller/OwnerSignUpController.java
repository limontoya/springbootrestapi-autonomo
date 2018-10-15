package com.api.autonomo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.api.autonomo.model.Invoice;
import com.api.autonomo.model.Owner;
import com.api.autonomo.properties.OwnerValidatorProperties;
import com.api.autonomo.service.InvoiceService;
import com.api.autonomo.service.OwnerService;
import com.api.autonomo.service.SecurityService;

@Controller
public class OwnerSignUpController {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private OwnerValidatorProperties ownerValidator;

	@Autowired
	private InvoiceService invoiceService;

	@GetMapping("/signup")
	public String newSignUp(Model model) {

		model.addAttribute("ownerForm", new Owner());
		return "signup";
	}

	@PostMapping("/save")
	public String saveSignUp(@ModelAttribute("ownerForm") Owner ownerForm, BindingResult bindingResult, Model model) {

		ownerValidator.validate(ownerForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "signup";
		}

		ownerService.saveOwner(ownerForm);
		securityService.autoLogin(ownerForm.getEmail(), ownerForm.getEmailKey());

		return "redirect:/viewinvoices";
	}

	@GetMapping(value = { "/", "/viewInvoices" })
	public ModelAndView getAllInvoices() {

		List<Invoice> invoicesList = invoiceService.findAllInvoices();

		return new ModelAndView("viewInvoices", "list", invoicesList);
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {

		if (error != null) {
			model.addAttribute("error", "Your user email and key are invalid.");
		}

		if (logout != null) {
			model.addAttribute("error", "You have been logged out successfully.");
		}

		return "login";
	}

}
