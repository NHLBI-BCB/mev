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
package edu.dfci.cccb.mev.dataset.domain.simple;

import edu.dfci.cccb.mev.dataset.domain.contract.Analyses;
import edu.dfci.cccb.mev.dataset.domain.contract.Dimension;
import edu.dfci.cccb.mev.dataset.domain.contract.Dimension.Type;
import edu.dfci.cccb.mev.dataset.domain.contract.InvalidDatasetNameException;
import edu.dfci.cccb.mev.dataset.domain.contract.InvalidDimensionTypeException;
import edu.dfci.cccb.mev.dataset.domain.contract.Values;
import edu.dfci.cccb.mev.dataset.domain.prototype.AbstractDataset;

/**
 * @author levk
 * 
 */
public class SimpleDataset extends AbstractDataset {

  private Values values;
  private Analyses analyses;
  private Dimension[] dimensions;

  /**
   * 
   * @param name
   * @param values
   * @param analyses
   * @param dimensions
   * @throws InvalidDatasetNameException
   */
  public SimpleDataset (String name,
                        Values values,
                        Analyses analyses,
                        Dimension... dimensions) throws InvalidDatasetNameException {
    super (name);
    this.values = values;
    this.analyses = analyses;
    this.dimensions = dimensions;
  }

  /* (non-Javadoc)
   * @see edu.dfci.cccb.mev.dataset.domain.contract.Dataset#values() */
  @Override
  public Values values () {
    return values;
  }

  /* (non-Javadoc)
   * @see
   * edu.dfci.cccb.mev.dataset.domain.contract.Dataset#dimension(edu.dfci.cccb
   * .mev.dataset.domain.contract.Dimension.Type) */
  @Override
  public Dimension dimension (Type type) throws InvalidDimensionTypeException {
    for (Dimension dimension : dimensions)
      if (dimension.type () == type)
        return dimension;
    throw new InvalidDimensionTypeException (); // TODO: add args
  }

  /* (non-Javadoc)
   * @see edu.dfci.cccb.mev.dataset.domain.contract.Dataset#analyses() */
  @Override
  public Analyses analyses () {
    return analyses;
  }
}
