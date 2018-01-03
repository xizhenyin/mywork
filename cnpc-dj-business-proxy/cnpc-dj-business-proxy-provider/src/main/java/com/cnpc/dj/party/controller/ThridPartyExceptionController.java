package com.cnpc.dj.party.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpc.dj.party.entity.Result;
import com.cnpc.dj.party.exception.ThridPartyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class ThridPartyExceptionController {
	@Autowired
	private ObjectMapper objectMapper;

	@ExceptionHandler(ThridPartyException.class)
	@ResponseBody
	public String thripPartyException(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) throws JsonProcessingException {
		Result result = new Result();
		result.setCode("1");
		result.setMsg(ex.getMessage());
		return objectMapper.writeValueAsString(result);
	}
}
