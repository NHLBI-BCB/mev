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
package edu.dfci.cccb.mev.dataset.rest.configuration;

import static edu.dfci.cccb.mev.dataset.rest.assembly.tsv.DatasetTsvMessageConverter.TSV_EXTENSION;
import static edu.dfci.cccb.mev.dataset.rest.assembly.tsv.DatasetTsvMessageConverter.TSV_MEDIA_TYPE;
import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;
import lombok.ToString;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import edu.dfci.cccb.mev.dataset.domain.contract.DatasetBuilder;
import edu.dfci.cccb.mev.dataset.domain.contract.ParserFactory;
import edu.dfci.cccb.mev.dataset.domain.contract.ValueStoreBuilder;
import edu.dfci.cccb.mev.dataset.domain.mock.MapBackedValueStoreBuilder;
import edu.dfci.cccb.mev.dataset.domain.simple.SimpleDatasetBuilder;
import edu.dfci.cccb.mev.dataset.domain.supercsv.SuperCsvComposerFactory;
import edu.dfci.cccb.mev.dataset.domain.supercsv.SuperCsvParserFactory;
import edu.dfci.cccb.mev.dataset.rest.assembly.json.simple.SimpleDatasetJsonSerializer;
import edu.dfci.cccb.mev.dataset.rest.assembly.json.simple.SimpleSelectionJsonSerializer;
import edu.dfci.cccb.mev.dataset.rest.assembly.json.simple.SimpleDimensionJsonSerializer;
import edu.dfci.cccb.mev.dataset.rest.assembly.tsv.DatasetTsvMessageConverter;
import edu.dfci.cccb.mev.dataset.rest.assembly.tsv.MultipartUploadDatasetArgumentResolver;
import edu.dfci.cccb.mev.dataset.rest.context.RestPathVariableDatasetRequestContextInjector;
import edu.dfci.cccb.mev.dataset.rest.resolvers.DatasetPathVariableMethodArgumentResolver;
import edu.dfci.cccb.mev.dataset.rest.resolvers.DimensionPathVariableMethodArgumentResolver;

/**
 * @author levk
 * 
 */
@Configuration
@Import (RestPathVariableDatasetRequestContextInjector.class)
@ComponentScan (basePackages = "edu.dfci.cccb.mev.dataset.rest.controllers")
@ToString
public class DatasetRestConfiguration extends WebMvcConfigurerAdapter {

  @Bean
  public MultipartUploadDatasetArgumentResolver multipartUploadDatasetArgumentResolver (ConfigurableBeanFactory beanFactory) {
    return new MultipartUploadDatasetArgumentResolver (beanFactory, false);
  }

  @Bean
  public SimpleDatasetJsonSerializer datasetJsonSerializer () {
    return new SimpleDatasetJsonSerializer ();
  }

  @Bean
  public SimpleSelectionJsonSerializer selectionJsonSerializer () {
    return new SimpleSelectionJsonSerializer ();
  }

  @Bean
  public SimpleDimensionJsonSerializer simpleDimensionJsonSerializer () {
    return new SimpleDimensionJsonSerializer ();
  }

  @Bean
  public SuperCsvComposerFactory superCsvComposerFactory () {
    return new SuperCsvComposerFactory ();
  }

  @Bean
  public DatasetTsvMessageConverter datasetTsvMessageConverter () {
    return new DatasetTsvMessageConverter ();
  }

  @Bean
  public ParserFactory tsvParserFactory () {
    return new SuperCsvParserFactory ();
  }

  @Bean
  @Scope (value = SCOPE_REQUEST, proxyMode = INTERFACES)
  public ValueStoreBuilder valueFactory () {
    return new MapBackedValueStoreBuilder ();
  }

  @Bean
  public DatasetBuilder datasetBuilder () {
    return new SimpleDatasetBuilder ();
  }

  @Bean
  public DatasetPathVariableMethodArgumentResolver datasetPathVariableMethodArgumentResolver () {
    return new DatasetPathVariableMethodArgumentResolver ();
  }

  @Bean
  public DimensionPathVariableMethodArgumentResolver dimensionPathVariableMethodArgumentResolver () {
    return new DimensionPathVariableMethodArgumentResolver ();
  }

  /* (non-Javadoc)
   * @see
   * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
   * #configureContentNegotiation
   * (org.springframework.web.servlet.config.annotation
   * .ContentNegotiationConfigurer) */
  @Override
  public void configureContentNegotiation (ContentNegotiationConfigurer configurer) {
    configurer.mediaType (TSV_EXTENSION, TSV_MEDIA_TYPE);
  }
}