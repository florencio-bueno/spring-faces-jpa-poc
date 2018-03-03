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
package bueno.configuration.banner;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class CustomBanner implements Banner {

	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {

		out.append("                                                                          \n");
		out.append(" ______            _______  _        _______        _______  _______  _______ \n");
		out.append("(  ___ \\ |\\     /|(  ____ \\( (    /|(  ___  )      (  ____ )(  ___  )(  ____ \\\n");
		out.append("| (   ) )| )   ( || (    \\/|  \\  ( || (   ) |      | (    )|| (   ) || (    \\/\n");
		out.append("| (__/ / | |   | || (__    |   \\ | || |   | |      | (____)|| |   | || |      \n");
		out.append("|  __ (  | |   | ||  __)   | (\\ \\) || |   | |      |  _____)| |   | || |      \n");
		out.append("| (  \\ \\ | |   | || (      | | \\   || |   | |      | (      | |   | || |      \n");
		out.append("| (___) )| (___) || (____/\\| )  \\  || (___) |      | )      | (___) || (____/\\\n");
		out.append("|______/ (_______)(_______/|/    \\_)(_______)      |/       (_______)(_______/\n");
		out.append("                                                                          \n");
		out.append("                                                                          \n");

	}

}
