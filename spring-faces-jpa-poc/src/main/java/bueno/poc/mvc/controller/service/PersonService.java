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
package bueno.poc.mvc.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bueno.poc.mvc.model.entity.Person;
import bueno.poc.mvc.model.repository.PersonDAO;

@Transactional(readOnly = true)
@Service
public class PersonService {

	@Autowired
	private PersonDAO dao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(Person person) {
		dao.save(person);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(Person person) {
		dao.update(person);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) {
		dao.deleteById(id);
	}

	public Person find(Long id) {
		return dao.lookup(id);
	}

	public List<Person> list() {
		return dao.getAll();
	}

	public List<String> listNames() {
		return dao.listNames();
	}

	public boolean isExist(String personName) {
		List<Person> list = dao.findByExactName(personName);

		return list != null && list.isEmpty();
	}

	public void deleteAll() {
		throw new RuntimeException("This is not possible at this time, try again later");
	}

}
