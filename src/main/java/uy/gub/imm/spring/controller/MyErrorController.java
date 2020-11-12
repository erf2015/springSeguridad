package uy.gub.imm.spring.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyErrorController implements ErrorController {

	@RequestMapping(method = RequestMethod.GET, path = "/error")
	public String error(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errorPage = "error";
		if (status != null) {
			Integer error = (Integer) status;
			if (error == HttpStatus.FORBIDDEN.value()) {
				errorPage = "error-403";
			} else if (error == HttpStatus.NOT_FOUND.value()) {
				errorPage = "error-404";
			} else if (error == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorPage = "error-500";
			} else {
				errorPage = "error";
			}
		}
		return errorPage;
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
