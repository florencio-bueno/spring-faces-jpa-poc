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
package bueno.configuration.web.jsf;

import javax.faces.application.ProjectStage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class ConfigureJSFContextDevParameters implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("javax.faces.PROJECT_STAGE", ProjectStage.Development.name());
		servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "0");//PARA PRODUCAO UTILIZE -1. PARA DESENVOLVIMENTO UTILIZE 0, CASO CONTRARIO OS XHTMLS SO SERAO ATUALIZADOS NO RESTART DO SERVIDOR
		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "false");//EM PRODUCAO NAO HA NECESSIDADE DOS COMENTARIOS, MUDAR VALOR PARA TRUE
	}

}
