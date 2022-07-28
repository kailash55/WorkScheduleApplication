package com.scheduler.schedulerpresentation.formmodels.elements;

import lombok.Data;

@Data
public class TextBox extends FormElement{
	
	public TextBox(String name)
	{
		super(name);
	}
	
	private String value;
}
