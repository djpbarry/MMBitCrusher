/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmbitcrusher;

import ij.process.ImageProcessor;
import mmbitcrusher.VariableMap.Binning;
import static mmbitcrusher.VariableMap.Binning.EIGHT;
import static mmbitcrusher.VariableMap.Binning.FOUR;
import static mmbitcrusher.VariableMap.Binning.ONE;
import static mmbitcrusher.VariableMap.Binning.TWO;
import mmbitcrusher.VariableMap.BitDepth;
import static mmbitcrusher.VariableMap.BitDepth.EIGHT_BIT;
import org.micromanager.Studio;
import org.micromanager.data.Coords;
import org.micromanager.data.Image;
import org.micromanager.data.Metadata;
import org.micromanager.data.Metadata.MetadataBuilder;
import org.micromanager.data.Processor;
import org.micromanager.data.ProcessorContext;

/**
 *
 * @author David Barry <david.barry at crick dot ac dot uk>
 */
public class BitCrusherProcessor extends Processor {

    private Studio studio;
    private BitDepth bitDepth;
    private Binning binning;
    private boolean scaling;

    public BitCrusherProcessor(Studio studio, BitDepth bitDepth, Binning binning, boolean scaling) {
        this.studio = studio;
        this.bitDepth = bitDepth;
        this.binning = binning;
        this.scaling = scaling;

    }

    public void processImage(Image image, ProcessorContext context) {
        ImageProcessor proc = studio.data().ij().createProcessor(image);
        Metadata md = image.getMetadata();
        double pixelSize = md.getPixelSizeUm();
//        IJ.log(String.format("Original pixel size: %f", pixelSize));
//        IJ.log(String.format("Original image dimensions: %f x %f", (pixelSize * image.getWidth()), (pixelSize * image.getHeight())));
        MetadataBuilder mb = md.copy();
        int width = image.getWidth();
        int height = image.getHeight();
        int binFactor = 1;
        switch (binning) {
            case TWO:
                binFactor = TWO.getBinValue();
                break;
            case FOUR:
                binFactor = FOUR.getBinValue();
                break;
            case EIGHT:
                binFactor = EIGHT.getBinValue();
                break;
            case ONE:
                binFactor = ONE.getBinValue();
            default:
        }
        ImageProcessor outputProc = binPixels(binFactor, proc);
        switch (bitDepth) {
            case EIGHT_BIT:
                outputProc = outputProc.convertToByteProcessor(scaling);
                break;
            case SIXTEEN_BIT:
                outputProc = outputProc.convertToShortProcessor(false);
            default:
        }
        mb.bitDepth(bitDepth.getBitDepthValue());
        mb.binning(binning.getBinValue());
//        IJ.log(String.format("Bin factor: %d", binning.getBinValue()));
        mb.pixelSizeUm(pixelSize * binning.getBinValue());

        Metadata md2 = mb.build();
//        IJ.log(String.format("Expected new pixel size: %f", (pixelSize * binning.getBinValue())));
//        IJ.log(String.format("Actual new pixel size: %f", md2.getPixelSizeUm()));
//        IJ.log(String.format("Expected new image dimensions: %f x %f",
//                (pixelSize * binning.getBinValue() * outputProc.getWidth()),
//                (pixelSize * binning.getBinValue() * outputProc.getHeight())));
//        IJ.log(String.format("Actual new image dimensions: %f x %f\n",
//                (md2.getPixelSizeUm() * outputProc.getWidth()),
//                (md2.getPixelSizeUm() * outputProc.getHeight())));
//        IJ.log("");

        int channelIndex = image.getCoords().getChannel();

        Coords coords = image.getCoords().copy()
                .channel(channelIndex).build();
        Image output = studio.data().createImage(outputProc.getPixels(),
                outputProc.getWidth(), outputProc.getHeight(), bitDepth.getBitDepthValue() / 8,
                image.getNumComponents(), coords, md2);
        context.outputImage(output);
    }

    private ImageProcessor binPixels(int binning, ImageProcessor input) {
        ImageProcessor output = input.resize(input.getWidth() / binning);
        int width = output.getWidth();
        int height = output.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                output.putPixelValue(x, y, bin(input, x * binning, y * binning, binning));
            }
        }
        output.resetMinAndMax();
        return output;
    }

    private int bin(ImageProcessor proc, int x0, int y0, int binSize) {
        int sum = 0;
        for (int y = y0; y < y0 + binSize; y++) {
            for (int x = x0; x < x0 + binSize; x++) {
                sum += proc.getPixelValue(x, y);
            }
        }
        return sum;
    }
}
