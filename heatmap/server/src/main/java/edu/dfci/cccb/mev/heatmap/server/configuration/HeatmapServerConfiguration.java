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
package edu.dfci.cccb.mev.heatmap.server.configuration;

import static org.springframework.context.annotation.FilterType.ANNOTATION;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

import java.util.List;

import javax.inject.Inject;

import lombok.ToString;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.dfci.cccb.mev.heatmap.domain.HeatmapBuilder;
import edu.dfci.cccb.mev.heatmap.domain.Workspace;
import edu.dfci.cccb.mev.heatmap.domain.concrete.ListWorkspace;
import edu.dfci.cccb.mev.heatmap.server.resolvers.DimensionMethodArgumentResolver;
import edu.dfci.cccb.mev.heatmap.server.resolvers.ImportHeatmapMethodArgumentResolver;
import edu.dfci.cccb.mev.heatmap.server.resolvers.WorkspaceHeatmapMethodArgumentResolver;

/**
 * @author levk
 * 
 */
@Configuration
@ComponentScan (value = "edu.dfci.cccb.mev.heatmap",
                includeFilters = @Filter (type = ANNOTATION, value = {
                                                                      Controller.class,
                                                                      ControllerAdvice.class,
                                                                      RestController.class }))
@ToString
public class HeatmapServerConfiguration extends WebMvcConfigurerAdapter {

  private @Inject Workspace workspace;
  private @Inject HeatmapBuilder heatmapBuilder;
  private @Inject ConfigurableBeanFactory beanFactory;

  @Bean
  @Scope (value = SCOPE_SESSION, proxyMode = INTERFACES)
  public Workspace workspace () {
    return new ListWorkspace ();
  }

  @Bean
  public HeatmapBuilder heatmapBuilder () {
    return null; // FIXME: stub
  }

  @Override
  public void addArgumentResolvers (List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add (new WorkspaceHeatmapMethodArgumentResolver (workspace));
    argumentResolvers.add (new ImportHeatmapMethodArgumentResolver (beanFactory, false, heatmapBuilder));
    argumentResolvers.add (new DimensionMethodArgumentResolver ());
  }
}
