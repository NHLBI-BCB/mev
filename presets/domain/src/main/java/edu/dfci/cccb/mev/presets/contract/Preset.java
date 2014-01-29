package edu.dfci.cccb.mev.presets.contract;
import edu.dfci.cccb.mev.presets.contract.PresetDescriptor;
import edu.dfci.cccb.mev.presets.contract.exceptions.PresetException;


public interface Preset {
  String name();
  PresetDescriptor getDescriptor();
  public abstract Preset init (Object[] values) throws PresetException; 
  
}
