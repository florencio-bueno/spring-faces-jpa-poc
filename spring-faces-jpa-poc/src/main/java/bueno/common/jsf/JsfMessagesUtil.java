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
package bueno.common.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public final class JsfMessagesUtil {

	private JsfMessagesUtil() {
		// Just in case
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	private static void addMessage(String componente, String mensagem, final Severity severidade) {
		FacesContext.getCurrentInstance().addMessage(componente, new FacesMessage(severidade, mensagem, null));
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public static final void addFatal(String mensagem) {
		addFatal(null, mensagem);
	}

	public static final void addFatal(String componente, String mensagem) {
		addMessage(componente, mensagem, FacesMessage.SEVERITY_FATAL);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public static final void addError(String mensagem) {
		addError(null, mensagem);
	}

	public static final void addError(String componente, String mensagem) {
		addMessage(componente, mensagem, FacesMessage.SEVERITY_ERROR);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public static final void addInfo(String mensagem) {
		addInfo(null, mensagem);
	}

	public static final void addInfo(String componente, String mensagem) {
		addMessage(componente, mensagem, FacesMessage.SEVERITY_INFO);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------

	public static final void addWarning(String mensagem) {
		addWarning(null, mensagem);
	}

	public static final void addWarning(String componente, String mensagem) {
		addMessage(componente, mensagem, FacesMessage.SEVERITY_WARN);
	}

}
