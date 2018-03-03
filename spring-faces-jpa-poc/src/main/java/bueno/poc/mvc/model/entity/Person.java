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
package bueno.poc.mvc.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import bueno.common.jpa.GenericEntity;
import lombok.Getter;
import lombok.Setter;

@NamedQueries({ //
		@NamedQuery(name = Person.LIST_NAMES, query = "select name from Person"),//
		@NamedQuery(name = Person.FIND_BY_EXACT_NAME, query = "select p.name from Person p where UPPER(p.name) = UPPER(:personName)")//
}) //

@Getter
@Setter
@Entity
@Table(name = "person")
public class Person extends GenericEntity {
	@Transient
	private static final long serialVersionUID = 7036406504080664825L;

	public static final String LIST_NAMES = "Person.listNames";
	public static final String FIND_BY_EXACT_NAME = "Person.findByExactName";

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "email", length = 500, nullable = false)
	private String email;

}
