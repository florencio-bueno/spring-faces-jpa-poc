/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bueno.poc.mvc.view.controller;

import static bueno.common.jsf.JsfMessagesUtil.addError;
import static bueno.common.jsf.JsfMessagesUtil.addInfo;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bueno.poc.mvc.controller.service.PersonService;
import bueno.poc.mvc.model.entity.Person;
import lombok.Getter;

@Controller
@ViewScoped
public class PersonController {

	@Autowired
	private PersonService service;

	@Getter
	private boolean viewOnly;

	@Getter
	private Person entity;

	@Getter
	private List<Person> entityList;

	@Getter
	private List<String> nameList;

	// ----------------------------------------------------------------------------------------------------------------------------------

	@PostConstruct
	public void init() {
		updateEntityList();
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public void delete() {
		try {
			service.delete(entity.getId());
			entity = null;
			updateEntityList();
			addInfo("Registry successfully deleted");
		} catch (Exception e) {
			addError("Could not delete the registry");
		}
	}

	public void save() {
		try {
			service.save(entity);
			entity = null;
			updateEntityList();
			addInfo("Registry successfully saved");
		} catch (Exception e) {
			addError("Could not save the registry");
		}
	}

	public void update() {
		try {
			service.update(entity);
			entity = null;
			updateEntityList();
			addInfo("Registry successfully updated");
		} catch (Exception e) {
			addError("Could not update the registry");
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public void names() {
		nameList = service.listNames();
	}

	public void updateEntityList() {
		entityList = service.list();
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public void add() {
		this.entity = new Person();
		this.viewOnly = false;
	}

	public void view(Person entity) {
		this.entity = entity;
		this.viewOnly = false;
	}

	public void viewOnly(Person entity) {
		this.entity = entity;
		this.viewOnly = true;
	}

	public void close() {
		this.entity = null;
		this.viewOnly = false;
	}

}
