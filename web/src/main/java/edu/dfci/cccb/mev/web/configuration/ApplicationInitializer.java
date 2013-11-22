/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.dfci.cccb.mev.web.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import edu.dfci.cccb.mev.dataset.rest.configuration.DatasetRestConfiguration;
import edu.dfci.cccb.mev.web.configuration.container.ContainerConfigurations;

/**
 * @author levk
 * 
 */
public class ApplicationInitializer implements WebApplicationInitializer {

  /* (non-Javadoc)
   * @see
   * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
   * .ServletContext) */
  @Override
  public void onStartup (ServletContext servletContext) throws ServletException {
    AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext ();

    mvcContext.register (DispatcherConfiguration.class, PersistenceConfiguration.class, ContainerConfigurations.class);

    // mvcContext.register (HeatmapServerConfiguration.class,
    // HeatmapClientConfiguration.class);
    // mvcContext.register (AnnotationServerConfiguration.class);

    mvcContext.register (DatasetRestConfiguration.class);

    Dynamic dispatcher = servletContext.addServlet ("dispatcher", new DispatcherServlet (mvcContext));
    dispatcher.setLoadOnStartup (1);
    dispatcher.addMapping ("/");
  }
}
