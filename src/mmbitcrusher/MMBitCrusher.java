/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmbitcrusher;

import org.micromanager.PropertyMap;
import org.micromanager.Studio;
import org.micromanager.data.ProcessorConfigurator;
import org.micromanager.data.ProcessorFactory;
import org.micromanager.data.ProcessorPlugin;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

/**
 *
 * @author David Barry <david.barry at crick dot ac dot uk>
 */
@Plugin(type = ProcessorPlugin.class)
public class MMBitCrusher implements ProcessorPlugin, SciJavaPlugin{

    private Studio studio;
    public static final String menuName = "Bit Crusher";
    public static final String tooltipDescription ="Bin image and/or reduce bit depth";
    private double version = 0.0;
    private String copyright = "Francis Crick Institute, 2018";

    /**
     * @param args the command line arguments
     */
    public ProcessorConfigurator createConfigurator(PropertyMap settings) {
        return new BitCrusherFrame(settings, studio);
    }

    public ProcessorFactory createFactory(PropertyMap settings) {
       return new BitCrusherFactory(studio, settings);
    }

    public String getCopyright() {
        return copyright;
    }

    public String getVersion() {
        return String.valueOf(version);
    }

    public String getHelpText() {
        return tooltipDescription;
    }

    public String getName() {
        return menuName;
    }

    public void setContext(Studio studio) {
        this.studio = studio;
    }
}
