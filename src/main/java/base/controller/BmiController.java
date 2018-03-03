package base.controller;

import base.common.RequestMapping;

public class BmiController {
	@RequestMapping("/toBmi.do")
	public String toBmi(){
		return "bmi";
	}
	@RequestMapping("/bmi.do")
	public String bmi(){
		System.out.println("BmiController's bmi()");
		return "view";
	}
}
