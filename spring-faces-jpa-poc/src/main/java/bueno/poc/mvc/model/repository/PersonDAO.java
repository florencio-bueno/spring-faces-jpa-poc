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
package bueno.poc.mvc.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import bueno.common.jpa.GenericDAO;
import bueno.poc.mvc.model.entity.Person;

@Repository
public class PersonDAO extends GenericDAO<Person> {
 
	public List<String> listNames() {
		return super.findByNamedQuery(Person.LIST_NAMES, String.class);
	}
	
	public List<Person> findByExactName(String name) {
		return super.findByNamedQuery(Person.FIND_BY_EXACT_NAME, "personName",name);
	}

}
