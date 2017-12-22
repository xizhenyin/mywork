package com.cnpc.dj.party.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.entity.ThirdCompany;
import com.cnpc.dj.party.service.ThirdCompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/company")
public class ThirdCompanyController {

	@Autowired
	ThirdCompanyService thirdCompanyService;

	@RequestMapping(value = "/get")
	@ResponseBody
	public ThirdCompany get(@RequestParam(required = false) String id) {
		ThirdCompany company = thirdCompanyService.get(id);
		return company;
	}

	@RequestMapping(value = { "/list", "" })
	public String list(ThirdCompany thirdCompany, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		return "";
	}

	@RequestMapping(value = "/load")
	@ResponseBody
	public List<ThirdCompany> load(ThirdCompany thirdCompany, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		return thirdCompanyService.findList(thirdCompany);
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/tologin")
	public String tologin() {
		return "mantance";
	}

	@RequestMapping(value = "form")
	public String form(ThirdCompany thirdCompany, Model model) {
		return "";
	}

	/**
	 * 
	 * @param thirdCompany
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Result save(ThirdCompany thirdCompany, Model model, RedirectAttributes redirectAttributes) {
		// System.out.println("url==/save");
		thirdCompany.setId(UUID.randomUUID().toString());

		int count = thirdCompanyService.insert(thirdCompany);
		Result result = new Result();
		if (count > 0) {
			result.setCode("0");
		} else {
			result.setCode("1");
		}

		return result;

	}

	/**
	 * 更新
	 * @param thirdCompany
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(ThirdCompany thirdCompany, Model model, RedirectAttributes redirectAttributes) {

		int count = thirdCompanyService.updateByPrimaryKey(thirdCompany);
		Result result = new Result();
		if (count > 0) {
			result.setCode("0");
		} else {
			result.setCode("1");
		}

		return result;

	}

	@RequestMapping(value = "delete")
	public String delete(ThirdCompany thirdCompany, RedirectAttributes redirectAttributes) {
		return "";
	}

	/**
	 * 物理删除
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "ajaxDelete")
	@ResponseBody
	public Result ajaxDelete(String id, RedirectAttributes redirectAttributes) {
		int count = thirdCompanyService.deleteByPrimaryKey(id);
		Result result = new Result();
		if (count > 0) {
			result.setCode("0");
		} else {
			result.setCode("1");
		}

		return result;
	}

	/**
	 * 厂家停止，下面服务都停止
	 * @param thirdCompany
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public Result updateStatus(ThirdCompany thirdCompany, Model model, RedirectAttributes redirectAttributes)
			throws JsonProcessingException {

		return thirdCompanyService.updateManageStatusByCompany(thirdCompany);

	}

}
