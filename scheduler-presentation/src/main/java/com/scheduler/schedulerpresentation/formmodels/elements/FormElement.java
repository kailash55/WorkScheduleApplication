package com.scheduler.schedulerpresentation.formmodels.elements;

import lombok.Data;

@Data
public class FormElement {
	private String id;
	private String name;
	private boolean isDisabled;
	private boolean isHidden;
	private boolean isValid = true;
	private String validationMessage;
	
	public FormElement(String name)
	{
		this.name = name;
	}
}
