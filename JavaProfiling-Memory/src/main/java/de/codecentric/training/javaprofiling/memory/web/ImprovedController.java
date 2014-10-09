package de.codecentric.training.javaprofiling.memory.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import de.codecentric.training.javaprofiling.memory.service.PrimeFinderService;
import de.codecentric.training.javaprofiling.memory.service.PrimeStorageService;
import de.codecentric.training.javaprofiling.memory.service.PrimeStorageService2;

/**
 * Improved Controller for the home view of the application.
 * 
 * @author fabian.lange
 * @author patrick.peschlow
 */
@Controller
public class ImprovedController {

	@Autowired
	private PrimeFinderService primeFinderService;

	@Autowired
	@Qualifier("Storage2")
	private PrimeStorageService primeStorageService;

	/**
	 * Handles GET requests for the home page.
	 * 
	 * @return home.jsp
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/improved")
	public String homeGet(Model model) {
		model.addAttribute("primecounts", primeStorageService.getPrimeCountsPerMax());
		return "home";
	}

	/**
	 * Handles POST requests for the home page.
	 * 
	 * @return the next jsp
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/improved")
	public String homePost(Model model, ServletRequest request) {
		if (WebUtils.hasSubmitParameter(request, "calculate")) {
			Integer maximumInteger = getMaximumInteger(request);
			if (maximumInteger != null) {
				calculateAndAddPrimes(maximumInteger);
			}
		}
		if (WebUtils.hasSubmitParameter(request, "clear")) {
			// rebuild the cache - it seems to leak memory
			primeStorageService = new PrimeStorageService2();
		}
		return "redirect:/improved";
	}

	private void calculateAndAddPrimes(Integer maximumInteger) {
		List<Integer> foundPrimes = primeFinderService.findPrimes(maximumInteger);
		primeStorageService.addPrimes(maximumInteger, foundPrimes);
	}

	private Integer getMaximumInteger(ServletRequest request) {
		String maximumString = WebUtils.findParameterValue(request, "maximum");
		try {
			return Integer.valueOf(maximumString.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
