/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmbitcrusher;

import mmbitcrusher.VariableMap.Binning;
import mmbitcrusher.VariableMap.BitDepth;
import static mmbitcrusher.VariableMap.SCALING_STRING;
import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.Processor;
import org.micromanager.data.ProcessorFactory;

/**
 *
 * @author David Barry <david.barry at crick dot ac dot uk>
 */
public class BitCrusherFactory implements ProcessorFactory {

    private Studio studio;
    private BitDepth bitDepth;
    private Binning binning;
    private boolean scaling;

    public BitCrusherFactory(Studio studio, PropertyMap settings) {
        this.studio = studio;
        int bits = settings.getInt(VariableMap.BIT_DEPTH_STRING);
        int bins = settings.getInt(VariableMap.BINNING_STRING);
        boolean scaling = settings.getBoolean(SCALING_STRING);
        this.bitDepth = VariableMap.getBitDepth(bits);
        this.binning = VariableMap.getBinning(bins);
        this.scaling = scaling;
    }

    public Processor createProcessor() {
        return new BitCrusherProcessor(studio, bitDepth, binning, scaling);
    }
}
